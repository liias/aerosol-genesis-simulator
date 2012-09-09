package ee.ut.simulator.service;

import ee.ut.simulator.domain.simulation.parameter.DefaultParameters;

public interface SimulationOrderService {
    public void generateDefaultParameters();

    DefaultParameters getDefaultParameters();

    void setDefaultParameters(DefaultParameters defaultParameters);
}
