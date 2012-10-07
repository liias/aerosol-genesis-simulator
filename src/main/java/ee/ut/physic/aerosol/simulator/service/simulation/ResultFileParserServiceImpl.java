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
        String[] values = line.split("\t");
        int i = 0;

        SimulationResult result = new SimulationResult();
        result.setSimulationProcess(process);
        Integer time = Integer.parseInt(values[0]);
        result.setTime(time);
        for (String value : values) {
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

    // number can be 1-999
    private String resultFileNumberToFileName(int number) {
        // with zero-padding with length 3
        String numberAsString = String.format("%03d", number);
        return "T" + numberAsString + ".xl";
    }

    @Override
    public BufferedReader readResultFile() {
        Properties burstAppProperties = Configuration.getInstance().getBurstAppProperties();
        String path = burstAppProperties.getProperty("burstapp.path");
        Integer resultFileNumber = 1;
        String outputFilename = resultFileNumberToFileName(resultFileNumber);
        String fullPath = path + outputFilename;
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
