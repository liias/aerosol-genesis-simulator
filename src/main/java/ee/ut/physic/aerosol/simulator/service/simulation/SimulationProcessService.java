package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

import java.util.Map;

public interface SimulationProcessService {
    public void startInNewThread(SimulationProcess process);

    public void setFailed(SimulationProcess process);

    public void setCompleted(SimulationProcess process);

    public void stop();

    public SimulationProcess getById(long id);

    public Map<String, Map<String, String>> getParametersMapById(long id);
}
