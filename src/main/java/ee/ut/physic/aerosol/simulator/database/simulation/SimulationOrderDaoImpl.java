package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SimulationOrderDaoImpl implements SimulationOrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(SimulationOrder order) {
        entityManager.persist(order);
    }

    @Override
    public SimulationOrder update(SimulationOrder order) {
        return entityManager.merge(order);
    }
}
