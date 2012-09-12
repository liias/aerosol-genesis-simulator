package ee.ut.simulator.service.simulation;

import ee.ut.simulator.domain.simulation.SimulationOrder;
import ee.ut.simulator.domain.simulation.parameter.ParametersConfiguration;

import javax.annotation.PostConstruct;

public interface SimulationOrderService {
    @PostConstruct
    public void generateDefaultParameters();

    ParametersConfiguration getParametersConfiguration();

    void setParametersConfiguration(ParametersConfiguration parametersConfiguration);

    public void add(SimulationOrder order);

    void listProcesses();

    public SimulationOrder getNewOrder();
}
