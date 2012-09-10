package ee.ut.simulator.service;

import ee.ut.simulator.domain.simulation.parameter.ParametersConfiguration;

import javax.annotation.PostConstruct;

public interface SimulationOrderService {
    @PostConstruct
    public void generateDefaultParameters();

    ParametersConfiguration getParametersConfiguration();

    void setParametersConfiguration(ParametersConfiguration parametersConfiguration);
}
