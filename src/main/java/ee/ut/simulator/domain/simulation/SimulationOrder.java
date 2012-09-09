package ee.ut.simulator.domain.simulation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMULATION_ORDER_SEQ")
    @SequenceGenerator(name = "SIMULATION_ORDER_SEQ", sequenceName = "simulation_order_seq", allocationSize = 1)
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
