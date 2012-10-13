package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
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

    private BigDecimal minimumValue;
    private BigDecimal maximumValue;
    private BigDecimal defaultValue;

    //Step MUST NOT be 0
    //The step for combobox
    private BigDecimal step;
    //If set, use these values instead of step-based
    private List<BigDecimal> selectionValues;
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

    public BigDecimal getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(BigDecimal minimumValue) {
        this.minimumValue = minimumValue;
    }

    public BigDecimal getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(BigDecimal maximumValue) {
        this.maximumValue = maximumValue;
    }

    public BigDecimal getDefaultValue() {
        if (defaultValue == null) {
            return getMinimumValue();
        }
        return defaultValue;
    }

    public void setDefaultValue(BigDecimal defaultValue) {
        this.defaultValue = defaultValue;
    }

    public BigDecimal getStep() {
        return step;
    }

    public void setStep(BigDecimal step) {
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
    public List<BigDecimal> getSelectionValues() {
        return selectionValues;
    }

    public void setSelectionValues(List<BigDecimal> selectionValues) {
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
        List<BigDecimal> selectionValues = getSelectionValues();
        if (selectionValues != null && !selectionValues.isEmpty()) {
            for (BigDecimal value : selectionValues) {
                values.add(value.toString());
            }
        } else {
            BigDecimal min = getMinimumValue();
            BigDecimal max = getMaximumValue();
            BigDecimal step = getStep();
            for (BigDecimal value = min; value.compareTo(max) != 1; value = value.add(step)) {
                String stringValue = value.toString();
                values.add(stringValue);
            }
        }
        return values.toArray(new String[values.size()]);
    }
}
