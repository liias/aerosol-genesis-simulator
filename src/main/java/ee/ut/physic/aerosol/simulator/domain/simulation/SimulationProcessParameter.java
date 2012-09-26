package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationProcessParameter {
    private long id;
    private SimulationProcess simulationProcess;
    //Used in BurstSimulator I/O files
    private String name;
    private Float freeAirValue;
    private Float forestValue;

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

    //TODO: Maybe should get it from definition, as forest value being null might be error instead
    // and it really should need forest value. But calling the definition every time seems a bit expensive,
    // if we plan to move definitions to db
    @Transient
    public boolean hasForestValue() {
        return forestValue != null;
    }

    @Transient
    public String getControlLineValue() {
        String controlLineValue = getFreeAirValue().toString();
        if (hasForestValue()) {
            controlLineValue += "/" + getForestValue();
        }
        return controlLineValue;
    }
}
