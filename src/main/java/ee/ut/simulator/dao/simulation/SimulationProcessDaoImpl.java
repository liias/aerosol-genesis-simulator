package ee.ut.simulator.dao.simulation;

import ee.ut.simulator.domain.simulation.SimulationProcess;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SimulationProcessDaoImpl implements SimulationProcessDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SimulationProcess getById(long id) {
        return entityManager.find(SimulationProcess.class, id);
    }
}
