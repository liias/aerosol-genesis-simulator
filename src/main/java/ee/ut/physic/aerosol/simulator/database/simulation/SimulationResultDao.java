package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;

import java.util.List;

public interface SimulationResultDao {
    /**
     * Gets all results between minTime and maxTime which belong to process in processIds
     *
     * @param processIds list of process id-s to search from
     * @param minTime    minimum time the result needs to belong
     * @param maxTime    maximum time the result needs to belong to
     * @return all valid results
     */
    List<SimulationResult> getAllResults(List<Long> processIds, int minTime, int maxTime);
}
