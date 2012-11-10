package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;

import java.util.List;

public interface SimulationOrderService {
    public void simulate(SimulationOrder simulationOrder);

    public void stopSimulation(SimulationOrder simulationOrder);

    public void setOrderForm(OrderForm orderForm);

    public void setCompleted();

    public List<SimulationOrder> getAllSimulationOrders();

    public void springAutoInjectionTest();
}
