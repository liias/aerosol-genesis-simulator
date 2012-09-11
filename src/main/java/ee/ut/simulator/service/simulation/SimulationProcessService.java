package ee.ut.simulator.service.simulation;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

public interface SimulationProcessService {

    @PostConstruct
    public void loadConfiguration() throws IOException;

    public Properties getConfiguration();

    public void setConfiguration(Properties configuration);

    public void start(long processId);
}
