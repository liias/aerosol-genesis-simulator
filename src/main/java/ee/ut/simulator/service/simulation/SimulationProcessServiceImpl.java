package ee.ut.simulator.service.simulation;

import ee.ut.simulator.dao.simulation.SimulationProcessDao;
import ee.ut.simulator.domain.simulation.SimulationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class SimulationProcessServiceImpl implements SimulationProcessService {

    @Autowired
    private SimulationProcessDao simulationProcessDao;

    private Properties configuration;

    @Override
    public void loadConfiguration() throws IOException {
        System.out.println("LOADING CONFIGURATION PROPERTIES FILE");
        Properties prop = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("properties/config.properties");
        prop.load(in);
        in.close();
        setConfiguration(prop);
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    //Is transactional needed, what does it do exactly?
    @Transactional
    @Override
    public void start(long processId) {
        SimulationProcess process = simulationProcessDao.getById(processId);
        System.out.println("starting process...");
        process.start();
    }
}
