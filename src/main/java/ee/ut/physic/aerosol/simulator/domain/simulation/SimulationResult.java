package ee.ut.physic.aerosol.simulator.domain.simulation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class SimulationResult {
    private long id;
    private Date completionDate;

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

    @PrePersist
    protected void onCreate() {
    	completionDate = new Date();
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
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
