package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimulationOrderServiceImpl implements SimulationOrderService {

    private OrderForm orderForm;

    @Autowired
    private SimulationOrderDao simulationOrderDao;

    @Autowired
    private SimulationProcessService simulationProcessService;

    public void save(SimulationOrder simulationOrder) {
        simulationOrderDao.add(simulationOrder);
    }

    @Override
    @Transactional
    public void simulate(SimulationOrder simulationOrder) {
        setInProcess();
        simulationOrder.generateProcesses();
        simulationOrderDao.add(simulationOrder);
        simulationProcessService.startInNewThread(simulationOrder.getNextNotStartedProcess());
    }

    @Override
    public void stopSimulation(SimulationOrder simulationOrder) {
        simulationProcessService.stop();
    }

    @Override
    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }


    public void setInProcess() {
        if (getOrderForm() == null) {
            return;
        }
        getOrderForm().setSimulationInProcess(true);
    }

    @Override
    public void setCompleted() {
        if (getOrderForm() == null) {
            return;
        }
        getOrderForm().setSimulationInProcess(false);
    }

    @Override
    public List<SimulationOrder> getAllSimulationOrders() {
        return simulationOrderDao.getAll();
    }

    @Override
    public void springAutoInjectionTest() {
        System.out.println("Spring auto-injection works!");
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }
}
