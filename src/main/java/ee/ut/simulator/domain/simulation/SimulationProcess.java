package ee.ut.simulator.domain.simulation;


import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="SIMULATION_PROCESS")
public class SimulationProcess {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SIMULATION_PROCESS_SEQ")
    @SequenceGenerator(name = "SIMULATION_PROCESS_SEQ", sequenceName="simulation_process_seq", allocationSize=1)
    private long id;

    @ManyToOne
    private SimulationOrder simulationOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    private Set<SimulationResult> results = new HashSet<SimulationResult>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void setSimulationOrder(SimulationOrder simulationOrder) {
        this.simulationOrder = simulationOrder;
    }

    public Set<SimulationResult> getResults() {
        return results;
    }

    public void setResults(Set<SimulationResult> results) {
        this.results = results;
    }
}
