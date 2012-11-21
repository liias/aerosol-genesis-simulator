package ee.ut.physic.aerosol.simulator.service.simulation;

import config.InMemorySpringConfiguration;
import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static java.lang.Thread.sleep;

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
