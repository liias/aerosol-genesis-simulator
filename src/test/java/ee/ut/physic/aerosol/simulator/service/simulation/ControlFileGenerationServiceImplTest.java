package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ControlFileGenerationServiceImplTest {

    @Mock
    SimulationProcess simulationProcess;
    ControlFileGenerationServiceImpl controlFileGenerationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controlFileGenerationService = new ControlFileGenerationServiceImpl();
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
        controlFileGenerationService.createContent(simulationProcess);
        String content = controlFileGenerationService.getContent();
    }

    @Test
    public void testSaveContentToPath() throws Exception {
        String content = "testcontent";
        controlFileGenerationService.setContent(content);
        String path = getClass().getResource(".").getPath() + "/testing.txt" ;
        //String path = "testing.txt";
        controlFileGenerationService.saveContentToPath(path);
        File testFile = new File(path);
        String fileContent = readFile(testFile);
        assertEquals(content, fileContent);
        boolean isDeleted = testFile.delete();
        //TODO: fix deleting
        if (isDeleted) {
            System.out.println("testSaveContentToPath() couldn't delete file in path " + path);
        }
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


    private static String readFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            // Instead of using default, pass in a decoder.
            String value = Charset.defaultCharset().decode(bb).toString();
            fc.close();
            bb.clear();
            return value;
        } finally {
            stream.close();
        }
    }
}
