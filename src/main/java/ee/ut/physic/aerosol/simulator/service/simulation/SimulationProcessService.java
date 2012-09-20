package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessService {
    public boolean start(SimulationProcess process);
}
