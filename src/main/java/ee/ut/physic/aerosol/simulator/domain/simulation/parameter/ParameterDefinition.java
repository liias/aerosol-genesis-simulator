package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

// Dont actually use them in database at the moment (if ever will)
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
    //The step for combobox
    private float step;
    //If set, use these values instead of step-based
    private List<Float> selectionValues;
    private String unit = "";
    private boolean hasForest = false;
    // If not specified, then default is float
    private String valueType = "float";
    private int lineNumber;

    @Id
    @GeneratedValue
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Transient
    public List<Float> getSelectionValues() {
        return selectionValues;
    }

    public void setSelectionValues(List<Float> selectionValues) {
        this.selectionValues = selectionValues;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Transient
    public String[] getAllValues() {
        List<String> values = new ArrayList<String>();
        List<Float> selectionValues = getSelectionValues();
        if (selectionValues != null && !selectionValues.isEmpty()) {
            for (Float value : selectionValues) {
                values.add(value.toString());
            }
        } else {
            float min = getMinimumValue();
            float max = getMaximumValue();
            float step = getStep();

            for (Float value = min; value <= max; value += step) {        
            	// little fix for adding floats, they can't be just added to eachother without overflowing
            	Float savedValue = (float)(Math.round(value * 100))/100;
                values.add(savedValue.toString());
            }
        }
        return values.toArray(new String[values.size()]);
    }
}
