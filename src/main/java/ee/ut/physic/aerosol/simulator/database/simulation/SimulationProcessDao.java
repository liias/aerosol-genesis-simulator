package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessDao {
    SimulationProcess update(SimulationProcess process);
    SimulationProcess getById(long id);
}
