package ee.ut.physic.aerosol.simulator.service.simulation;

import org.springframework.stereotype.Service;

import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;

@Service
public interface ValidationService {

	public void validateOrder(OrderForm orderForm) throws GeneralException;

	public int validateSimulationCount(OrderForm orderForm, int initialCount);
}
