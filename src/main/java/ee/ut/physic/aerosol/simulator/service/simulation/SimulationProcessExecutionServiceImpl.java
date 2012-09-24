package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Service
@Scope("prototype")
@Lazy(value = true)
public class SimulationProcessExecutionServiceImpl implements SimulationProcessExecutionService {

    @Autowired
    private SimulationProcessService simulationProcessService;

    @Autowired
    private ControlFileGenerationService controlFileGenerationService;

    private SimulationProcess simulationProcess;

    @Override
    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    @Override
    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    public void createControlFile(String configPath) {
        controlFileGenerationService.createContent(simulationProcess);
        controlFileGenerationService.saveContentToPath(configPath);
    }

    @Override
    public void run() {
        getSimulationProcess().setState(SimulationProcessState.RUNNING);
        Properties burstAppProperties = Configuration.getInstance().getBurstAppProperties();
        //long startTime = System.currentTimeMillis();
        String path = burstAppProperties.getProperty("burstapp.path");
        String fullPath = path + burstAppProperties.getProperty("burstapp.fileName");
        String configPath = path + burstAppProperties.getProperty("burstapp.configPath");
        createControlFile(configPath);

        Process process;
        try {
            System.out.println(path);
            String[] command = {fullPath};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream();
            File dir = new File(path);
            builder.directory(dir);
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //input of java, output of exe file
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if ((line.startsWith("Press ENTER for exit"))) {
                    break;
                }
                if (!line.isEmpty()) {
                    // Just emulate pressing enter after each line
                    process.getOutputStream().write("\n".getBytes());
                    process.getOutputStream().flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exitCode = process.exitValue();
        System.out.println("Exit code: " + exitCode);

        simulationProcessService.setCompleted(simulationProcess);
        //long endTime = System.currentTimeMillis();
    }
}
