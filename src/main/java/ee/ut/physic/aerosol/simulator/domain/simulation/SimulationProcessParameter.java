package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.persistence.*;

@Entity
public class SimulationProcessParameter extends AbstractParameter {
    private SimulationProcess simulationProcess;

    public SimulationProcessParameter() {
    }

    public SimulationProcessParameter(ParameterDefinition definition) throws IllegalArgumentException {
        super(definition);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_PROCESS_PARAM_SEQ")
    @SequenceGenerator(name = "SIMU_PROCESS_PARAM_SEQ", sequenceName = "SIMU_PROCESS_PARAM_SEQ", allocationSize = 1)
    public long getId() {
        return id;
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
