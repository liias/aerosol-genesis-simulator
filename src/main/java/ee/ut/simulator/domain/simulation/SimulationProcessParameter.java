package ee.ut.simulator.domain.simulation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * One parameter that is going to config file burstcontrol.txt
 */
@Entity
public class SimulationProcessParameter {
    @Id
    private long id;

    @ManyToOne
    private SimulationProcess simulationProcess;

    //Used in BurstSimulator I/O files
    private String name;
    private float value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
