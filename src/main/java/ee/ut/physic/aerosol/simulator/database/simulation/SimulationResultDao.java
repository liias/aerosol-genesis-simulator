package ee.ut.physic.aerosol.simulator.database.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;

import java.util.List;

public interface SimulationResultDao {
    //criteria: number of results must be at least the same as in reference file (or time at least e.g 100)
    List<SimulationResult> findResultsGroupedByProcess(int minTime, int maxTime);
}
