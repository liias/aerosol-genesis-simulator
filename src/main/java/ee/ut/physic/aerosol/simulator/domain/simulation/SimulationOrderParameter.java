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
    private Double freeAirMin;
    private Double freeAirMax;
    private Double forestMin;
    private Double forestMax;

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

    public Double getFreeAirMin() {
        return freeAirMin;
    }

    public void setFreeAirMin(Double freeAirMin) {
        this.freeAirMin = freeAirMin;
        this.setFreeAirValue(freeAirMin);
    }

    public Double getFreeAirMax() {
        return freeAirMax;
    }

    public void setFreeAirMax(Double freeAirMax) {
        this.freeAirMax = freeAirMax;
    }

    public Double getForestMin() {
        return forestMin;
    }

    public void setForestMin(Double forestMin) {
        this.forestMin = forestMin;
        this.setForestValue(forestMin);
    }

    public Double getForestMax() {
        return forestMax;
    }

    public void setForestMax(Double forestMax) {
        this.forestMax = forestMax;
    }

    @Transient
    public Double getFreeAirExactValueOrRandomBetweenMinMax() {
        return getExactValueOrRandomBetweenMinMax(getFreeAirMin(), getFreeAirMax());
    }

    @Transient
    public Double getForestExactValueOrRandomBetweenMinMax() {
        if (hasForest()) {
            //Forest value is not required, but maybe it should
            if (getForestMin() != null) {
                return getExactValueOrRandomBetweenMinMax(getForestMin(), getForestMax());
            }
        }
        return null;
    }

    @Transient
    private Double getRandomIntegerBetweenMinMax(Double min, Double max) {
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
        return intValue.doubleValue();
    }

    @Transient
    private Double getRandomFloatValueBetweenMinAndMax(Double min, Double max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    //TODO: Unit test it
    @Transient
    public Double getExactValueOrRandomBetweenMinMax(Double min, Double max) {
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
        Double freeAirValue = getFreeAirExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setFreeAirValue(freeAirValue);
        Double forestValue = getForestExactValueOrRandomBetweenMinMax();
        simulationProcessParameter.setForestValue(forestValue);
        return simulationProcessParameter;
    }
}
