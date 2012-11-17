package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

import java.util.List;

public interface SimulationProcessDao {
    SimulationProcess update(SimulationProcess process);

    SimulationProcess getById(long id);

    List<Long> getProcessIdsWhereProcessTimeLessOrEqualThan(long time);

    SimulationProcess getByHash(String hash);
}
