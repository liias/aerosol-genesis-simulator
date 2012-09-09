package ee.ut.simulator.domain.simulation;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationProcess {

    @Id
    private long id;

    @NotNull
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
