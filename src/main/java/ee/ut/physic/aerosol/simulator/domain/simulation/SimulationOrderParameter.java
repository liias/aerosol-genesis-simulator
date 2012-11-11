package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.Random;

@Entity
public class SimulationOrderParameter extends AbstractParameter {
    @ExcludeFromJson
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
        this.setFreeAirValue(freeAirMin);
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
        this.setForestValue(forestMin);
    }

    public Float getForestMax() {
        return forestMax;
    }

    public void setForestMax(Float forestMax) {
        this.forestMax = forestMax;
    }

    @Transient
    public Float getFreeAirExactValueOrRandomBetweenMinMax() {
        return getExactValueOrRandomBetweenMinMax(getFreeAirMin(), getFreeAirMax());
    }

    @Transient
    public Float getForestExactValueOrRandomBetweenMinMax() {
        if (hasForest()) {
            //Forest value is not required, but maybe it should
            if (getForestMin() != null) {
                return getExactValueOrRandomBetweenMinMax(getForestMin(), getForestMax());
            }
        }
        return null;
    }

    @Transient
    private Float getRandomIntegerBetweenMinMax(Float min, Float max) {
        Random random = new Random();
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
        return intValue.floatValue();
    }

    @Transient
    private Float getRandomFloatValueBetweenMinAndMax(Float min, Float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    //TODO: Unit test it
    @Transient
    public Float getExactValueOrRandomBetweenMinMax(Float min, Float max) {
        if (min == null) {
            throw new IllegalArgumentException("Minimum value MUST be set");
        }
        if (max != null) {
            if (isIntegerValue()) {
                return getRandomIntegerBetweenMinMax(min, max);
            }
            return getRandomFloatValueBetweenMinAndMax(min, max);
        }
        return min;
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
