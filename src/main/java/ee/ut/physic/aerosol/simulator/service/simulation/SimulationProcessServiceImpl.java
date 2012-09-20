package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.*;
import org.springframework.stereotype.Service;

@Service
public class SimulationProcessServiceImpl implements SimulationProcessService {

    //returns true if succeeded
    public boolean start(SimulationProcess process) {
        System.out.println("starting process...");
        //TODO: Generate and write burstcontrol.txt based on process parameters

        //Currently not associated with process entity (could use operations system-s pid)
        ProcessExecuter processExecuter = new ProcessExecuter();
        processExecuter.run();
        return true;
    }
}
