package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResultValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Service
public class ResultFileParserServiceImpl implements ResultFileParserService {
    final Logger log = LoggerFactory.getLogger(ResultFileParserServiceImpl.class);

    private SimulationResultValue createResultValue(SimulationResult result, String name, String value) {
        SimulationResultValue resultValue = new SimulationResultValue();
        resultValue.setName(name);
        resultValue.setValue(value);
        resultValue.setSimulationResult(result);
        result.addSimulationResultValue(resultValue);
        return resultValue;
    }

    private SimulationResult createSimulationResultFromLine(SimulationProcess process, String[] valueNames, String line) {
        SimulationResult result = new SimulationResult();
        result.setSimulationProcess(process);
        int i = 0;
        for (String value : line.split("\t")) {
            createResultValue(result, valueNames[i], value);
            i++;
        }
        return result;
    }

    // Parses result file into SimulationResults
    @Override
    public Set<SimulationResult> parseResultFile(SimulationProcess process) {
        Set<SimulationResult> results = new HashSet<SimulationResult>();
        try {
            BufferedReader br = readResultFile();
            // Read first line from buffer
            String[] valueNames = br.readLine().split("\t");
            String line;
            // Read all other files from buffer
            while ((line = br.readLine()) != null) {
                SimulationResult simulationResult = createSimulationResultFromLine(process, valueNames, line);
                results.add(simulationResult);
            }
            br.close();
        } catch (Exception e) {
            log.error("error", e);
        }
        return results;
    }

    @Override
    public BufferedReader readResultFile() {
        Properties burstAppProperties = Configuration.getInstance().getBurstAppProperties();
        String path = burstAppProperties.getProperty("burstapp.path");
        String fullPath = path + burstAppProperties.getProperty("burstapp.outputFile");
        try {
            FileInputStream stream = new FileInputStream(fullPath);
            DataInputStream in = new DataInputStream(stream);
            return new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            log.error("error", e);
        }
        return null;
    }
}
