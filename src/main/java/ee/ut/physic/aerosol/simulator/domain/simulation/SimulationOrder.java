package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
public class SimulationOrder {
    final Logger logger = LoggerFactory.getLogger(SimulationOrder.class);
    @ExcludeFromJson
    private long id;
    private List<SimulationProcess> simulationProcesses = new ArrayList<SimulationProcess>();
    private Collection<SimulationOrderParameter> simulationOrderParameters = new ArrayList<SimulationOrderParameter>();
    private String comment;
    private int numberOfProcesses;
    private Calendar createdAt;
    private int numberOfFinishedProcesses = 0;

    @Id
    @GeneratedValue
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
    public List<SimulationProcess> getSimulationProcesses() {
        return simulationProcesses;
    }

    public void setSimulationProcesses(List<SimulationProcess> simulationProcesses) {
        this.simulationProcesses = simulationProcesses;
    }

    public void addProcess(SimulationProcess process) {
        getSimulationProcesses().add(process);
    }

    public SimulationProcess generateProcess() {
        SimulationProcess generatedProcess = new SimulationProcess();
        generatedProcess.setSimulationOrder(this);
        for (SimulationOrderParameter simulationOrderParameter : getSimulationOrderParameters()) {
            SimulationProcessParameter simulationProcessParameter = simulationOrderParameter.generateProcessParameter();
            simulationProcessParameter.setSimulationProcess(generatedProcess);
            generatedProcess.addParameter(simulationProcessParameter);
        }
        String hash = generatedProcess.generateParametersHash();
        generatedProcess.setParametersHash(hash);
        return generatedProcess;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public void setNumberOfProcesses(int numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;
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

    public int getNumberOfFinishedProcesses() {
        return numberOfFinishedProcesses;
    }

    public void setNumberOfFinishedProcesses(int numberOfFinishedProcesses) {
        this.numberOfFinishedProcesses = numberOfFinishedProcesses;
    }

    public void increaseCountOfFinishedProcesses() {
        setNumberOfFinishedProcesses(getNumberOfFinishedProcesses() + 1);
    }

    @Transient
    public SimulationProcess getNextNotStartedProcess() {
        int nextProcessIndex = getNumberOfFinishedProcesses();
        if (nextProcessIndex == getNumberOfProcesses()) {
            return null;
        }
        List<SimulationProcess> processes = getSimulationProcesses();
        return processes.get(nextProcessIndex);
    }

    @Transient
    public Map<String, Map<String, String>> getParametersMap() {
        Map<String, Map<String, String>> parametersMap = new HashMap<String, Map<String, String>>(52);
        for (SimulationOrderParameter parameter : simulationOrderParameters) {
            String name = parameter.getName();
            Map<String, String> parameterValues = new HashMap<String, String>(2);
            parameterValues.put("freeAirMin", parameter.stringValue(parameter.getFreeAirMin()));
            parameterValues.put("freeAirMax", parameter.stringValue(parameter.getFreeAirMax()));
            if (parameter.hasForest()) {
                parameterValues.put("forestMin", parameter.stringValue(parameter.getForestMin()));
                parameterValues.put("forestMax", parameter.stringValue(parameter.getForestMax()));
            }
            parametersMap.put(name, parameterValues);
        }
        return parametersMap;
    }

}
