package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;
import java.util.Random;

@Entity
public class SimulationOrderParameter {
    private long id;
    private SimulationOrder simulationOrder;

    //Used in BurstSimulator I/O files
    //must be same as ParameterDefinition name
    private String name;
    private String label;
    private String description;

    private Float freeAirValue;
    private Float freeAirMin;
    private Float freeAirMax;
    private Float forestValue;
    private Float forestMin;
    private Float forestMax;

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

    public Float getFreeAirValue() {
        return freeAirValue;
    }

    public void setFreeAirValue(Float freeAirValue) {
        this.freeAirValue = freeAirValue;
    }

    public Float getFreeAirMin() {
        return freeAirMin;
    }

    public void setFreeAirMin(Float freeAirMin) {
        this.freeAirMin = freeAirMin;
    }

    public Float getFreeAirMax() {
        return freeAirMax;
    }

    public void setFreeAirMax(Float freeAirMax) {
        this.freeAirMax = freeAirMax;
    }

    public Float getForestValue() {
        return forestValue;
    }

    public void setForestValue(Float forestValue) {
        this.forestValue = forestValue;
    }

    public Float getForestMin() {
        return forestMin;
    }

    public void setForestMin(Float forestMin) {
        this.forestMin = forestMin;
    }

    public Float getForestMax() {
        return forestMax;
    }

    public void setForestMax(Float forestMax) {
        this.forestMax = forestMax;
    }

    @Transient
    public Float getFreeAirExactValueOrRandomBetweenMinMax() {
        return getExactValueOrRandomBetweenMinMax(getFreeAirValue(), getFreeAirMin(), getFreeAirMax());
    }

    @Transient
    public Float getForestExactValueOrRandomBetweenMinMax() {
        return getExactValueOrRandomBetweenMinMax(getForestValue(), getForestMin(), getForestMax());
    }

    //TODO: add integer support (can still return float, but with .0 precision)
    @Transient
    public Float getExactValueOrRandomBetweenMinMax(Float value, Float min, Float max) {
        if (value == null) {
            value = new Random().nextFloat() * (max - min + 1) + min;
        }
        return value;
    }
}
