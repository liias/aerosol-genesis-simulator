package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationProcessServiceImpl implements SimulationProcessService {

    //TODO: Test if spring really creates new instance of SPEService every time
    //If not, apply this: http://stackoverflow.com/questions/7621920/scopeprototype-bean-scope-not-creating-new-bean
    @Autowired
    private SimulationProcessExecutionService simulationProcessService;

    //Starts the task asynchronously, meaning return value has no real use
    public void start(SimulationProcess process) {
        System.out.println("starting process...");
        //TODO: Generate and write burstcontrol.txt based on process parameters

        //Consider ScheduledExecutorService http://devlearnings.wordpress.com/2010/09/21/tip-use-scheduledexecutor-instead-of-thread-for-polling-or-infinite-loops/
        Thread thread = new Thread() {
            public void run() {
                simulationProcessService.run();
            }
        };
        thread.start();
    }
}
