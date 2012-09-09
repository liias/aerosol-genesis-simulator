package ee.ut.simulator.domain.simulation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationOrder {

    @Id
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    private Set<SimulationProcess> processes = new HashSet<SimulationProcess>();

    public Set<SimulationProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<SimulationProcess> processes) {
        this.processes = processes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
