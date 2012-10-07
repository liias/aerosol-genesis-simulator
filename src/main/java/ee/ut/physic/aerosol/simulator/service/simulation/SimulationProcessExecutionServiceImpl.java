package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    final Logger log = LoggerFactory.getLogger(SimulationProcessExecutionServiceImpl.class);

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


    private void writeToConsole(Process process, String text) throws IOException {
        process.getOutputStream().write((text + "\n").getBytes());
        process.getOutputStream().flush();
    }

    private void writeEnterToConsole(Process process) throws IOException {
        writeToConsole(process, "");
    }


    // We can't use readLine() reliably, because Burstapp mostly calls write (instead of writeln) before read(),
    // so there are not line ending and our readLine() will never end, because burstapp waits for input.
    // We could read every character, but all these error messages are so different plain-text it's ugly.
    // So we pre-fill buffer with:
    // 1) the result file number
    // 2) 5 newlines (simulates pressing ENTER, also suits for selecting y from y/n as y is default when only
    //    ENTER is pressed). We can add extra ENTERs, these shouldn't be a problem.
    //Normal lines wanting input (with our answer):
    /*    Please write number 1..999 for output files: <1>
        T001.xl exists, owerwrite (y/n)? <ENTER>
        L001.txt exists, owerwrite (y/n)? <ENTER>
        Press ENTER for exit! <ENTER>
    */
    private void preFillBurstAppInput(Process process, Integer fileNumber) throws IOException {
        writeToConsole(process, fileNumber.toString());
        writeEnterToConsole(process);
        writeEnterToConsole(process);
        writeEnterToConsole(process);
        writeEnterToConsole(process);
        writeEnterToConsole(process);
    }

    @Override
    public void run() {
        getSimulationProcess().setState(SimulationProcessState.RUNNING);
        Properties burstAppProperties = Configuration.getInstance().getBurstAppProperties();
        //long startTime = System.currentTimeMillis();
        String path = burstAppProperties.getProperty("burstapp.path");
        String fullPath = path + burstAppProperties.getProperty("burstapp.fileName");
        String configPath = path + burstAppProperties.getProperty("burstapp.configPath");
        // Could be 1-999
        Integer resultFileNumber = 1;
        String uniqueSuccessLineStart = "Press ENTER for exit";
        //String newFullPath = Configuration.getInstance().getFullPath();
        createControlFile(configPath);
        // I think it is currently singleton, so we could set Process process as a class field only when we wont run processes
        // simultaneously
        Process process;
        try {
            log.debug("Path for app is {}", path);
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
        boolean is_success = false;
        try {
            preFillBurstAppInput(process, resultFileNumber);
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if ((line.startsWith(uniqueSuccessLineStart))) {
                    is_success = true;
                }
            }
            process.waitFor();
            int exitCode = process.exitValue();
            log.debug("Burst Simulator exit code: {}", exitCode);
        } catch (IOException e) {
            log.debug(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        if (is_success) {
            simulationProcessService.setCompleted(simulationProcess);
        } else {
            simulationProcessService.setFailed(simulationProcess);
        }
    }
}
