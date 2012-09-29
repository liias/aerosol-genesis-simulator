package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.persistence.*;

@Entity
public class SimulationProcessParameter implements Comparable<SimulationProcessParameter> {
    private long id;
    private SimulationProcess simulationProcess;
    //Used in BurstSimulator I/O files
    private String name;
    private Float freeAirValue;
    private Float forestValue;
    private ParameterDefinition definition;

    public SimulationProcessParameter() {
    }

    public SimulationProcessParameter(ParameterDefinition definition) throws IllegalArgumentException {
        if (definition == null) {
            throw new IllegalArgumentException("Parameter definition does not exist!");
        }
        this.definition = definition;
        setName(definition.getName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_PROCESS_PARAM_SEQ")
    @SequenceGenerator(name = "SIMU_PROCESS_PARAM_SEQ", sequenceName = "SIMU_PROCESS_PARAM_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFreeAirValue() {
        return freeAirValue;
    }

    public void setFreeAirValue(Float freeAirValue) {
        this.freeAirValue = freeAirValue;
    }

    public Float getForestValue() {
        return forestValue;
    }

    public void setForestValue(Float forestValue) {
        this.forestValue = forestValue;
    }

    @Transient
    public ParameterDefinition getDefinition() {
        // If the definition reference is gone e.g because the parameter was just received from db
        // or whatever reason then get it again from configuration (no worries, it's still fast)
        if (definition == null) {
            definition = Configuration.getInstance().getDefinitionByName(getName());
        }
        return definition;
    }

    public void setDefinition(ParameterDefinition definition) {
        this.definition = definition;
    }

    //TODO: Maybe should get it from definition, as forest value being null might be error instead
    // and it really should need forest value. But calling the definition every time seems a bit expensive,
    // if we plan to move definitions to db
    @Transient
    public boolean hasForestValue() {
        return forestValue != null;
    }

    public String getValueStringBasedOnType(Float floatValue) {
        if ("int".equals(getDefinition().getValueType())) {
            int intVal = floatValue.intValue();
            return Integer.toString(intVal);
        }
        return floatValue.toString();
    }

    @Transient
    public boolean isIntegerValue() {
        return "int".equals(getDefinition().getValueType());
    }

    @Transient
    public boolean isFloatValue() {
        return "float".equals(getDefinition().getValueType());
    }

    @Transient
    public String getUnit() {
        return getDefinition().getUnit();
    }

    @Transient
    public int getLineNumber() {
        return getDefinition().getLineNumber();
    }

    @Transient
    public String getLabel() {
        return getDefinition().getLabel();
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

    @Override
    public int compareTo(SimulationProcessParameter otherParameter) {
        int otherLineNumber = otherParameter.getLineNumber();
        if (getLineNumber() > otherLineNumber) {
            return 1;
        } else if (getLineNumber() < otherLineNumber) {
            return -1;
        }
        return 0;
    }
}
