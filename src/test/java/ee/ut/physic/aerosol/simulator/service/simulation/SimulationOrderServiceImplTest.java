package ee.ut.physic.aerosol.simulator.service.simulation;

import config.InMemorySpringConfiguration;
import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertEquals;

public class SimulationOrderServiceImplTest {

    SimulationOrderServiceImpl simulationOrderService;

    @Before
    public void setUp() throws Exception {
        Locale locale = Locale.getDefault();
        locale = Locale.ENGLISH;
        Locale.setDefault(locale);

        // Instantiate configuration
        Configuration.getInstance();
        ApplicationContextLoader loader = ApplicationContextLoader.getInstance();
        loader.loadApplicationContext(InMemorySpringConfiguration.class);
        simulationOrderService = new SimulationOrderServiceImpl();
        loader.injectDependencies(simulationOrderService);
    }


    public void tearDown() throws Exception {

    }

    @Test
    @Ignore
    public void testSimulate() throws Exception {
        SimulationOrder order = createDefaultOrder();
        simulationOrderService.simulate(order);

        // Wait for your code to complete
        sleep(10);

        //        setInProcess();
        //generateProcessesForOrder(simulationOrder);
        //simulationOrderDao.add(simulationOrder);
        //simulationProcessService.startInNewThread(simulationOrder.getNextNotStartedProcess());
    }

    @Test
    public void testFixNadyktoParametersWithYu1LessThanYu2() throws Exception {
        SimulationProcess process = createProcessWithNadyktoValues(1.4, 1.5);
        simulationOrderService.fixNadyktoParameters(process);
        List<SimulationProcessParameter> params = process.getSimulationProcessParameters();
        assertEquals(1.51, params.get(0).getFreeAirValue());
        assertEquals(1.5, params.get(1).getFreeAirValue());
    }

    @Test
    public void testFixNadyktoParametersWithYu1EqualToYu2() throws Exception {
        SimulationProcess process = createProcessWithNadyktoValues(1.4, 1.4);
        simulationOrderService.fixNadyktoParameters(process);
        List<SimulationProcessParameter> params = process.getSimulationProcessParameters();
        assertEquals(1.41, params.get(0).getFreeAirValue());
        assertEquals(1.4, params.get(1).getFreeAirValue());
    }

    @Test
    public void testFixNadyktoParametersWithNothingToDo() throws Exception {
        SimulationProcess process = createProcessWithNadyktoValues(1.5, 1.4);
        simulationOrderService.fixNadyktoParameters(process);
        List<SimulationProcessParameter> params = process.getSimulationProcessParameters();
        assertEquals(1.5, params.get(0).getFreeAirValue());
        assertEquals(1.4, params.get(1).getFreeAirValue());
    }

    private SimulationProcess createProcessWithNadyktoValues(double yu1, double yu2) {
        SimulationProcess process = new SimulationProcess();
        ParameterDefinition yu1Definition = Configuration.getInstance().getDefinitionByName("Yu1");
        ParameterDefinition yu2Definition = Configuration.getInstance().getDefinitionByName("Yu2");
        SimulationProcessParameter nadykto1Param = new SimulationProcessParameter(yu1Definition);
        SimulationProcessParameter nadykto2Param = new SimulationProcessParameter(yu2Definition);
        nadykto1Param.setFreeAirValue(yu1);
        nadykto2Param.setFreeAirValue(yu2);
        process.addParameter(nadykto1Param);
        process.addParameter(nadykto2Param);
        return process;
    }

    private SimulationOrder createDefaultOrder() {
        SimulationOrder order = new SimulationOrder();
        order.setNumberOfProcesses(1);
        order.setComment("Kommentaar");

        Collection<SimulationOrderParameter> parameters = new ArrayList<SimulationOrderParameter>(30);
        //TODO: somehow import from file or something, maybe
        order.setSimulationOrderParameters(parameters);
        return order;
    }
}
