package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcessParameter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class ControlFileGenerationServiceImpl implements ControlFileGenerationService {

    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SimulationProcessParameter> getParametersOrderedByLineNumber(SimulationProcess simulationProcess) {
        List<SimulationProcessParameter> orderedParameters = new ArrayList<SimulationProcessParameter>();
        for (SimulationProcessParameter parameter : simulationProcess.getSimulationProcessParameters()) {
            orderedParameters.add(parameter);
        }
        Collections.sort(orderedParameters);
        return orderedParameters;
    }

    private Template getBurstControlTemplate() {
        Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "class");
        velocityProperties.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(velocityProperties);
        return velocityEngine.getTemplate("templates/burstcontrol.vm");
    }

    @Override
    public void createContent(SimulationProcess simulationProcess) {
        VelocityContext context = new VelocityContext();
        context.put("id", simulationProcess.getId());
        context.put("output_filename", "simulation_output");
        context.put("parameters", getParametersOrderedByLineNumber(simulationProcess));
        StringWriter writer = new StringWriter();
        Template template = getBurstControlTemplate();
        template.merge(context, writer);
        setContent(writer.toString());
    }

    @Override
    public void saveContentToPath(String path) {
        try {
            FileWriter outFile = new FileWriter(path);
            PrintWriter out = new PrintWriter(outFile);
            // Write text to file
            out.print(getContent());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
