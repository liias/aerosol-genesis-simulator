package ee.ut.physic.aerosol.simulator.errors;

import ee.ut.physic.aerosol.simulator.ui.Message;

public class GeneralException extends RuntimeException {
	private static final long serialVersionUID = -2587046796980550060L;

	public GeneralException(String message) {
		super(message);
		Message.showError(message);
	}
}
