package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class SimulationResult {
    @ExcludeFromJson
    private long id;
    private Date completionDate;
    private Integer time;
    @ExcludeFromJson
    private SimulationProcess simulationProcess;
    private List<SimulationResultValue> simulationResultValues = new ArrayList<SimulationResultValue>();

    @Id
    @GeneratedValue
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @ManyToOne
    public SimulationProcess getSimulationProcess() {
        return simulationProcess;
    }

    public void setSimulationProcess(SimulationProcess simulationProcess) {
        this.simulationProcess = simulationProcess;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationResult")
    public List<SimulationResultValue> getSimulationResultValues() {
        return simulationResultValues;
    }

    public void setSimulationResultValues(List<SimulationResultValue> simulationResultValues) {
        this.simulationResultValues = simulationResultValues;
    }

    public void addSimulationResultValue(SimulationResultValue simulationResultValue) {
        getSimulationResultValues().add(simulationResultValue);
    }
}
