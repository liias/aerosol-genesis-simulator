package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationProcessDao;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationResultDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResultValue;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SimulationResultServiceImpl implements SimulationResultService {
    final Logger logger = LoggerFactory.getLogger(SimulationResultServiceImpl.class);
    @Autowired
    private ResultFileParserService resultFileParserService;
    @Autowired
    private SimulationProcessDao simulationProcessDao;
    @Autowired
    private SimulationResultDao simulationResultDao;
    @Autowired
    private SimulationProcessService simulationProcessService;

    @Transactional
    @Override
    public void addResultsForProcess(SimulationProcess process) {
        List<SimulationResult> simulationResults = resultFileParserService.parseResultFile(process);
        process.setSimulationResults(simulationResults);
        simulationProcessDao.update(process);
    }

    //TODO: process time must have at least the same time as reference (100)
    @Transactional
    private List<HashMap<String, Double>> findRatingForProcesses(List<SimulationResult> referenceResults, List<SimulationResult> allResultsInTimeRange, int numberOfRatings) {
        // When time is 100, then resultsAmountInProcess is 100/5 + 1 = 21
        int resultsAmountInProcess = referenceResults.size();
        int allResultsAmount = allResultsInTimeRange.size();
        if (allResultsAmount % resultsAmountInProcess != 0) {
            throw new IllegalArgumentException("Reference has different amount of results than Process has");
        }
        int numberOfProcesses = allResultsAmount / resultsAmountInProcess;
        List<HashMap<String, Double>> processRatings = new ArrayList<HashMap<String, Double>>();

        double worstRatingInProcessRatings = -1;
        Integer worstRatingIndexInProcessRatings = null;

        int globalResultIndex = 0;
        // for each process
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            double processRating = 0;
            int resultIndex;
            // for each result row in process
            for (resultIndex = 0; resultIndex < resultsAmountInProcess; resultIndex++, globalResultIndex++) {
                List<SimulationResultValue> referenceValues = referenceResults.get(resultIndex).getSimulationResultValues();
                List<SimulationResultValue> resultValues = allResultsInTimeRange.get(globalResultIndex).getSimulationResultValues();
                int numberOfReferenceValues = referenceValues.size();
                if (numberOfReferenceValues != resultValues.size()) {
                    throw new IllegalStateException("reference matrix has different number of values than result matrix");
                }
                double resultRating = 0;
                // for each value in result
                for (int valueIndex = 0; valueIndex < numberOfReferenceValues; valueIndex++) {
                    SimulationResultValue referenceValueObject = referenceValues.get(valueIndex);
                    SimulationResultValue resultValueObject = resultValues.get(valueIndex);
                    double referenceValue = referenceValueObject.getNumericValue();
                    double resultValue = resultValueObject.getNumericValue();
                    int weight = referenceValueObject.getWeight();
                    double valueWithWeight = Math.pow(referenceValue - resultValue, 2) * weight;
                    resultRating += valueWithWeight;
                }
                processRating += resultRating;
            }
            // ugly, get the last result process id
            long processId = allResultsInTimeRange.get(globalResultIndex - 1).getSimulationProcess().getId();
            HashMap<String, Double> newWorstRatingAndIndex = addToProcessRatingsIfAllowed(processRatings, numberOfRatings, processId, processRating, worstRatingInProcessRatings, worstRatingIndexInProcessRatings);
            worstRatingInProcessRatings = newWorstRatingAndIndex.get("rating");
            worstRatingIndexInProcessRatings = newWorstRatingAndIndex.get("index").intValue();
            System.out.println(worstRatingInProcessRatings + ": " + worstRatingIndexInProcessRatings);
        }
        return processRatings;
    }

    private HashMap<String, Double> addToProcessRatingsIfAllowed(List<HashMap<String, Double>> processRatings, int numberOfRatings, long processId, double processRating, double worstRatingInProcessRatings, Integer worstRatingIndexInProcessRatings) {
        HashMap<String, Double> pidAndRating = new HashMap<String, Double>();
        pidAndRating.put("pid", (double) processId);
        pidAndRating.put("rating", processRating);
        // if ratings table is not full yet
        if (processRatings.size() <= numberOfRatings) {
            // if this rating is worst than the worst rating the processRatings map so far
            if (processRating > worstRatingInProcessRatings) {
                worstRatingInProcessRatings = processRating;
                worstRatingIndexInProcessRatings = processRatings.size();
            }
            processRatings.add(pidAndRating);
            // if ratings table is full, then replace the worst one only if this is better
        } else {
            // if this rating is better
            if (processRating < worstRatingInProcessRatings) {
                processRatings.set(worstRatingIndexInProcessRatings, pidAndRating);
            }
            //now we need to find the new worst rating and index, as this might have been better one than some others too
            worstRatingInProcessRatings = -1;
            worstRatingIndexInProcessRatings = null;
            int i = 0;
            for (HashMap<String, Double> tempPidAndRating : processRatings) {
                double tempRating = tempPidAndRating.get("rating");
                if (tempRating > worstRatingInProcessRatings) {
                    worstRatingInProcessRatings = 0;
                    worstRatingIndexInProcessRatings = i;
                }
                i++;
            }
        }
        HashMap<String, Double> newWorstRatingAndIndex = new HashMap<String, Double>(2);
        newWorstRatingAndIndex.put("rating", worstRatingInProcessRatings);
        newWorstRatingAndIndex.put("index", (double) worstRatingIndexInProcessRatings);
        return newWorstRatingAndIndex;
    }

    @Transactional
    @Override
    public List<HashMap<String, Double>> findBestResults(int numberOfRatings) throws GeneralException {
        //ordered by time
        List<SimulationResult> referenceResults = resultFileParserService.parseReferenceResults("ref.xl");
        // 5 * (21 -1) = 100
        int maxTime = 5 * (referenceResults.size() - 1);
        //also ordered by time
        List<Long> validProcessIds = simulationProcessDao.getProcessIdsWhereProcessTimeLessOrEqualThan(maxTime);
        if (validProcessIds.isEmpty()) {
            throw new GeneralException("There are no simulations in time range: 0-" + maxTime);
        }
        List<SimulationResult> allResultsInTimeRange = simulationResultDao.getAllResults(validProcessIds, 0, maxTime);
        if (allResultsInTimeRange.isEmpty()) {
            throw new GeneralException("There are no results in time range: 0-" + maxTime);
        }
        return findRatingForProcesses(referenceResults, allResultsInTimeRange, numberOfRatings);
    }

    public void saveBestProcessId(Long processId) {
        String bestProcessId = String.valueOf(processId);
        Configuration conf = Configuration.getInstance();
        Properties userSettings = conf.getUserSettings();
        userSettings.setProperty("bestProcessId", bestProcessId);
        Configuration.getInstance().saveUserSettings(userSettings);
    }

    //Convenience method to do it all for best results file content generation
    @Transactional
    public String generateBestResultsFileAndSaveBestProcessId(int numberOfRatings) throws GeneralException {
        List<HashMap<String, Double>> processRatings = findBestResults(numberOfRatings);
        StringBuilder allLines = new StringBuilder(100);
        //The smallest rating is the best one, find it
        Long bestProcessId = null;
        Double bestRating = null;
        List<String> parameterNames = Configuration.getInstance().getParametersConfiguration().getParameterNamesWithForest();
        allLines.append("score\t");
        allLines.append("pid\t");
        allLines.append("comment\t");
        for (String parameterName : parameterNames) {
            allLines.append(parameterName).append("\t");
        }
        allLines.append("\n");
        for (HashMap<String, Double> pidAndRating : processRatings) {
            double pidDouble = pidAndRating.get("pid");
            long processId = (long) pidDouble;
            double rating = pidAndRating.get("rating");
            if (bestRating == null || rating < bestRating) {
                bestProcessId = processId;
                bestRating = rating;
            }
            SimulationProcess process = simulationProcessDao.getById(processId);
            allLines.append(simulationProcessService.getBestFileContent(process, rating));
            allLines.append("\n");
        }
        logger.info("Best rating is " + bestRating + " for process with id " + bestProcessId);
        saveBestProcessId(bestProcessId);
        return allLines.toString();
    }

    @Transactional
    public String getResultsFileContent(SimulationProcess process) {
        String pid = Long.toString(process.getId());
        String comment = process.getSimulationOrder().getComment();
        List<SimulationResult> results = process.getSimulationResults();
        Collections.sort(results);
        String[] parameterNames = Configuration.getInstance().getResultParameters().getParameterNames();

        StringBuilder lines = new StringBuilder(100);
        lines.append("pid\t");
        lines.append("comment\t");
        lines.append("rid\t");
        for (String parameterName : parameterNames) {
            lines.append(parameterName).append("\t");
        }
        lines.append("\n");

        for (SimulationResult result : results) {
            String resultId = Long.toString(result.getId());
            lines.append(pid).append("\t");
            lines.append(comment).append("\t");
            lines.append(resultId).append("\t");
            //Collections.sort(resultvalues);
            //TODO: Are they in correct order?
            for (SimulationResultValue resultValue : result.getSimulationResultValues()) {
                lines.append(resultValue.getValue()).append("\t");
            }
            lines.append("\n");
        }

        return lines.toString();
    }

    @Transactional
    @Override
    public String getResultsFileContent(Long id) {
        SimulationProcess simulationProcess = simulationProcessDao.getById(id);
        return getResultsFileContent(simulationProcess);
    }
}