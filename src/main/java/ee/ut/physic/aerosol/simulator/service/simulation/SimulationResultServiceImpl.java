package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationResultServiceImpl implements SimulationResultService {
    final Logger logger = LoggerFactory.getLogger(SimulationResultServiceImpl.class);

    @Autowired
    private ResultFileParserService resultFileParserService;

    @Override
    public void addResultsForProcess(SimulationProcess process) {
        resultFileParserService.parseResultFile();
    }
}
