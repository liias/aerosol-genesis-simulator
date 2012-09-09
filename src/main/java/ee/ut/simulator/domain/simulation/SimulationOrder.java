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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    private Set<SimulationOrderParameter> parameters = new HashSet<SimulationOrderParameter>();

    /*public void createParameters() {
        //TODO: This is temporary, in future generate parameters (without explicit values) from JSON
        SimulationOrderParameter parameter = new SimulationOrderParameter();
        parameter.setName("time");
        parameters.add(parameter);
        SimulationOrderParameter parameter2 = new SimulationOrderParameter();
        parameter2.setName("nst");
        parameter2.setLabel("Number of evolution steps in a minute (recommended 20...60)");
        parameters.add(parameter2);
    } */

    public Set<SimulationOrderParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<SimulationOrderParameter> parameters) {
        this.parameters = parameters;
    }

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
