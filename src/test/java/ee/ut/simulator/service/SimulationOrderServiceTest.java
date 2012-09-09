package ee.ut.simulator.service;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SimulationOrderServiceTest {

    @Test
    public void testGenerateDefaultParameters() throws Exception {
        SimulationOrderService simulationOrderService = new SimulationOrderServiceImpl();
        simulationOrderService.generateDefaultParameters();
        int numberOfParameters = simulationOrderService.getDefaultParameters().getParameters().size();
        assertEquals(3, numberOfParameters);
    }


}
