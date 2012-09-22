package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;

public interface ControlFileGenerationService {

    //private String content;
    public void createContent(SimulationOrder simulationOrder);
    public void saveContentToPath(String path);
}
