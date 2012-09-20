package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationProcessParameter {
    private long id;
    private SimulationProcess simulationProcess;
    //Used in BurstSimulator I/O files
    private String name;
    private float freeAirValue;
    private float forestValue;

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

    public float getFreeAirValue() {
        return freeAirValue;
    }

    public void setFreeAirValue(float freeAirValue) {
        this.freeAirValue = freeAirValue;
    }

    public float getForestValue() {
        return forestValue;
    }

    public void setForestValue(float forestValue) {
        this.forestValue = forestValue;
    }
}
