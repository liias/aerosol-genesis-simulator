package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SimulationProcessDaoImpl implements SimulationProcessDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SimulationProcess update(SimulationProcess process) {
        return entityManager.merge(process);
    }
}
