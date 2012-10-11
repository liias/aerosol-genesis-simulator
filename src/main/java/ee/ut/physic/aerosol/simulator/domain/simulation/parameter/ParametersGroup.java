package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import java.util.Collection;

public class ParametersGroup {
    private String id, name;
    private Collection<ParameterDefinition> definitions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ParameterDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Collection<ParameterDefinition> definitions) {
        this.definitions = definitions;
    }

    public ParameterDefinition getParameterByName(String name) {
        for (ParameterDefinition parameterDefinition : getDefinitions()) {
            if (parameterDefinition.getName().equals(name)) {
                return parameterDefinition;
            }
        }
        return null;
    }
}
