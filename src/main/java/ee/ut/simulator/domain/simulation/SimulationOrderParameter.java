package ee.ut.simulator.domain.simulation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Will produce SimulationProcessParameters.
 * Will use value or min and max. If value is set, don't use min and max.
 */
@Entity
public class SimulationOrderParameter {
    @Id
    private long id;

    @ManyToOne
    private SimulationOrder simulationOrder;

    //Used in BurstSimulator I/O files
    private String name;

    //Short description
    @Transient
    private String label;

    //Long description
    @Transient
    private String description;

    private float value;
    private float min;
    private float max;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
