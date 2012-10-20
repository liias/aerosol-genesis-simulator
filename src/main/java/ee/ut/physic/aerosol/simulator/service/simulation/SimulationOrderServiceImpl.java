package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.database.simulation.SimulationOrderDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulationOrderServiceImpl implements SimulationOrderService {

    @Autowired
    private SimulationOrderDao simulationOrderDao;

    @Autowired
    private SimulationProcessService simulationProcessService;

    public void save(SimulationOrder simulationOrder) {
        simulationOrderDao.add(simulationOrder);
    }

    @Override
    @Transactional
    public void simulate(SimulationOrder simulationOrder) {
        simulationOrder.generateProcesses();
        simulationOrderDao.add(simulationOrder);
        simulationProcessService.startInNewThread(simulationOrder.getNextNotStartedProcess());
    }

    @Override
    public void stopSimulation(SimulationOrder simulationOrder) {
        simulationProcessService.stop();
    }
}
