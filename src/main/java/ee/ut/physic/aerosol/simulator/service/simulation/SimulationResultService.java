package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public interface SimulationResultService {
    void addResultsForProcess(SimulationProcess process);
    String generateBestResultsFileAndSaveBestProcessId(int numberOfRatings) throws GeneralException;
    List<HashMap<String, Double>> findBestResults(int numberOfRatings) throws GeneralException;
    String getResultsFileContent(SimulationProcess process);
    String getResultsFileContent(Long id) throws GeneralException;
}
