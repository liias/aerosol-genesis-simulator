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

import java.io.*;


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

    private Process burstAppProcess;

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
        //TODO: update in db maybe
        getSimulationProcess().setState(SimulationProcessState.RUNNING);
        String path = Configuration.getInstance().getBurstSimulatorDirPath();
        String fullPath = path + Configuration.getInstance().getBurstSimulatorFileName();
        String configFilePath = path + "burstcontrol.txt";
        // Could be 1-999
        Integer resultFileNumber = getSimulationProcess().getResultFileNumber();
        String uniqueSuccessLineStart = "Press ENTER for exit";
        createControlFile(configFilePath);
        // I think it is currently singleton, so we could set Process process as a class field only when we wont run processes
        // simultaneously
        //Process process;
        try {
            log.debug("Path for app is {}", path);
            String[] command = {fullPath};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream();
            File dir = new File(path);
            builder.directory(dir);
            burstAppProcess = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //input of java, output of exe file
        BufferedReader input;
        InputStream inputStream = burstAppProcess.getInputStream();
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "IBM775");
        } catch (UnsupportedEncodingException e) {
            log.debug("unsupported encoding");
            inputStreamReader = new InputStreamReader(inputStream);
        }
        input = new BufferedReader(inputStreamReader);
        String line;
        SimulationProcessState stopReason = SimulationProcessState.FAILED;
        try {
            preFillBurstAppInput(burstAppProcess, resultFileNumber);
            //We must allow InterruptedException, waitFor allows that
            burstAppProcess.waitFor();
            //TODO: But make sure that the buffer doesn't get full with time 1440 (in which case it would be stuck in waitFor())
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if ((line.startsWith(uniqueSuccessLineStart))) {
                    stopReason = SimulationProcessState.FINISHED;
                    break;
                }
            }
            int exitCode = burstAppProcess.exitValue();
            log.debug("Burst Simulator exit code: {}", exitCode);
        } catch (IOException e) {
            log.debug(e.getMessage());
            stopReason = SimulationProcessState.CANCELED;
        } catch (InterruptedException e) {
            log.warn("Thread was interrupted");
            log.warn(e.getMessage());
            killBurstAppProcess();
            stopReason = SimulationProcessState.CANCELED;
        }
        try {
            input.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        endProcessWithReason(stopReason);
    }

    private void endProcessWithReason(SimulationProcessState stopReason) {
        switch (stopReason) {
            case FINISHED:
                simulationProcessService.setCompleted(simulationProcess);
                break;
            case FAILED:
                simulationProcessService.setFailed(simulationProcess);
                break;
            case CANCELED:
                simulationProcessService.setCanceled(simulationProcess);
        }
    }

    public Process getBurstAppProcess() {
        return burstAppProcess;
    }

    public void killBurstAppProcess() {
        burstAppProcess.destroy();
    }
}

