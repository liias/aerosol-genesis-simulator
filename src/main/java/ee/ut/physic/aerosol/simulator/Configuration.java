package ee.ut.physic.aerosol.simulator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Singleton called on startup.
 */
public class Configuration {
    final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static Configuration instance = new Configuration();
    private ParametersConfiguration parametersConfiguration;
    private Properties userSettings;
    private String etcPath;
    private String burstSimulatorDirPath;
    private String burstSimulatorFileName;

    public static Configuration getInstance() {
        return instance;
    }

    private Configuration() {
        setPaths();
        loadUserSettings();
        loadParametersConfiguration();
    }

    private void setPaths() {
        setEtcPath();
        setBurstSimulatorDirPathAndBinaryName();
    }

    public ParametersConfiguration getParametersConfiguration() {
        return parametersConfiguration;
    }

    public void setParametersConfiguration(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;
    }

    public void loadParametersConfiguration() throws JsonSyntaxException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config/parameters.json");
        Reader json = new InputStreamReader(stream);
        ParametersConfiguration parametersConfiguration = new Gson().fromJson(json, ParametersConfiguration.class);
        parametersConfiguration.buildDefinitionsByName();
        setParametersConfiguration(parametersConfiguration);
    }

    public String getUserConfPath() {
        return getEtcPath() + "conf/";
    }

    public void loadUserSettings() {
        Properties properties = new Properties();
        String path = getUserConfPath();
        String fullPath = path + "settings.properties";
        try {
            File settingsFile = new File(fullPath);
            FileInputStream inputStream = new FileInputStream(settingsFile);
            properties.load(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userSettings = properties;
    }

    public ParameterDefinition getDefinitionByName(String name) {
        return getParametersConfiguration().getDefinitionByName(name);
    }

    public String getFullPath() {
        // /C:/Users/Madis/Projects/ags/target/classes/
        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path;
        }
    }

    private boolean isRunFromJar() {
        String className = this.getClass().getName().replace('.', '/');
        String classJar = this.getClass().getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    private void setEtcPath() {
        String path = getFullPath();
        if (isRunFromJar()) {
            path = path.substring(0, path.lastIndexOf("/") + 1);
            logger.info("Running from JAR");
        } else {
            path += "../../";
            logger.info("Running outside of JAR");
        }
        path += "etc/";
        logger.info("Path to etc/ is: " + path);
        this.etcPath = path;
    }

    public String getEtcPath() {
        return etcPath;
    }

    public void setBurstSimulatorDirPathAndBinaryName() {
        String sep = "/";
        String archPath;
        //TODO: add 32 bit support when such binaries are compiled
        if ("amd64".equals(SystemUtils.OS_ARCH) || "x86_64".equals(SystemUtils.OS_ARCH)) {
            archPath = "x86_64";
        } else {
            throw new IllegalStateException("Unsupported os architecture or Java architecture: " + SystemUtils.OS_ARCH);
        }
        archPath += sep;
        String osPath;
        String binaryName;
        if (SystemUtils.IS_OS_WINDOWS) {
            osPath = "win";
            binaryName = "burst_simulator.exe";
        } else if (SystemUtils.IS_OS_LINUX) {
            osPath = "linux";
            binaryName = "burst_simulator";
        } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
            osPath = "mac";
            binaryName = "burst_simulator.app";
        } else {
            throw new IllegalStateException("Unknown Operation System: " + SystemUtils.OS_NAME);
        }
        osPath += sep;

        burstSimulatorDirPath = getEtcPath() + "burst_simulator" + sep + osPath + archPath;
        burstSimulatorFileName = binaryName;
    }

    public String getBurstSimulatorDirPath() {
        return burstSimulatorDirPath;
    }

    public String getBurstSimulatorFileName() {
        return burstSimulatorFileName;
    }

    public Properties getUserSettings() {
        return userSettings;
    }
}
