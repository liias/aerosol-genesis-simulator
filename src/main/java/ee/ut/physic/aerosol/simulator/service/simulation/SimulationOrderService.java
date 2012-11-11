package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;

import java.util.List;
import java.util.Map;

public interface SimulationOrderService {
    public void simulate(SimulationOrder simulationOrder);

    public void stopSimulation(SimulationOrder simulationOrder);

    public void setOrderForm(OrderForm orderForm);

    public void setCompleted();

    public List<SimulationOrder> getAllSimulationOrders();
    public String getOrdersInJson();

    public void importOrders(List<SimulationOrder> orders);
    public void springAutoInjectionTest();

    public SimulationOrder getById(long id);

    public Map<String, Map<String, String>> getParametersMapById(long id);
}
