package ee.ut.simulator.service;

import com.google.gson.Gson;
import ee.ut.simulator.domain.simulation.parameter.ParametersConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class SimulationOrderServiceImpl implements SimulationOrderService {
    private ParametersConfiguration parametersConfiguration;

    @Override
    public ParametersConfiguration getParametersConfiguration() {
        return parametersConfiguration;
    }

    @Override
    public void setParametersConfiguration(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;
    }

    @Override
    //PostConstruct will call this method on initialization
    @PostConstruct
    public void generateDefaultParameters() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("parameters.json");
        Reader json = new InputStreamReader(stream);
        ParametersConfiguration parametersConfiguration = new Gson().fromJson(json, ParametersConfiguration.class);
        setParametersConfiguration(parametersConfiguration);
    }
}
