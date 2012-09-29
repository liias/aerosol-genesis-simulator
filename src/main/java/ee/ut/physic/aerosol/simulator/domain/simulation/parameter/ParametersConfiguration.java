package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import java.util.Collection;
import java.util.HashMap;

public class ParametersConfiguration {
    private Collection<ParametersGroup> parametersGroups;

    private HashMap<String, ParameterDefinition> definitionsByName;

    public Collection<ParametersGroup> getParametersGroups() {
        return parametersGroups;
    }

    public void setParametersGroups(Collection<ParametersGroup> parametersGroups) {
        this.parametersGroups = parametersGroups;
    }

    public ParameterDefinition getParameterByName(String name) {
        for (ParametersGroup parametersGroup : getParametersGroups()) {
            ParameterDefinition parameterDefinition = parametersGroup.getParameterByName(name);
            if (parameterDefinition != null) {
                return parameterDefinition;
            }
        }
        return null;
    }

    // For quick lookup
    public void buildDefinitionsByName() {
        definitionsByName = new HashMap<String, ParameterDefinition>();
        for (ParametersGroup parametersGroup : getParametersGroups()) {
            for (ParameterDefinition parameterDefinition : parametersGroup.getDefinitions()) {
                definitionsByName.put(parameterDefinition.getName(), parameterDefinition);
            }
        }
    }

    public ParameterDefinition getDefinitionByName(String name) {
        return definitionsByName.get(name);
    }
}
