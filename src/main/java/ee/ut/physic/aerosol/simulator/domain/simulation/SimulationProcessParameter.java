package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class SimulationProcessParameter extends AbstractParameter {
    @ExcludeFromJson
    private SimulationProcess simulationProcess;

    public SimulationProcessParameter() {
    }

    public SimulationProcessParameter(ParameterDefinition definition) throws IllegalArgumentException {
        super(definition);
    }

    @ManyToOne
    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    @Transient
    public String getControlLineValue() {
        String controlLineValue = getValueStringBasedOnType(getFreeAirValue());
        if (hasForestValue()) {
            controlLineValue += "/" + getValueStringBasedOnType(getForestValue());
        }
        return controlLineValue;
    }

    @Transient
    public String getControlLine() {
        String controlLine = getControlLineValue() + " ";
        if (isIntegerValue()) {
            controlLine += "#";
        }
        if (hasForestValue()) {
            controlLine += "*";
        }
        controlLine += getUnit() + ": " + getLabel() + ", " + getLineNumber() + ":" + getName();
        return controlLine;
    }
}
