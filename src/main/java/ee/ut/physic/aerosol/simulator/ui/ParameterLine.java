package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ParameterLine {
    final Logger logger = LoggerFactory.getLogger(ParameterLine.class);

    private ParameterDefinition parameterDefinition;
    private JLabel label;
    private JComboBox freeAirMin;
    private JComboBox freeAirMax;
    private JComboBox forestMin;
    private JComboBox forestMax;
    private String[] parameterValues;
    private boolean hasForest = false;
    private String name;

    public ParameterLine(ParameterDefinition parameterDefinition) {
        this.parameterDefinition = parameterDefinition;
        this.name = parameterDefinition.getName();
        if (parameterDefinition.isHasForest()) {
            hasForest = true;
        }
        parameterValues = parameterDefinition.getAllValues();
        createWidgets();
    }

    private void createWidgets() {
        label = new JLabel();
        label.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        String labelText = parameterDefinition.getLabel();
        labelText += " " + parameterDefinition.getUnit();
        label.setText(labelText);
        String tooltip = "<html><b>" + parameterDefinition.getLabel() + "</b><br>Unit: " + parameterDefinition.getUnit() + "<br>" + parameterDefinition.getDescription() + "</html>";
        label.setToolTipText(tooltip);
        freeAirMin = createComboBox();
        freeAirMax = createComboBox();
        if (hasForest) {
            forestMin = createComboBox();
            forestMax = createComboBox();
        }
        setDefaultValues();
    }

    @SuppressWarnings("unchecked")
    private JComboBox createComboBox() {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("");
        for (String value : parameterValues) {
            comboBox.addItem(value);
        }
        comboBox.setEditable(true);
        comboBox.setBorder(BorderFactory.createEmptyBorder());
        comboBox.setPrototypeDisplayValue("00000000");
        int height = (int) comboBox.getPreferredSize().getHeight();
        comboBox.setPreferredSize(new Dimension(80, height));
        return comboBox;
    }

    private void setDefaultValues() {
        String defaultValue = getParameterDefinition().getDefaultValue().toString();
        freeAirMin.setSelectedItem(defaultValue);
        freeAirMax.setSelectedIndex(0);
        if (hasForest) {
            String defaultForestValue = getParameterDefinition().getDefaultForestValue().toString();
            forestMin.setSelectedItem(defaultForestValue);
            forestMax.setSelectedIndex(0);
        }
    }

    public void reset() {
        setDefaultValues();
    }

    public void clear() {
        freeAirMin.setSelectedIndex(0);
        freeAirMax.setSelectedIndex(0);
        if (hasForest) {
            forestMin.setSelectedIndex(0);
            forestMax.setSelectedIndex(0);
        }
    }

    public JComboBox getFreeAirMin() {
        return freeAirMin;
    }

    public JComboBox getFreeAirMax() {
        return freeAirMax;
    }

    public JComboBox getForestMin() {
        return forestMin;
    }

    public JComboBox getForestMax() {
        return forestMax;
    }

    public ParameterDefinition getParameterDefinition() {
        return parameterDefinition;
    }

    public JLabel getLabel() {
        return label;
    }

    // Returns null when nothing was selected
    private Float getSelectedValue(JComboBox comboBox) {
        String selectedValue = (String) comboBox.getSelectedItem();
        Float value = null;
        if (!selectedValue.isEmpty()) {
            value = Float.parseFloat(selectedValue);
        }
        return value;
    }

    public SimulationOrderParameter getOrderParameter() {
        SimulationOrderParameter simulationOrderParameter = new SimulationOrderParameter(getParameterDefinition());
        Float freeAirMin = getSelectedValue(getFreeAirMin());
        simulationOrderParameter.setFreeAirMin(freeAirMin);
        Float freeAirMax = getSelectedValue(getFreeAirMax());
        simulationOrderParameter.setFreeAirMax(freeAirMax);
        if (hasForest) {
            Float forestMin = getSelectedValue(getForestMin());
            simulationOrderParameter.setForestMin(forestMin);
            Float forestMax = getSelectedValue(getForestMax());
            simulationOrderParameter.setForestMax(forestMax);
        }
        return simulationOrderParameter;
    }

    public HashMap<String, String> getAllValues() {
        HashMap<String, String> allValues = new HashMap<String, String>();
        allValues.put("freeAirMin", (String) getFreeAirMin().getSelectedItem());
        allValues.put("freeAirMax", (String) getFreeAirMax().getSelectedItem());
        if (hasForest) {
            allValues.put("forestMin", (String) getForestMin().getSelectedItem());
            allValues.put("forestMax", (String) getForestMax().getSelectedItem());
        }
        return allValues;
    }

    public String getName() {
        return name;
    }

    public boolean isHasForest() {
        return hasForest;
    }

    public void setFieldValue(String fieldName, String fieldValue) {
        if (fieldName.equals("freeAirMin")) {
            getFreeAirMin().setSelectedItem(fieldValue);
        } else if (fieldName.equals("freeAirMax")) {
            getFreeAirMax().setSelectedItem(fieldValue);

        } else if (fieldName.equals("forestMin")) {
            getForestMin().setSelectedItem(fieldValue);

        } else if (fieldName.equals("forestMax")) {
            getForestMax().setSelectedItem(fieldValue);
        } else {
            logger.warn("Unknown field name: " + fieldName);
        }
    }
}