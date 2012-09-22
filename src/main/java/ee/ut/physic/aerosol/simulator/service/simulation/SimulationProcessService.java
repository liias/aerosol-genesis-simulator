package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessService {
    public void start(SimulationProcess process);

    public void setFailed();

    public void setCompleted();
}
