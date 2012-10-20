package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;

public interface SimulationOrderService {
    public void simulate(SimulationOrder simulationOrder);

    public void stopSimulation(SimulationOrder simulationOrder);
}
