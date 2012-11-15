package ee.ut.physic.aerosol.simulator.errors;

import ee.ut.physic.aerosol.simulator.ui.Message;

public class GeneralException extends Exception {

    public GeneralException(String message) {
        super(message);
        Message.showError(message);
    }
}
