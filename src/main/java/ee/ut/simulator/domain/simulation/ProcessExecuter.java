package ee.ut.simulator.domain.simulation;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProcessExecuter implements Runnable {

    Properties configuration;

    public ProcessExecuter(Properties configuration) {
        setConfiguration(configuration);
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run() {
        //long startTime = System.currentTimeMillis();
        String path = getConfiguration().getProperty("burstsimulator.path");
        String fullPath = path + getConfiguration().getProperty("burstsimulator.fileName");

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
        //long endTime = System.currentTimeMillis();
        //jobManager.onExit(this);
    }
}
