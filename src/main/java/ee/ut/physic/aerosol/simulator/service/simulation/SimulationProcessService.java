package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessService {
    public void startInNewThread(SimulationProcess process);

    public void setFailed(SimulationProcess process);

    public void setCompleted(SimulationProcess process);

    public void stop();
}
