package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public ParametersGroup getGroupById(String id) {
        for (ParametersGroup parametersGroup : getParametersGroups()) {
            if (id.equals(parametersGroup.getId())) {
                return parametersGroup;
            }
        }
        throw new IllegalArgumentException("No such group with id: " + id);
    }

    public ParameterDefinition getDefinitionByName(String name) {
        return definitionsByName.get(name);
    }

    public List<String> getParameterNamesWithForest() {
        List<String> parameterNames = new ArrayList<String>(52);
        for (ParametersGroup parametersGroup : parametersGroups) {
            for (ParameterDefinition parameterDefinition : parametersGroup.getDefinitions()) {
                parameterNames.add(parameterDefinition.getName());
                if (parameterDefinition.isHasForest()) {
                    parameterNames.add(parameterDefinition.getName() + " forest");
                }
            }
        }
        return parameterNames;
    }
}
