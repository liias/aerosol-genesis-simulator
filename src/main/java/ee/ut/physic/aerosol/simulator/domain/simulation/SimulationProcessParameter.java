package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.persistence.*;

@Entity
public class SimulationProcessParameter {
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
    public String getControlLineValue() {
        String controlLineValue = getValueStringBasedOnType(getFreeAirValue());
        if (hasForestValue()) {
            controlLineValue += "/" + getValueStringBasedOnType(getForestValue());
        }
        return controlLineValue;
    }
}
