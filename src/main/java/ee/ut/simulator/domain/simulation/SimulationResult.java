package ee.ut.simulator.domain.simulation;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="SIMULATION_RESULT")
public class SimulationResult {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SIMULATION_RESULT_SEQ")
    @SequenceGenerator(name = "SIMULATION_RESULT_SEQ", sequenceName="simulation_result_seq", allocationSize=1)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar completionDate;    
    
    @ManyToOne
    private SimulationProcess simulationProcess;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationResult")
    private Set<SimulationResultValue> resultValues = new HashSet<SimulationResultValue>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Calendar completionDate) {
        this.completionDate = completionDate;
    }

    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    public Set<SimulationResultValue> getResultValues() {
        return resultValues;
    }

    public void setResultValues(Set<SimulationResultValue> resultValues) {
        this.resultValues = resultValues;
    }
}
