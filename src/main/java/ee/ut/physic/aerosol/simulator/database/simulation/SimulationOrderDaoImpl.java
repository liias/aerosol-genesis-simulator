package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<SimulationOrder> getAll() {
        Query query = entityManager.createQuery("SELECT so FROM SimulationOrder so");
        return (List<SimulationOrder>) query.getResultList();
    }
}
