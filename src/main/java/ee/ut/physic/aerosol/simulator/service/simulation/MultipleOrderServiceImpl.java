package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.errors.ParametersExistException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.ui.OrderToolBar;
import ee.ut.physic.aerosol.simulator.ui.dialogs.SimulationIsReadyDialog;
import ee.ut.physic.aerosol.simulator.ui.dialogs.SimulationIsRunningDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MultipleOrderServiceImpl implements MultipleOrderService {
    final Logger logger = LoggerFactory.getLogger(MultipleOrderServiceImpl.class);

    private OrderForm orderForm;
    private OrderToolBar toolbar;
    private ArrayList<ArrayList<String>> orderParams;
    private int currentOrder;
    private boolean running = false;
    @Autowired
    private ValidationService validationService;
    @Autowired
    public SimulationOrderService simulationOrderService;

    @Override
    public void init(OrderForm orderForm, OrderToolBar toolbar, ArrayList<ArrayList<String>> stringSet) {
        this.orderForm = orderForm;
        this.toolbar = toolbar;
        this.orderParams = stringSet;
        this.currentOrder = -1;
        this.running = true;

        try {
            SimulationIsRunningDialog.show();
            simulate();
        } catch (GeneralException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void simulate() throws GeneralException {
        if (orderParams != null && orderParams.size() != currentOrder + 1) {
            currentOrder++;
            logger.info("making order with : " + (currentOrder) + " - " + running);
            toolbar.setSimulationInProcess(true);
            logger.debug("Simulate button pressed");
            try {
                orderForm.setAllParameterValues(orderParams.get(currentOrder));
                validationService.validateOrder(orderForm);
                SimulationOrder simulationOrder = orderForm.createSimulationOrderWithData(toolbar.getComment(), 1);
                simulationOrderService.simulate(simulationOrder);
            } catch (ParametersExistException e) {
                toolbar.setSimulationInProcess(false);
                throw new GeneralException(e.getMessage());
            } catch (GeneralException e) {
                toolbar.setSimulationInProcess(false);
                throw new GeneralException(e.getMessage());
            }
        } else {
            if (running) {
                SimulationIsReadyDialog.show();
                running = false;
            }
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
