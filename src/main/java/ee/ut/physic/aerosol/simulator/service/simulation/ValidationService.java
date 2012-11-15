package ee.ut.physic.aerosol.simulator.service.simulation;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;

@Service
public class ValidationService {
	final Logger log = LoggerFactory.getLogger(ValidationService.class);
	
    public void validateOrder(OrderForm orderForm) throws GeneralException {
    	for(String title : orderForm.getAllParameterValues().keySet()) {
    		Map<String, String> valueMap = orderForm.getAllParameterValues().get(title);
    		for(String name : valueMap.keySet()) {  
    			if(("forestMin".equals(name) || "freeAirMin".equals(name)) && valueMap.get(name).length() == 0) {
    				throw new GeneralException("Fill all fields please (Minimum)");
    			}
    		}
    	}
    }
}