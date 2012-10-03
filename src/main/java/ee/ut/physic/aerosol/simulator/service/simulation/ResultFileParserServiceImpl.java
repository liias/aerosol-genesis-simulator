package ee.ut.physic.aerosol.simulator.service.simulation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResult;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationResultValue;

@Service
public class ResultFileParserServiceImpl implements ResultFileParserService {
	final Logger log = LoggerFactory.getLogger(SimulationResultServiceImpl.class);

    @Override
    public Set<SimulationResult> parseResultFile(SimulationProcess process) {
		Set<SimulationResult> results = new HashSet<SimulationResult>();
		try {
			BufferedReader br = readResultFile();
			String strLine;
			String [] firstLine = new String[100];
			while ((strLine = br.readLine()) != null)   {
				// since I will be using names from first line in result, keeping this for future and skipping it
				if(strLine.startsWith("time")) {
					firstLine = strLine.split("\t");
					continue;
				}
				SimulationResult simulationResult = new SimulationResult();	
				simulationResult.setSimulationProcess(process);
				
				int count = 0;
				for(String one : strLine.split("\t")) {
					SimulationResultValue value = new SimulationResultValue();
					value.setSimulationResult(simulationResult);
					value.setName(firstLine[count]);
					value.setValue(one);
					simulationResult.getSimulationResultValues().add(value);
					count ++;
				}
				results.add(simulationResult);
			}
		} catch(Exception e) {
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
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			return br;
		} catch (Exception e) {
			log.error("error", e);
		}
    	return null;
    }
}
