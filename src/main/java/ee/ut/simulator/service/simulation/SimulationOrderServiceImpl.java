package ee.ut.simulator.service.simulation;

import com.google.gson.Gson;
import ee.ut.simulator.dao.simulation.SimulationOrderDao;
import ee.ut.simulator.domain.simulation.SimulationOrder;
import ee.ut.simulator.domain.simulation.SimulationProcess;
import ee.ut.simulator.domain.simulation.parameter.ParametersConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SimulationOrderDao simulationOrderDao;

    @Override
    @Transactional
    public void add(SimulationOrder order) {
        SimulationProcess testProcess = new SimulationProcess();
        testProcess.setSimulationOrder(order);
        order.addProcess(testProcess);
        simulationOrderDao.add(order);
    }

    @Override
    public void listProcesses() {

    }
}
