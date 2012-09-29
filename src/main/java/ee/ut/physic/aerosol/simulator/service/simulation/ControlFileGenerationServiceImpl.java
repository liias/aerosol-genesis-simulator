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

    @Override
    public void createContent(SimulationProcess simulationProcess) {
        Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "class");
        velocityProperties.put("class.resource.loader.description", "Velocity Classpath Resource Loader");
        velocityProperties.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(velocityProperties);
        /*  next, get the Template  */
        Template t = velocityEngine.getTemplate("templates/burstcontrol.vm");
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("id", simulationProcess.getId());
        context.put("output_filename", "simulation_output");
        context.put("parameters", getParametersOrderedByLineNumber(simulationProcess));

        for (SimulationProcessParameter parameter : simulationProcess.getSimulationProcessParameters()) {
            context.put(parameter.getName(), parameter.getControlLineValue());
        }

        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
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
