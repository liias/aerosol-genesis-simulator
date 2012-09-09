package ee.ut.simulator.service;

import com.google.gson.Gson;
import ee.ut.simulator.domain.simulation.parameter.DefaultParameters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SimulationOrderServiceImpl implements SimulationOrderService {
    public SimulationOrderServiceImpl() {
        try {
            BufferedReader json = new BufferedReader(new FileReader("main/resources/parameters.json"));
            DefaultParameters defaultParameters = new Gson().fromJson(json, DefaultParameters.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
