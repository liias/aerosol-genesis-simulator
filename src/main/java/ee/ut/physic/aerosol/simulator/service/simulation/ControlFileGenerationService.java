package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface ControlFileGenerationService {
    public void saveContentToPath(String path);

    void createContent(SimulationProcess simulationProcess);
}
