package ee.ut.simulator.domain.simulation;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * SimulationOrder makes exactly so many proccesses as the value for number of runs is set
 */
@Entity
public class SimulationProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMULATION_PROCESS_SEQ")
    @SequenceGenerator(name = "SIMULATION_PROCESS_SEQ", sequenceName = "simulation_process_seq", allocationSize = 1)
    private long id;

    @NotNull
    @ManyToOne
    private SimulationOrder simulationOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    private Set<SimulationResult> results = new HashSet<SimulationResult>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    private Set<SimulationProcessParameter> parameters = new HashSet<SimulationProcessParameter>();

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

    public Set<SimulationProcessParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<SimulationProcessParameter> parameters) {
        this.parameters = parameters;
    }
}
