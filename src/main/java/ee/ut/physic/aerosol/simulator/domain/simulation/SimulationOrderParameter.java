package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.persistence.*;
import java.util.Random;

@Entity
public class SimulationOrderParameter extends AbstractParameter {
    private SimulationOrder simulationOrder;
    private Float freeAirMin;
    private Float freeAirMax;
    private Float forestMin;
    private Float forestMax;

    public SimulationOrderParameter() {
    }

    public SimulationOrderParameter(ParameterDefinition definition) throws IllegalArgumentException {
        super(definition);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_ORDER_PARAM_SEQ")
    @SequenceGenerator(name = "SIMU_ORDER_PARAM_SEQ", sequenceName = "SIMU_ORDER_PARAM_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "simulationOrderId")
    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void setSimulationOrder(SimulationOrder simulationOrder) {
        this.simulationOrder = simulationOrder;
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

    @Transient
    public boolean isOnlyMinAndMaxSet(Float value, Float min, Float max) {
        return value == null && min != null && max != null;
    }

    //TODO: add integer support (can still return float, but with .0 precision)
    @Transient
    public Float getExactValueOrRandomBetweenMinMax(Float value, Float min, Float max) {
        if (isOnlyMinAndMaxSet(value, min, max)) {
            value = new Random().nextFloat() * (max - min + 1) + min;
        }
        return value;
    }

    public SimulationProcessParameter generateProcessParameter() {
        SimulationProcessParameter simulationProcessParameter = new SimulationProcessParameter(getDefinition());
        Float freeAirValue = getFreeAirExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setFreeAirValue(freeAirValue);
        Float forestValue = getForestExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setForestValue(forestValue);
        return simulationProcessParameter;
    }
}
