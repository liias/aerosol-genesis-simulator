package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;

public interface SimulationProcessExecutionService extends Runnable {
    void setSimulationProcess(SimulationProcess simulationProcess);

    SimulationProcess getSimulationProcess();
}
