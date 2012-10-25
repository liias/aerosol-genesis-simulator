package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;

import java.io.BufferedReader;
import java.util.List;
import java.util.Set;

public interface ResultFileParserService {
    Set<SimulationResult> parseResultFile(SimulationProcess process);
    List<SimulationResult> parseReferenceResults(String fileName);

    BufferedReader readResultFile(Integer resultFileNumber);
}
