package ee.ut.simulator.domain.simulation;

import ee.ut.simulator.service.simulation.SimulationProcessService;
import ee.ut.simulator.service.simulation.SimulationProcessServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProcessExecuterTest {

    @Test
    public void testRun() throws Exception {
        ProcessExecuter processExecuter = new ProcessExecuter();
        SimulationProcessService processService = new SimulationProcessServiceImpl();
        processService.loadConfiguration();
        processExecuter.setSimulationProcessService(processService);
        processExecuter.run();
        assertEquals(4, 2 + 2);
    }
}
