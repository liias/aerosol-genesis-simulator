package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
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

    @Transient
    public Float getExactValueOrRandomBetweenMinMax(Float value, Float min, Float max) {
        if (isOnlyMinAndMaxSet(value, min, max)) {
            Random random = new Random();
            if (isIntegerValue()) {
                Integer minInteger = min.intValue();
                Integer maxInteger = max.intValue();
                Integer intValue;
                if (minInteger.equals(maxInteger)) {
                    intValue = minInteger;
                } else if (minInteger > maxInteger) {
                    intValue = random.nextInt(minInteger - maxInteger) + maxInteger;
                } else {
                    intValue = random.nextInt(maxInteger - minInteger) + minInteger;
                }
                //The lower limit is inclusive, but the upper limit is exclusive.
                value = intValue.floatValue();
            } else {
                value = random.nextFloat() * (max - min) + min;
            }
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
