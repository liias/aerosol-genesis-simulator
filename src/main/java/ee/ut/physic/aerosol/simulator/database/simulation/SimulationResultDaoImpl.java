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
    public List<SimulationResult> findResultsGroupedByProcess(int minTime, int maxTime) {
       // String query = "select res from SimulationResult res left join fetch res.simulationResultValues where res.time >= :minTime and res.time <= :maxTime order by res.simulationProcess.id asc, res.time asc";
        String query = "select res from SimulationResult res where res.time >= :minTime and res.time <= :maxTime order by res.simulationProcess.id asc, res.time asc";

//        String query = "SELECT res.simulationProcess, sum(val.value) FROM SimulationResult res left join res.id left join SimulationResultValue as val  WHERE res.time >= :minTime AND res.time <= :maxTime GROUP BY res.simulationProcess ORDER BY res.time asc";
        return entityManager.createQuery(query, SimulationResult.class)
                .setParameter("minTime", minTime)
                .setParameter("maxTime", maxTime)
                .getResultList();
    }

/*    public SimulationResult findBestResult(int minTime, int maxTime) {
        return entityManager.createQuery("SELECT o FROM SimulationResult o", SimulationResult.class).getSingleResult();
    }*/

    public List<SimulationResult> findAll() {
        return entityManager.createQuery("SELECT o FROM SimulationResult o", SimulationResult.class).getResultList();
    }
}
