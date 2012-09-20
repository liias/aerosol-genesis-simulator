package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import java.util.Collection;

public class ParametersConfiguration {
    private Collection<ParametersGroup> parametersGroups;

    public Collection<ParametersGroup> getParametersGroups() {
        return parametersGroups;
    }

    public void setParametersGroups(Collection<ParametersGroup> parametersGroups) {
        this.parametersGroups = parametersGroups;
    }
}
