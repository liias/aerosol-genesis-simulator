package ee.ut.physic.aerosol.simulator.domain.simulation;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
public class SimulationOrder {
    private long id;
    private List<SimulationProcess> simulationProcesses = new ArrayList<SimulationProcess>();
    private Collection<SimulationOrderParameter> simulationOrderParameters = new ArrayList<SimulationOrderParameter>();
    private String comment;
    private int numberOfProcesses;
    private Calendar createdAt;
    private int numberOfFinishedProcesses = 0;

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
    public List<SimulationProcess> getSimulationProcesses() {
        return simulationProcesses;
    }

    public void setSimulationProcesses(List<SimulationProcess> simulationProcesses) {
        this.simulationProcesses = simulationProcesses;
    }

    public void addProcess(SimulationProcess process) {
        getSimulationProcesses().add(process);
    }

    public void generateProcesses() {
        //generate process for numberOfProcesses times
        for (int i = 0; i < getNumberOfProcesses(); i++) {
            generateProcess();
        }
    }

    public void generateProcess() {
        // TODO: Generate process from real info set form order
        // There are 2 possibilities: random, or all possible, at first implement random
        // (like it was in old app)
        SimulationProcess generatedProcess = new SimulationProcess();
        generatedProcess.setSimulationOrder(this);
        for (SimulationOrderParameter simulationOrderParameter : getSimulationOrderParameters()) {
            SimulationProcessParameter simulationProcessParameter = generateProcessParameterFromOrderParameter(simulationOrderParameter);
            simulationProcessParameter.setSimulationProcess(generatedProcess);
            generatedProcess.addParameter(simulationProcessParameter);
        }
        addProcess(generatedProcess);
    }

    public SimulationProcessParameter generateProcessParameterFromOrderParameter(SimulationOrderParameter simulationOrderParameter) {
        SimulationProcessParameter simulationProcessParameter = new SimulationProcessParameter();
        simulationProcessParameter.setName(simulationOrderParameter.getName());
        Float freeAirValue = simulationOrderParameter.getFreeAirExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setFreeAirValue(freeAirValue);
        Float forestValue = simulationOrderParameter.getForestExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setForestValue(forestValue);
        return simulationProcessParameter;
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

    public void incrementNumberOfFinishedProcesses() {
        setNumberOfFinishedProcesses(getNumberOfFinishedProcesses() + 1);
    }

    @Transient
    public SimulationProcess getNextNotStartedProcess() {
        List<SimulationProcess> processes = getSimulationProcesses();
        int nextProcessIndex = getNumberOfFinishedProcesses();
        if (nextProcessIndex == getNumberOfProcesses()) {
            return null;
        }
        return processes.get(nextProcessIndex);
    }
}
