package ee.ut.simulator.domain.simulation.parameter;

import java.util.Collection;

/**
 * Reads in parameters from json
 */
public class DefaultParameters {
    private Collection<Parameter> parameters;

    public Collection<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Parameter getParameterById(String id) {
        for (Parameter parameter : getParameters()) {
            if (parameter.getId().equals(id)) {
                return parameter;
            }
        }
        return null;
    }
}
