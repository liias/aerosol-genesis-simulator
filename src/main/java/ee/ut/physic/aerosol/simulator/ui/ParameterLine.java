package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ParameterLine {
    final Logger logger = LoggerFactory.getLogger(ParameterLine.class);

    private ParameterDefinition parameterDefinition;
    private Double min;
    private Double max;
    private UndoManager undoManager;
    private JLabel label;
    private JComboBox freeAirMin;
    private JComboBox freeAirMax;
    private JComboBox forestMin;
    private JComboBox forestMax;
    private String[] parameterValues;
    private boolean hasForest = false;
    private String name;

    public ParameterLine(ParameterDefinition parameterDefinition, UndoManager undoManager) {
        this.parameterDefinition = parameterDefinition;
        min = Double.parseDouble(parameterDefinition.getMinimumValue().toPlainString());
        max = Double.parseDouble(parameterDefinition.getMaximumValue().toPlainString());
        this.undoManager = undoManager;
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

    private JTextComponent getTextComponent(JComboBox comboBox) {
        return (JTextComponent) comboBox.getEditor().getEditorComponent();
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
        JTextComponent textComponent = getTextComponent(comboBox);
        textComponent.getDocument().addUndoableEditListener(undoManager);
        addValidator(comboBox);
        return comboBox;
    }

    private void addValidator(final JComboBox comboBox) {
        final JTextComponent textComponent = getTextComponent(comboBox);

        // Listen for changes in the text
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validateField(comboBox);
            }

            public void removeUpdate(DocumentEvent e) {
                validateField(comboBox);
            }

            public void insertUpdate(DocumentEvent e) {
                validateField(comboBox);
            }
        });
    }

    private void validateField(JComboBox comboBox) {
        String valueString = (String) comboBox.getEditor().getItem();
        if (valueString == null) {
            return;
        }
        if (valueString.isEmpty()) {
            return;
        }
        try {
            Double value = Double.parseDouble(valueString);
            if (value < min || value > max) {
                UIHelper.setInvalid(comboBox);
                return;
            }
        } catch (NumberFormatException e) {
            UIHelper.setInvalid(comboBox);
            return;
        }

        UIHelper.setValid(comboBox);
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
    //Might not be the latest value, if field hasnt been unfocused
    private Double getSelectedValue(JComboBox comboBox) {
        String selectedValue = (String) comboBox.getSelectedItem();
        Double value = null;
        if (!selectedValue.isEmpty()) {
            value = Double.parseDouble(selectedValue);
        }
        return value;
    }

    public SimulationOrderParameter getOrderParameter() {
        SimulationOrderParameter simulationOrderParameter = new SimulationOrderParameter(getParameterDefinition());
        Double freeAirMin = getSelectedValue(getFreeAirMin());
        simulationOrderParameter.setFreeAirMin(freeAirMin);
        Double freeAirMax = getSelectedValue(getFreeAirMax());
        simulationOrderParameter.setFreeAirMax(freeAirMax);
        if (hasForest) {
            Double forestMin = getSelectedValue(getForestMin());
            simulationOrderParameter.setForestMin(forestMin);
            Double forestMax = getSelectedValue(getForestMax());
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

    public ArrayList<String> getAllValuesArray() {
        ArrayList<String> allValues = new ArrayList<String>();
        allValues.add((String) getFreeAirMin().getSelectedItem());
        allValues.add((String) getFreeAirMax().getSelectedItem());
        if (hasForest) {
            allValues.add((String) getForestMin().getSelectedItem());
            allValues.add((String) getForestMax().getSelectedItem());
        }
        return allValues;
    }

    public int setAllValuesArray(ArrayList<String> allValues, int count) {
        int myCount = 2;
        getFreeAirMin().setSelectedItem(allValues.get(count));
        getFreeAirMax().setSelectedItem(allValues.get(count + 1));
        if (hasForest) {
            getForestMin().setSelectedItem(allValues.get(count + 2));
            getForestMax().setSelectedItem(allValues.get(count + 3));
            myCount += 2;
        }
        return count + myCount;
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