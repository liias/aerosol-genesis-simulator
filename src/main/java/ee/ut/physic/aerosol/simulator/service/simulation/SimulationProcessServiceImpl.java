package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationProcessDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulationProcessServiceImpl implements SimulationProcessService {
    final Logger logger = LoggerFactory.getLogger(SimulationProcessServiceImpl.class);

    //Execution service is singleton. Is this will be a problem, consider this:
    //If not, apply this: http://stackoverflow.com/questions/7621920/scopeprototype-bean-scope-not-creating-new-bean
    @Autowired
    private SimulationProcessExecutionService simulationProcessExecutionService;

    @Autowired
    private SimulationProcessDao simulationProcessDao;

    @Autowired
    private SimulationOrderDao simulationOrderDao;

    //Starts the task asynchronously, meaning return value has no real use
    public void start(final SimulationProcess process) {
        logger.info("Process start called");
        //TODO: Generate and write burstcontrol.txt based on process parameters

        //Consider ScheduledExecutorService http://devlearnings.wordpress.com/2010/09/21/tip-use-scheduledexecutor-instead-of-thread-for-polling-or-infinite-loops/
        Thread thread = new Thread() {
            public void run() {
                simulationProcessExecutionService.setSimulationProcess(process);
                simulationProcessExecutionService.run();
            }
        };
        thread.start();
    }

    @Override
    public void setFailed(SimulationProcess process) {
        process.setState(SimulationProcessState.FAILED);
        process = simulationProcessDao.update(process);
    }

    //This is a callback method
    @Override
    @Transactional
    public void setCompleted(SimulationProcess process) {
        //process.setState(SimulationProcessState.FINISHED);
        //process = simulationProcessDao.update(process);
        SimulationOrder order = process.getSimulationOrder();
        order.incrementNumberOfFinishedProcesses();
        order = simulationOrderDao.update(order);
        logger.info("setCompleted callback called");
        SimulationProcess nextProcess = order.getNextNotStartedProcess();
        if (nextProcess != null) {
            start(nextProcess);
        }
    }
}
