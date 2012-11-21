package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.*;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.errors.ParametersExistException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.ui.OrderToolBar;
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
    @Autowired
    private ValidationService validationService;
    @Autowired
    public SimulationOrderService simulationOrderService;

    @Override
    public void init(OrderForm orderForm, OrderToolBar toolbar, ArrayList<ArrayList<String>> stringSet) {
        this.orderForm = orderForm;
        this.toolbar = toolbar;
        this.orderParams = stringSet;

        try {
            simulate(0);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void simulate(int orderNumber) throws GeneralException {
        toolbar.setSimulationInProcess(true);
        logger.debug("Simulate button pressed");
        try {
            orderForm.setAllParameterValues(orderParams.get(orderNumber));
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
    }

}
