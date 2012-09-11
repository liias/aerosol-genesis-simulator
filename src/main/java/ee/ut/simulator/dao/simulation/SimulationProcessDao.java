package ee.ut.simulator.dao.simulation;

import ee.ut.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessDao {

    public SimulationProcess getById(long id);
}
