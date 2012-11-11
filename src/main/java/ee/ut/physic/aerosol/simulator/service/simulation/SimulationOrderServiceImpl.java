package ee.ut.physic.aerosol.simulator.service.simulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.*;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.util.JsonExclusionStrategy;
import org.slf4j.Logger;
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

    @Transactional
    public String getOrdersInJson() {
        List<SimulationOrder> orders = getAllSimulationOrders();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new JsonExclusionStrategy(Logger.class))
                .create();
        String jsonOrders = gson.toJson(orders);
        return jsonOrders;
    }

    //Not sure how slow this is
    private void fixManyToOneReferences(SimulationOrder simulationOrder) {
        for (SimulationProcess simulationProcess : simulationOrder.getSimulationProcesses()) {
            simulationProcess.setSimulationOrder(simulationOrder);
            for (SimulationResult simulationResult : simulationProcess.getSimulationResults()) {
                simulationResult.setSimulationProcess(simulationProcess);
                for (SimulationResultValue simulationResultValue : simulationResult.getSimulationResultValues()) {
                    simulationResultValue.setSimulationResult(simulationResult);
                }
            }
            for (SimulationProcessParameter processParameter : simulationProcess.getSimulationProcessParameters()) {
                processParameter.setSimulationProcess(simulationProcess);
            }
        }
        for (SimulationOrderParameter orderParameter : simulationOrder.getSimulationOrderParameters()) {
            orderParameter.setSimulationOrder(simulationOrder);
        }
    }

    @Override
    @Transactional
    public void importOrders(List<SimulationOrder> orders) {
        for (SimulationOrder simulationOrder : orders) {
            fixManyToOneReferences(simulationOrder);
            simulationOrderDao.add(simulationOrder);
        }
    }

    @Override
    @Transactional
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
