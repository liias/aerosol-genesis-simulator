package ee.ut.physic.aerosol.simulator;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ConfigurationTest {
    @Test
    public void testLoadParametersConfiguration() throws Exception {
        Configuration configuration = Configuration.getInstance();
        configuration.loadParametersConfiguration();
        ParametersConfiguration parametersConfiguration = configuration.getParametersConfiguration();
        assertTrue(parametersConfiguration.getParametersGroups().size() != 0);
    }
}
