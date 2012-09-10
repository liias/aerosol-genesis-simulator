package ee.ut.simulator.domain.simulation.parameter;

public class ParameterDefinition {
    //Used in BurstSimulator I/O files
    private String id;

    //Short description
    private String label;

    //Long description
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        }
        return getLabel();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //private float value;
    //private float min;
    //private float max;
}
