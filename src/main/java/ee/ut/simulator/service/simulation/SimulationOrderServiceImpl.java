package ee.ut.simulator.service.simulation;

import com.google.gson.Gson;
import ee.ut.simulator.dao.simulation.SimulationOrderDao;
import ee.ut.simulator.domain.simulation.SimulationOrder;
import ee.ut.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.simulator.domain.simulation.SimulationProcess;
import ee.ut.simulator.domain.simulation.SimulationProcessParameter;
import ee.ut.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.simulator.domain.simulation.parameter.ParametersConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

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
        simulationOrderDao.add(order);
    }

    @Override
    public void generateProcesses(SimulationOrder order) {
        // TODO: Generate process from real info set form order
        // There are 2 possibilities: random, or all possible, at first implement random (like it was in old app)
        //order.getParameters()
        // TODO: number of processes is selected in order, currently lets make only one
        SimulationProcess generatedProcess = new SimulationProcess();
        generatedProcess.setSimulationOrder(order);
        for (SimulationOrderParameter orderParameter : order.getParameters()) {
            SimulationProcessParameter processParameter = new SimulationProcessParameter();
            //currently assume only setting exact value (instead of min and max)
            float generatedValue = orderParameter.getValue();
            processParameter.setValue(generatedValue);
            generatedProcess.addParameter(processParameter);
        }
        order.addProcess(generatedProcess);
    }

    @Override
    public void listProcesses() {

    }

    @Override
    public SimulationOrder getNewOrder() {
        SimulationOrder order = new SimulationOrder();
        Collection<ParameterDefinition> parameterDefinitions = getParametersConfiguration().getParameterDefinitions();
        order.setEmptyParametersFromDefinitions(parameterDefinitions);
        return order;
    }
}
