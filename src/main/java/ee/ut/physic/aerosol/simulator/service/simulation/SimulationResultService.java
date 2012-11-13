package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

import java.util.HashMap;
import java.util.List;

public interface SimulationResultService {
    void addResultsForProcess(SimulationProcess process);

    List<HashMap<String, Double>> findBestResults();
    String getResultsFileContent(SimulationProcess process);
    String findBestResultsAndGenerateFileContent();
}
