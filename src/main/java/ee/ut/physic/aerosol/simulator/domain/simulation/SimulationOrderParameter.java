package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationOrderParameter {
    private long id;
    private SimulationOrder simulationOrder;

    //Used in BurstSimulator I/O files
    //must be same as ParameterDefinition name
    private String name;
    private String label;
    private String description;

    private float freeAirValue;
    private float freeAirMin;
    private float freeAirMax;
    private float forestValue;
    private float forestMin;
    private float forestMax;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_ORDER_PARAM_SEQ")
    @SequenceGenerator(name = "SIMU_ORDER_PARAM_SEQ", sequenceName = "SIMU_ORDER_PARAM_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "simulationOrderId")
    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void setSimulationOrder(SimulationOrder simulationOrder) {
        this.simulationOrder = simulationOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Transient
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getFreeAirValue() {
        return freeAirValue;
    }

    public void setFreeAirValue(float freeAirValue) {
        this.freeAirValue = freeAirValue;
    }

    public float getFreeAirMin() {
        return freeAirMin;
    }

    public void setFreeAirMin(float freeAirMin) {
        this.freeAirMin = freeAirMin;
    }

    public float getFreeAirMax() {
        return freeAirMax;
    }

    public void setFreeAirMax(float freeAirMax) {
        this.freeAirMax = freeAirMax;
    }

    public float getForestValue() {
        return forestValue;
    }

    public void setForestValue(float forestValue) {
        this.forestValue = forestValue;
    }

    public float getForestMin() {
        return forestMin;
    }

    public void setForestMin(float forestMin) {
        this.forestMin = forestMin;
    }

    public float getForestMax() {
        return forestMax;
    }

    public void setForestMax(float forestMax) {
        this.forestMax = forestMax;
    }
}
