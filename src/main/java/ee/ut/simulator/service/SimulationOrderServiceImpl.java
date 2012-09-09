package ee.ut.simulator.service;

import com.google.gson.Gson;
import ee.ut.simulator.domain.simulation.parameter.DefaultParameters;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class SimulationOrderServiceImpl implements SimulationOrderService {
    private DefaultParameters defaultParameters;

    @Override
    public DefaultParameters getDefaultParameters() {
        return defaultParameters;
    }

    @Override
    public void setDefaultParameters(DefaultParameters defaultParameters) {
        this.defaultParameters = defaultParameters;
    }

    @Override
    public void generateDefaultParameters() {

        InputStream stream = getClass().getClassLoader().getResourceAsStream("parameters.json");
        Reader json = new InputStreamReader(stream);
        DefaultParameters defaultParameters = new Gson().fromJson(json, DefaultParameters.class);
        setDefaultParameters(defaultParameters);

    }
}
