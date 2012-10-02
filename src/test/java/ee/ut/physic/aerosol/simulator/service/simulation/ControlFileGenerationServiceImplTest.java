package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ControlFileGenerationServiceImplTest {

    @Mock
    SimulationProcess simulationProcess;
    @Mock
    ControlFileGenerationServiceImpl controlFileGenerationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    private ParameterDefinition createDefinitionWithLineNumber(int lineNumber) {
        ParameterDefinition definition = new ParameterDefinition();
        definition.setLineNumber(lineNumber);
        return definition;
    }

    private SimulationProcessParameter createParameterWithLineNumber(int lineNumber) {
        ParameterDefinition definition = createDefinitionWithLineNumber(lineNumber);
        SimulationProcessParameter parameter = new SimulationProcessParameter();
        parameter.setDefinition(definition);
        return parameter;
    }

    @Test
    public void testGetParametersOrderedByLineNumber() throws Exception {
        simulationProcess.addParameter(createParameterWithLineNumber(1));
        simulationProcess.addParameter(createParameterWithLineNumber(2));
        simulationProcess.addParameter(createParameterWithLineNumber(4));
        simulationProcess.addParameter(createParameterWithLineNumber(3));
        List<SimulationProcessParameter> parametersOrderedByLineNumber = controlFileGenerationService.getParametersOrderedByLineNumber(simulationProcess);
        int i = 1;
        for (SimulationProcessParameter parameter : parametersOrderedByLineNumber) {
            assertEquals(i, parameter.getLineNumber());
            i++;
        }
    }

    @Test
    public void testCreateContent() throws Exception {

    }

    @Test
    public void testSaveContentToPath() throws Exception {

    }
}
