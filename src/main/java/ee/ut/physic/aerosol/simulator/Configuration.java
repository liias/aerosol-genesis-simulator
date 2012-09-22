package ee.ut.physic.aerosol.simulator;

import com.google.gson.Gson;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Singleton called on startup.
 */
public class Configuration {
    private static Configuration instance = new Configuration();
    private ParametersConfiguration parametersConfiguration;
    private Properties burstAppProperties;

    public static Configuration getInstance() {
        return instance;
    }

    private Configuration() {
        loadParametersConfiguration();
        loadBurstAppProperties();
    }

    public ParametersConfiguration getParametersConfiguration() {
        return parametersConfiguration;
    }

    public void setParametersConfiguration(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;
    }

    public Properties getBurstAppProperties() {
        return burstAppProperties;
    }

    public void setBurstAppProperties(Properties burstAppProperties) {
        this.burstAppProperties = burstAppProperties;
    }

    public void loadParametersConfiguration() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config/parameters.json");
        Reader json = new InputStreamReader(stream);
        ParametersConfiguration parametersConfiguration = new Gson().fromJson(json, ParametersConfiguration.class);
        setParametersConfiguration(parametersConfiguration);
    }

    public void loadBurstAppProperties() {
        Properties properties = new Properties();
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream("config/burstapp.properties");
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBurstAppProperties(properties);
    }
}
