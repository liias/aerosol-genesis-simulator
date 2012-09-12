package ee.ut.simulator.domain.simulation;

import ee.ut.simulator.domain.simulation.parameter.ParameterDefinition;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import javax.persistence.*;
import java.util.*;

@Entity
public class SimulationOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMULATION_ORDER_SEQ")
    @SequenceGenerator(name = "SIMULATION_ORDER_SEQ", sequenceName = "simulation_order_seq", allocationSize = 1)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    private Set<SimulationProcess> processes = new HashSet<SimulationProcess>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    private List<SimulationOrderParameter> parameters = LazyList.decorate(new ArrayList<SimulationOrderParameter>(), FactoryUtils.instantiateFactory(SimulationOrderParameter.class));

    //private Set<SimulationOrderParameter> parameters = new HashSet<SimulationOrderParameter>();

    public List<SimulationOrderParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<SimulationOrderParameter> parameters) {
        this.parameters = parameters;
    }

    public Set<SimulationProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<SimulationProcess> processes) {
        this.processes = processes;
    }

    public void addProcess(SimulationProcess process) {
        getProcesses().add(process);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmptyParametersFromDefinitions(Collection<ParameterDefinition> definitions) {
        for (ParameterDefinition definition : definitions) {
            SimulationOrderParameter simulationOrderParameter = new SimulationOrderParameter();
            simulationOrderParameter.setSimulationOrder(this);
            simulationOrderParameter.setName(definition.getId());
            simulationOrderParameter.setLabel(definition.getLabel());
            simulationOrderParameter.setDescription(definition.getDescription());
            getParameters().add(simulationOrderParameter);
        }
    }
}
