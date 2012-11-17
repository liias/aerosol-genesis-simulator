package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SimulationProcessDaoImpl implements SimulationProcessDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SimulationProcess update(SimulationProcess process) {
        return entityManager.merge(process);
    }

    @Override
    public SimulationProcess getById(long id) {
        return entityManager.find(SimulationProcess.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> getProcessIdsWhereProcessTimeLessOrEqualThan(long time) {
        Query query = entityManager.createQuery("SELECT param.simulationProcess.id FROM SimulationProcessParameter param where param.name='time' AND param.freeAirValue >= 100");
        return (List<Long>) query.getResultList();
    }

    @Override
    public List<SimulationProcess> getAllByHash(String hash) {
        String query = "select p from SimulationProcess p where p.parametersHash=:hash";
        return entityManager.createQuery(query, SimulationProcess.class)
                .setParameter("hash", hash)
                .getResultList();
    }
}