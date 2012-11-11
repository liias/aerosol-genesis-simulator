package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbstractParameter implements Comparable<AbstractParameter> {
    @ExcludeFromJson
    protected long id;
    //Used in BurstSimulator I/O files
    private String name;
    private Float freeAirValue;
    private Float forestValue;
    protected ParameterDefinition definition;

    public AbstractParameter() {
    }

    public AbstractParameter(ParameterDefinition definition) throws IllegalArgumentException {
        if (definition == null) {
            throw new IllegalArgumentException("Parameter definition does not exist!");
        }
        this.definition = definition;
        setName(definition.getName());
    }

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

    public Float getFreeAirValue() {
        return freeAirValue;
    }

    public void setFreeAirValue(Float freeAirValue) {
        this.freeAirValue = freeAirValue;
    }

    public Float getForestValue() {
        return forestValue;
    }

    public void setForestValue(Float forestValue) {
        this.forestValue = forestValue;
    }

    @Transient
    public ParameterDefinition getDefinition() {
        // If the definition reference is gone e.g because the parameter was just received from db
        // or whatever reason then get it again from configuration (no worries, it's still fast)
        if (definition == null) {
            definition = Configuration.getInstance().getDefinitionByName(getName());
        }
        return definition;
    }

    public void setDefinition(ParameterDefinition definition) {
        this.definition = definition;
    }

    @Transient
    public String getLabel() {
        return getDefinition().getLabel();
    }

    @Transient
    public String getDescription() {
        return getDefinition().getDescription();
    }

    //Indicates if this parameter CAN (MUST???) have forest value
    //TODO: When forest-enabled parameter, is it a must to have forest value?
    @Transient
    public boolean hasForest() {
        return getDefinition().isHasForest();
    }

    //Indicates if this parameter has some forest value set
    @Transient
    public boolean hasForestValue() {
        return forestValue != null;
    }

    public String getValueStringBasedOnType(Float floatValue) {
        if ("int".equals(getDefinition().getValueType())) {
            int intVal = floatValue.intValue();
            return Integer.toString(intVal);
        }
        //When float is e.g 0.0000016, toString will show e.g 1.6E-6, so converting to BigDecimal to get 0.0000016
        return new BigDecimal(floatValue.toString()).toPlainString();
    }

    @Transient
    public boolean isIntegerValue() {
        return "int".equals(getDefinition().getValueType());
    }

    @Transient
    public boolean isFloatValue() {
        return "float".equals(getDefinition().getValueType());
    }

    @Transient
    public String getUnit() {
        return getDefinition().getUnit();
    }

    @Transient
    public int getLineNumber() {
        return getDefinition().getLineNumber();
    }

    @Override
    public int compareTo(AbstractParameter otherParameter) {
        int otherLineNumber = otherParameter.getLineNumber();
        if (getLineNumber() > otherLineNumber) {
            return 1;
        } else if (getLineNumber() < otherLineNumber) {
            return -1;
        }
        return 0;
    }
}
