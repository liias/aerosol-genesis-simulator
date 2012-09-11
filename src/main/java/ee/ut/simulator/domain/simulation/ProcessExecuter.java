package ee.ut.simulator.domain.simulation;

import java.io.*;
import java.util.Properties;

public class ProcessExecuter implements Runnable {
    public Properties loadConfig() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("properties/config.properties");
        prop.load(in);
        in.close();
        return prop;
    }

    @Override
    public void run() {
        //long startTime = System.currentTimeMillis();
        String path;
        String fullPath;
        try {
            Properties config = loadConfig();
            path = config.getProperty("burstsimulator.path");
            fullPath = path + config.getProperty("burstsimulator.fileName");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
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
