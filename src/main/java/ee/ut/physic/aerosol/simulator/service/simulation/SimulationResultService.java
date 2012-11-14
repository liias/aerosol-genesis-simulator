package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public interface SimulationResultService {
    void addResultsForProcess(SimulationProcess process);
    String getResultsFileContent(SimulationProcess process);
    String findBestResultsAndGenerateFileContent(int numberOfRatings);
    List<HashMap<String, Double>> findBestResults(int numberOfRatings);
}
