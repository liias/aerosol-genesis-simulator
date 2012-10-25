package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationResultService {
    void addResultsForProcess(SimulationProcess process);

    void compareWithReference();
}
