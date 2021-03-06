package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

import java.util.List;

public interface SimulationProcessDao {
    SimulationProcess update(SimulationProcess process);

    SimulationProcess getById(long id);

    List<Long> getProcessIdsWhereProcessTimeMoreOrEqualThan(double time);

    List<SimulationProcess> getAllByHash(String hash);
}
