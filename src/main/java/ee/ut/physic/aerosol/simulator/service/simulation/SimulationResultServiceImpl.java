package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.database.simulation.SimulationProcessDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SimulationResultServiceImpl implements SimulationResultService {
    @Autowired
    private ResultFileParserService resultFileParserService;

    @Autowired
    private SimulationProcessDao simulationProcessDao;

    @Transactional
    @Override
    public void addResultsForProcess(SimulationProcess process) {
        Set<SimulationResult> simulationResults = resultFileParserService.parseResultFile(process);
        process.setSimulationResults(simulationResults);
        simulationProcessDao.update(process);
    }
}
