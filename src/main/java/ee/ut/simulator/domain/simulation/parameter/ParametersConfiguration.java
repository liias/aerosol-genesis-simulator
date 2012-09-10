package ee.ut.simulator.domain.simulation.parameter;

import java.util.Collection;

/**
 * Reads in parameterDefinitions from json
 */
public class ParametersConfiguration {
    private Collection<ParameterDefinition> parameterDefinitions;

    public Collection<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }

    public void setParameterDefinitions(Collection<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }

    public ParameterDefinition getParameterById(String id) {
        for (ParameterDefinition parameterDefinition : getParameterDefinitions()) {
            if (parameterDefinition.getId().equals(id)) {
                return parameterDefinition;
            }
        }
        return null;
    }
}
