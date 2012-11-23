package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.ui.OrderForm;
import ee.ut.physic.aerosol.simulator.ui.OrderToolBar;

import java.util.ArrayList;

public interface MultipleOrderService {

    public void init(OrderForm orderForm, OrderToolBar toolbar, ArrayList<ArrayList<String>> stringSet);

    public void simulate() throws GeneralException;

    public boolean isRunning();
}
