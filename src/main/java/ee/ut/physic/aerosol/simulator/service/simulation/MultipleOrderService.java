package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.errors.ParametersExistException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.ui.OrderToolBar;

import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleOrderService {
    
	final Logger logger = LoggerFactory.getLogger(MultipleOrderService.class);

	private OrderForm orderForm;
	@Autowired
	private ValidationService validationService;
	@Autowired
	public SimulationOrderService simulationOrderService;



	public void simulate(OrderToolBar toolbar, Set<ArrayList<String>> stringSet) throws GeneralException {
		toolbar.setSimulationInProcess(true);
		logger.debug("Simulate button pressed");
		try {
			orderForm.setAllParameterValues(stringSet.iterator().next());
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
	
    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }
}

