package ee.ut.simulator.service;

import ee.ut.simulator.service.simulation.SimulationOrderService;
import ee.ut.simulator.service.simulation.SimulationOrderServiceImpl;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SimulationOrderServiceTest {

    @Test
    public void testGenerateDefaultParameters() throws Exception {
        SimulationOrderService simulationOrderService = new SimulationOrderServiceImpl();
        simulationOrderService.generateDefaultParameters();
        int numberOfParameters = simulationOrderService.getParametersConfiguration().getParameterDefinitions().size();
        assertEquals(3, numberOfParameters);
    }


}
