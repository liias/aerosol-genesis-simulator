package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ParameterLine {
    private ParameterDefinition parameterDefinition;
    private JLabel label;
    private JComboBox freeAirMin;
    private JComboBox freeAirMax;
    private JComboBox forestMin;
    private JComboBox forestMax;
    String[] parameterValues;

    public ParameterLine(ParameterDefinition parameterDefinition) {
        this.parameterDefinition = parameterDefinition;
        parameterValues = parameterDefinition.getAllValues();
        createWidgets();
    }

    private void createWidgets() {
        label = new JLabel();
        label.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
        label.setText(parameterDefinition.getLabel());
        freeAirMin = createComboBox(true, false);
        freeAirMax = createComboBox(false, false);
        if (parameterDefinition.isHasForest()) {
            forestMin = createComboBox(true, true);
            forestMax = createComboBox(false, true);
        }
    }

    @SuppressWarnings("unchecked")
    private JComboBox createComboBox(boolean isMin, boolean inForest) {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("");
        for (String value : parameterValues) {
            comboBox.addItem(value);
        }
        comboBox.setEditable(true);
        //set the minimum value as default on start
        if (isMin) {
            comboBox.setSelectedIndex(1);
        }
        comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
        //comboBox.putClientProperty("sizeVariant", "mini");
        return comboBox;
    }

    /*
    private JSpinner createSpinner(boolean inForest) {
        SpinnerModel model = new SpinnerNumberModel(parameterDefinition.getMinimumValue(), parameterDefinition.getMinimumValue(), parameterDefinition.getMaximumValue(), parameterDefinition.getStep());
        JSpinner spinner = new JSpinner(model);
        //spinner.putClientProperty("JComponent.sizeVariant", "mini");
        spinner.setBorder(new EmptyBorder(2,3,2,3));

        Dimension d = spinner.getPreferredSize();
        d.width = 100;
        spinner.setPreferredSize(d);

        // Set the margin (add two spaces to the left and right side of the value)
        int top = 0;
        int left = 2;
        int bottom = 0;
        int right = 2;
        Insets insets = new Insets(top, left, bottom, right);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tf.setMargin(insets);

        if (inForest) {
            tf.setBackground(Color.green);
//            spinner.setBackground(Color.green);
        }
        return spinner;
    }*/

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

    /*
    private Float getSpinnerValue(JSpinner spinner) {
        return new Float((Double) spinner.getValue());
    } */

    public SimulationOrderParameter getOrderParameter() {
        SimulationOrderParameter simulationOrderParameter = new SimulationOrderParameter(getParameterDefinition());
        Float freeAirMin = getSelectedValue(getFreeAirMin());
        simulationOrderParameter.setFreeAirMin(freeAirMin);
        Float freeAirMax = getSelectedValue(getFreeAirMax());
        simulationOrderParameter.setFreeAirMax(freeAirMax);
        if (parameterDefinition.isHasForest()) {
            Float forestMin = getSelectedValue(getForestMin());
            simulationOrderParameter.setForestMin(forestMin);
            Float forestMax = getSelectedValue(getForestMax());
            simulationOrderParameter.setForestMax(forestMax);
        }
        return simulationOrderParameter;
    }
}
