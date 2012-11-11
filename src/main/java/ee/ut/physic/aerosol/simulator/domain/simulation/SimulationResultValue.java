package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;

import javax.persistence.*;

@Entity
public class SimulationResultValue {
    @ExcludeFromJson
    private long id;
    // value is not Float or smthing like that, because sometimes value = ?
    private String name, value;
    @ExcludeFromJson
    private SimulationResult simulationResult;

    //Only for Reference values
    @Transient
    private Integer weight;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public SimulationResult getSimulationResult() {
        return simulationResult;
    }

    public void setSimulationResult(SimulationResult simulationResult) {
        this.simulationResult = simulationResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Transient
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Transient
    public Double getNumericValue() {
        Double numericValue = 0.0;
        // or we could set numericValue to null if it's question-mark
        if (!"?".equals(getValue())) {
            numericValue = Double.parseDouble(getValue());
        }
        return numericValue;
    }
}
