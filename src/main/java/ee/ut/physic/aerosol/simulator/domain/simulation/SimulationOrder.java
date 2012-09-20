package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationOrder {
    private long id;
    private Set<SimulationProcess> simulationProcesses = new HashSet<SimulationProcess>();
    private Collection<SimulationOrderParameter> simulationOrderParameters = new ArrayList<SimulationOrderParameter>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_ORDER_SEQ")
    @SequenceGenerator(name = "SIMU_ORDER_SEQ", sequenceName = "SIMU_ORDER_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    public Collection<SimulationOrderParameter> getSimulationOrderParameters() {
        return simulationOrderParameters;
    }

    public void setSimulationOrderParameters(Collection<SimulationOrderParameter> simulationOrderParameters) {
        this.simulationOrderParameters = simulationOrderParameters;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationOrder")
    public Set<SimulationProcess> getSimulationProcesses() {
        return simulationProcesses;
    }

    public void setSimulationProcesses(Set<SimulationProcess> simulationProcesses) {
        this.simulationProcesses = simulationProcesses;
    }

    public void addProcess(SimulationProcess process) {
        getSimulationProcesses().add(process);
    }

    public void generateProcesses() {
        // TODO: Generate process from real info set form order
        // There are 2 possibilities: random, or all possible, at first implement random (like it was in old app)
        //order.getParameters()
        // TODO: number of simulationProcesses is selected in order, currently lets make only one
        SimulationProcess generatedProcess = new SimulationProcess();
        generatedProcess.setSimulationOrder(this);
        for (SimulationOrderParameter simulationOrderParameter : getSimulationOrderParameters()) {
            SimulationProcessParameter simulationProcessParameter = new SimulationProcessParameter();
            //currently assume only setting exact free air value (instead of min and max)
            float freeAirValue = simulationOrderParameter.getFreeAirValue();
            simulationProcessParameter.setSimulationProcess(generatedProcess);
            simulationProcessParameter.setName(simulationOrderParameter.getName());
            simulationProcessParameter.setFreeAirValue(freeAirValue);
            generatedProcess.addParameter(simulationProcessParameter);
        }
        addProcess(generatedProcess);
    }
}
