package ee.ut.physic.aerosol.simulator.service.simulation;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.physic.aerosol.simulator.database.simulation.SimulationProcessDao;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;

@Service
public class SimulationResultServiceImpl implements SimulationResultService {
    final Logger log = LoggerFactory.getLogger(SimulationResultServiceImpl.class);

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
