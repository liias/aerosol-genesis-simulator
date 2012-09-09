package ee.ut.simulator.service;

import com.google.gson.Gson;
import ee.ut.simulator.domain.simulation.parameter.DefaultParameters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
        try {
            BufferedReader json = new BufferedReader(new FileReader("src/main/resources/parameters.json"));
            DefaultParameters defaultParameters = new Gson().fromJson(json, DefaultParameters.class);
            setDefaultParameters(defaultParameters);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
