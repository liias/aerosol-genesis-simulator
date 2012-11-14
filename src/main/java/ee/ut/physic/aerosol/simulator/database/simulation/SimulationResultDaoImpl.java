package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SimulationResultDaoImpl implements SimulationResultDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SimulationResult> getAllResults(List<Long> processIds, int minTime, int maxTime) {
        String query = "select res from SimulationResult res where res.simulationProcess.id IN :processIds AND res.time >= :minTime and res.time <= :maxTime order by res.simulationProcess.id asc, res.time asc";
        return entityManager.createQuery(query, SimulationResult.class)
                .setParameter("processIds", processIds)
                .setParameter("minTime", minTime)
                .setParameter("maxTime", maxTime)
                .getResultList();
    }
}
