package ee.ut.physic.aerosol.simulator.domain.simulation;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationProcess {
    private long id;
    private SimulationOrder simulationOrder;
    private Set<SimulationResult> simulationResults = new HashSet<SimulationResult>();
    private Set<SimulationProcessParameter> simulationProcessParameters = new HashSet<SimulationProcessParameter>();
    private Calendar createdAt;
    private SimulationProcessState state = SimulationProcessState.NOT_STARTED;

    @Id
    @GeneratedValue
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

    public void addParameter(SimulationProcessParameter parameter) {
        getSimulationProcessParameters().add(parameter);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    public Calendar getCreatedAt() {
        if (createdAt == null) {
            createdAt = new GregorianCalendar();
        }
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated
    public SimulationProcessState getState() {
        return state;
    }

    public void setState(SimulationProcessState state) {
        this.state = state;
    }
}
