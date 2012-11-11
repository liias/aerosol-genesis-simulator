package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;

import java.util.List;

public interface SimulationOrderDao {
    void add(SimulationOrder order);

    SimulationOrder update(SimulationOrder order);

    List<SimulationOrder> getAll();

    SimulationOrder getById(long id);
}
