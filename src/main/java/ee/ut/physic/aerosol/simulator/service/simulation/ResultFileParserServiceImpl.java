package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResultValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResultFileParserServiceImpl implements ResultFileParserService {
    final Logger log = LoggerFactory.getLogger(ResultFileParserServiceImpl.class);

    private SimulationResultValue createResultValue(SimulationResult result, String name, String value) {
        SimulationResultValue resultValue = new SimulationResultValue();
        resultValue.setName(name);
        resultValue.setValue(value);
        resultValue.setSimulationResult(result);
        result.addSimulationResultValue(resultValue);
        return resultValue;
    }

    private SimulationResult createSimulationResultFromLine(SimulationProcess process, String[] valueNames, String line) {
        String[] values = line.split("\t");
        int i = 0;

        SimulationResult result = new SimulationResult();
        result.setSimulationProcess(process);
        Integer time = Integer.parseInt(values[0]);
        result.setTime(time);
        for (String value : values) {
            createResultValue(result, valueNames[i], value);
            i++;
        }
        return result;
    }

    // Parses result file into SimulationResults
    @Override
    public List<SimulationResult> parseResultFile(SimulationProcess process) {
        List<SimulationResult> results = new ArrayList<SimulationResult>();
        try {
            Integer resultFileNumber = process.getResultFileNumber();
            BufferedReader br = readResultFile(resultFileNumber);
            // Read first line from buffer
            String[] valueNames = br.readLine().split("\t");
            String line;
            // Read all other files from buffer
            while ((line = br.readLine()) != null) {
                SimulationResult simulationResult = createSimulationResultFromLine(process, valueNames, line);
                results.add(simulationResult);
            }
            br.close();
        } catch (Exception e) {
            log.error("error", e);
        }
        return results;
    }

    // number can be 1-999
    private String resultFileNumberToFileName(int number) {
        // with zero-padding with length 3
        String numberAsString = String.format("%03d", number);
        return "T" + numberAsString + ".xl";
    }

    @Override
    public BufferedReader readResultFile(Integer resultFileNumber) {
        String path = Configuration.getInstance().getBurstSimulatorDirPath();
        String outputFilename = resultFileNumberToFileName(resultFileNumber);
        String fullPath = path + outputFilename;
        return readFile(fullPath);
    }

    private BufferedReader readFile(String filePath) {
        try {
            FileInputStream stream = new FileInputStream(filePath);
            DataInputStream in = new DataInputStream(stream);
            return new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            log.error("error", e);
        }
        return null;
    }

    private BufferedReader readReferenceResultsFile(String filename) {
        String path = Configuration.getInstance().getUserConfPath();
        String fullPath = path + filename;
        return readFile(fullPath);
    }

    // Creates Result with every value also having weight
    private SimulationResult createReferenceResultFromLine(String[] valueNames, int weights[], String line) {
        String[] values = line.split("\t");
        int i = 0;
        SimulationResult result = new SimulationResult();
        Integer time = Integer.parseInt(values[0]);
        result.setTime(time);
        for (String value : values) {
            SimulationResultValue resultValue = createResultValue(result, valueNames[i], value);
            Integer weight = weights[i];
            resultValue.setWeight(weight);
            i++;
        }
        return result;
    }

    private int[] convertStringsToIntegers(String[] stringArray) {
        int[] numbers = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            numbers[i] = Integer.parseInt(stringArray[i].trim());
        }
        return numbers;
    }

    @Override
    public List<SimulationResult> parseReferenceResults(String filename) {
        List<SimulationResult> results = new ArrayList<SimulationResult>();
        try {
            BufferedReader br = readReferenceResultsFile(filename);
            // First line is value names
            String[] valueNames = br.readLine().split("\t");
            //Second line is value weights
            String[] weightsAsStrings = br.readLine().split("\t");
            int[] weights = convertStringsToIntegers(weightsAsStrings);
            String line;
            // Read all other files from buffer
            while ((line = br.readLine()) != null) {
                SimulationResult simulationResult = createReferenceResultFromLine(valueNames, weights, line);
                results.add(simulationResult);
            }
            br.close();
        } catch (Exception e) {
            log.error("error", e);
        }
        return results;
    }
}
