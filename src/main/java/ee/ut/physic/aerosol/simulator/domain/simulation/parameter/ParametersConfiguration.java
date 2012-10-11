package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import java.util.HashMap;
import java.util.Set;

public class ParametersConfiguration {
    private Set<ParametersGroup> parametersGroups;

    private HashMap<String, ParameterDefinition> definitionsByName;

    public Set<ParametersGroup> getParametersGroups() {
        return parametersGroups;
    }

    public void setParametersGroups(Set<ParametersGroup> parametersGroups) {
        this.parametersGroups = parametersGroups;
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
