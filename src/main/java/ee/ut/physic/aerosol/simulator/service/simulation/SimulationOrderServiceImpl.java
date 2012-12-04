package ee.ut.physic.aerosol.simulator.service.simulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.*;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.errors.ParametersExistException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.util.JsonExclusionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SimulationOrderServiceImpl implements SimulationOrderService {
    final Logger logger = LoggerFactory.getLogger(SimulationOrderServiceImpl.class);

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
    public void simulate(SimulationOrder simulationOrder) throws ParametersExistException {
        setInProcess();
        generateProcessesForOrder(simulationOrder);
        simulationOrderDao.add(simulationOrder);
        simulationProcessService.startInNewThread(simulationOrder.getNextNotStartedProcess());
    }

    public void generateProcessesForOrder(SimulationOrder order) throws ParametersExistException {
        if (order.getNumberOfProcesses() == 1) {
            //If only one process is made, which means no max values were set, then throw the exception, so user will know
            generateProcess(order);
        } else {
            //generate process for numberOfProcesses times
            for (int i = 0; i < order.getNumberOfProcesses(); i++) {
                try {
                    generateProcess(order);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                } catch (ParametersExistException e) {
                    //If multiple processes were to be generated, then don't tell user that this process is skipped
                    logger.warn(e.getMessage());
                    order.setNumberOfProcesses(order.getNumberOfProcesses() - 1);
                }
            }
        }
    }

    private void generateProcess(SimulationOrder order) throws ParametersExistException {
        SimulationProcess generatedProcess = order.generateProcess();
        String hash = generatedProcess.getParametersHash();
        if (simulationProcessService.isFinishedProcessWithHashAlreadyExisting(hash)) {
            throw new ParametersExistException("Simulation process with these parameters already exists in database");
        }
        fixNadyktoParameters(generatedProcess);
        order.addProcess(generatedProcess);
    }

    protected void fixNadyktoParameters(SimulationProcess generatedProcess) {
        Double nadykto1Value = null;
        Double nadykto2Value = null;
        SimulationProcessParameter nadykto1Parameter = null;
        SimulationProcessParameter nadykto2Parameter = null;
        for (SimulationProcessParameter processParameter : generatedProcess.getSimulationProcessParameters()) {
            if (processParameter.isNadykto1()) {
                nadykto1Parameter = processParameter;
                nadykto1Value = processParameter.getFreeAirValue();
            } else if (processParameter.isNadykto2()) {
                nadykto2Parameter = processParameter;
                nadykto2Value = processParameter.getFreeAirValue();
            }
        }
        // just fix the nadykto1
        if (nadykto1Value != null && nadykto2Value != null) {
            if (nadykto1Value <= nadykto2Value) {
                if (nadykto2Value < 2) {
                    //ok, we must fix nadykto2 as well, see https://agsimulator.teamlab.com/products/projects/tasks.aspx?prjID=312726&id=2144269
                    nadykto2Parameter.setFreeAirValue(2.0);
                    nadykto1Parameter.setFreeAirValue(2.01);
                } else {
                    nadykto1Parameter.setFreeAirValue(nadykto2Value + 0.01);
                }
            }
        }
    }

    @Override
    public void stopSimulation(SimulationOrder simulationOrder) {
        simulationProcessService.stop();
    }

    @Override
    public void stopCurrentProcess(SimulationOrder simulationOrder) {
        simulationProcessService.stopCurrentProcess();
    }

    @Override
    public void stopCurrentProcessAsFailed(SimulationOrder simulationOrder) {
        simulationProcessService.stopCurrentProcessAsFailed();
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
        logger.debug("Order completed");
        if (getOrderForm() == null) {
            return;
        }
        getOrderForm().setSimulationInProcess(false);
    }

    @Transactional
    public String getOrdersInJson() {
        List<SimulationOrder> orders = getAllSimulationOrders();
        Gson gson = new GsonBuilder().setExclusionStrategies(new JsonExclusionStrategy(Logger.class)).create();
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

    @Override
    public SimulationOrder getById(long id) {
        return simulationOrderDao.getById(id);
    }

    @Transactional
    @Override
    public Map<String, Map<String, String>> getParametersMapById(long id) throws GeneralException {
        SimulationOrder order = getById(id);
        if (order == null) {
            throw new GeneralException("There is no simulation process with id " + id);
        }
        return order.getParametersMap();
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }
}
