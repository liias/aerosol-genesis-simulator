package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationProcess {
    private long id;
    private SimulationOrder simulationOrder;
    private Set<SimulationResult> simulationResults = new HashSet<SimulationResult>();
    private Set<SimulationProcessParameter> simulationProcessParameters = new HashSet<SimulationProcessParameter>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_PROCESS_SEQ")
    @SequenceGenerator(name = "SIMU_PROCESS_SEQ", sequenceName = "SIMU_PROCESS_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void setSimulationOrder(SimulationOrder simulationOrder) {
        this.simulationOrder = simulationOrder;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    public Set<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(Set<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    public Set<SimulationProcessParameter> getSimulationProcessParameters() {
        return simulationProcessParameters;
    }

    public void setSimulationProcessParameters(Set<SimulationProcessParameter> simulationProcessParameters) {
        this.simulationProcessParameters = simulationProcessParameters;
    }

    public void addParameter(SimulationProcessParameter parameterSimulation) {
        getSimulationProcessParameters().add(parameterSimulation);
    }
}
