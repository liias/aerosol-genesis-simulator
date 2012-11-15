package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationProcessDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessState;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SimulationProcessServiceImpl implements SimulationProcessService {
    final Logger logger = LoggerFactory.getLogger(SimulationProcessServiceImpl.class);

    //Execution service is singleton. Is this will be a problem, consider this:
    //If not, apply this: http://stackoverflow.com/questions/7621920/scopeprototype-bean-scope-not-creating-new-bean
    @Autowired
    private SimulationProcessExecutionService simulationProcessExecutionService;

    @Autowired
    private SimulationResultService simulationResultService;

    @Autowired
    private SimulationProcessDao simulationProcessDao;

    @Autowired
    private SimulationOrderDao simulationOrderDao;

    @Autowired
    private SimulationOrderService simulationOrderService;

    private List<Thread> simulationThreads = new ArrayList<Thread>();

    //Starts the task asynchronously, meaning return value has no real use
    public void startInNewThread(final SimulationProcess process) {
        logger.info("Process start called");
        process.setResultFileNumber(1);
        simulationProcessExecutionService.setSimulationProcess(process);
        Thread simulationThread = new Thread(simulationProcessExecutionService);
        simulationThreads.add(simulationThread);
        simulationThread.start();
        logger.debug("new thread started");
        // I think now the old thread stops
    }

    @Override
    public void setFailed(SimulationProcess process) {
        process.setState(SimulationProcessState.FAILED);
        SimulationOrder order = process.getSimulationOrder();
        order.increaseCountOfFinishedProcesses();
        order = simulationOrderDao.update(order);
        logger.warn("setFailed callback called");
        //TODO: Should we really try to execute the next process?
        SimulationProcess nextProcess = order.getNextNotStartedProcess();
        if (nextProcess != null) {
            startInNewThread(nextProcess);
        } else {
            simulationOrderService.setCompleted();
        }
    }

    @Override
    @Transactional
    public void setCompleted(SimulationProcess process) {
        process.setState(SimulationProcessState.FINISHED);
        SimulationOrder order = process.getSimulationOrder();
        order.increaseCountOfFinishedProcesses();
        order = simulationOrderDao.update(order);
        logger.info("setCompleted callback called");

        simulationResultService.addResultsForProcess(process);

        SimulationProcess nextProcess = order.getNextNotStartedProcess();
        if (nextProcess != null) {
            startInNewThread(nextProcess);
        } else {
            simulationOrderService.setCompleted();
        }
    }

    private Thread getNewestSimulationThread() throws IndexOutOfBoundsException {
        return simulationThreads.get(simulationThreads.size() - 1);
    }

    @Override
    public void stop() {
        try {
            Thread newestSimulationThread = getNewestSimulationThread();
            newestSimulationThread.interrupt();
        } catch (IndexOutOfBoundsException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public SimulationProcess getById(long id) {
        return simulationProcessDao.getById(id);
    }

    @Transactional
    @Override
    public Map<String, Map<String, String>> getParametersMapById(long id) {
        SimulationProcess process = getById(id);
        return process.getParametersMap();
    }

    @Transactional
    @Override
    public String getBestFileContent(SimulationProcess process, double rating) {
        StringBuilder lines = new StringBuilder(100);
        String pid = Long.toString(process.getId());
        String comment = process.getSimulationOrder().getComment();

        List<SimulationProcessParameter> parameters = process.getSimulationProcessParameters();
        Collections.sort(parameters);
        lines.append(rating).append("\t");
        lines.append(pid).append("\t");
        lines.append(comment).append("\t");
        for (SimulationProcessParameter parameter : parameters) {
            String freeAirValue = parameter.stringValue(parameter.getFreeAirValue());
            lines.append(freeAirValue).append("\t");
            if (parameter.hasForest()) {
                String forestValue = parameter.stringValue(parameter.getForestValue());
                lines.append(forestValue).append("\t");
            }
        }
        return lines.toString();
    }

    @Override
    public Long getBestProcessId() throws GeneralException {
        Properties conf = Configuration.getInstance().getUserSettings();
        String id = conf.getProperty("bestProcessId");
        if (id == null) {
            throw new GeneralException("Try to find the best results first");
        }
        return Long.valueOf(id);
    }
}
