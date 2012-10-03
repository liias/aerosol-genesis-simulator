package ee.ut.physic.aerosol.simulator.service.simulation;

import java.io.BufferedReader;
import java.util.Set;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;

public interface ResultFileParserService {
	Set<SimulationResult> parseResultFile(SimulationProcess process);
    BufferedReader readResultFile();
}
