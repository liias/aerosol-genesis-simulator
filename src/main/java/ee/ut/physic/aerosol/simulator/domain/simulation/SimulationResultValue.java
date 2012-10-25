package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationResultValue {
    private long id;
    // value is not Float or smthing like that, because sometimes value = ?
    private String name, value;
    private SimulationResult simulationResult;

    //Only for Reference values
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


/*    @Transient
public Double getMin() {
    Double val = Double.parseDouble(value);
    Double min = weight * val;

    return val;
}

@Transient
public Double getMax() {
    Double val = Double.parseDouble(value);
    Double max = weight * val;
    // x = ((ref[r][k] - (tul[r][k])) * ((ref[r][k] - tul[r][k]))) * kaalud[r];
    // sum += x;//summeerub

    //get the difference between the area and your input, take absolute value so always positive, then order ascending and take the first one

    //SELECT TOP 1 * FROM [myTable]
    //WHERE Name = 'Test' and Size = 2 and PType = 'p'
    //ORDER BY ABS( Area - @input )

    //SELECT ALL, FROM values, kus... LEFT JOIN result_id (või GROUP BY) vms
    //time = 5 AND
    //resultval1 =

    //max weight = 10, min 0

    return val;
}*/
}
