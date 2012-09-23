package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;

public interface SimulationOrderDao {
    void add(SimulationOrder order);

    SimulationOrder update(SimulationOrder order);
}
