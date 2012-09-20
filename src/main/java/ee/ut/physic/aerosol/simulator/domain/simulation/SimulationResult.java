package ee.ut.physic.aerosol.simulator.domain.simulation;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationResult {
    private long id;
    private Calendar completionDate;
    private SimulationProcess simulationProcess;
    private Set<SimulationResultValue> simulationResultValues = new HashSet<SimulationResultValue>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_RESULT_SEQ")
    @SequenceGenerator(name = "SIMU_RESULT_SEQ", sequenceName="SIMU_RESULT_SEQ", allocationSize=1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    public Calendar getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Calendar completionDate) {
        this.completionDate = completionDate;
    }

    @ManyToOne
    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationResult")
    public Set<SimulationResultValue> getSimulationResultValues() {
        return simulationResultValues;
    }

    public void setSimulationResultValues(Set<SimulationResultValue> simulationResultValues) {
        this.simulationResultValues = simulationResultValues;
    }
}
