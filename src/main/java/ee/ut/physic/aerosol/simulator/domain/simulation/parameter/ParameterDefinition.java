package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ParameterDefinition {
    private long id;
    //Used in BurstSimulator I/O files
    private String name;
    //Short description
    private String label = "Label";
    //Long description
    private String description = "";

    private float minimumValue;
    private float maximumValue;
    private float step;
    private String unit = "";
    private boolean hasForest = false;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARAM_DEF_SEQ")
    @SequenceGenerator(name = "PARAM_DEF_SEQ", sequenceName="PARAM_DEF_SEQ", allocationSize=1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(float minimumValue) {
        this.minimumValue = minimumValue;
    }

    public float getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(float maximumValue) {
        this.maximumValue = maximumValue;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isHasForest() {
        return hasForest;
    }

    public void setHasForest(boolean hasForest) {
        this.hasForest = hasForest;
    }

    @Transient
    public String[] getAllValues() {
        float min = getMinimumValue();
        float max = getMaximumValue();
        float step = getStep();
        List<String> values = new ArrayList<String>();
        for (Float value = min; value < max; value += step) {
            values.add(value.toString());
        }
        return values.toArray(new String[values.size()]);
    }
}
