package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
public class SimulationProcess {
    @ExcludeFromJson
    private long id;
    @ExcludeFromJson
    private SimulationOrder simulationOrder;
    private Set<SimulationResult> simulationResults = new HashSet<SimulationResult>();
    private Set<SimulationProcessParameter> simulationProcessParameters = new HashSet<SimulationProcessParameter>();
    private Calendar createdAt;
    private SimulationProcessState state = SimulationProcessState.NOT_STARTED;
    private Integer resultFileNumber;

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

    @Transient
    public void setResultFileNumber(Integer resultFileNumber) {
        this.resultFileNumber = resultFileNumber;
    }

    @Transient
    public Integer getResultFileNumber() {
        return resultFileNumber;
    }

    @Transient
    public Map<String, Map<String, String>> getParametersMap() {
        Map<String, Map<String, String>> parametersMap = new HashMap<String, Map<String, String>>(30);
        for (SimulationProcessParameter parameter : simulationProcessParameters) {
            String name = parameter.getName();
            Map<String, String> parameterValues = new HashMap<String, String>(2);
            parameterValues.put("freeAirMin", parameter.stringValue(parameter.getFreeAirValue()));
            parameterValues.put("freeAirMax", "");
            if (parameter.hasForest()) {
                parameterValues.put("forestMin", parameter.stringValue(parameter.getForestValue()));
                parameterValues.put("forestMax", "");
            }
            parametersMap.put(name, parameterValues);
        }
        return parametersMap;
    }

}
