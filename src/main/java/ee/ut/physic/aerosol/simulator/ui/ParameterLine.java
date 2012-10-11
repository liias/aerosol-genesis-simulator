package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.swing.*;

public class ParameterLine {
    private ParameterDefinition parameterDefinition;
    private JLabel label;
    private JComboBox freeAirComboBox;
    private JComboBox forestComboBox;
    private JSpinner freeAirMinSpinner;
    private JSpinner freeAirMaxSpinner;
    private JSpinner forestMinSpinner;
    private JSpinner forestMaxSpinner;

    public ParameterLine(ParameterDefinition parameterDefinition) {
        this.parameterDefinition = parameterDefinition;
        init();
    }

    public void init() {
        createWidgets();
    }

    private void createWidgets() {
        label = new JLabel();
        label.setText(parameterDefinition.getLabel());
        String[] values = parameterDefinition.getAllValues();
        freeAirComboBox = createComboBoxForValues(values);
        freeAirMinSpinner = createSpinner();
        freeAirMaxSpinner = createSpinner();
        if (parameterDefinition.isHasForest()) {
            forestComboBox = createComboBoxForValues(values);
            forestMinSpinner = createSpinner();
            forestMaxSpinner = createSpinner();
        }
    }

    @SuppressWarnings("unchecked")
    private JComboBox createComboBoxForValues(String[] values) {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("");
        for (String value : values) {
            comboBox.addItem(value);
        }
        //comboBox.putClientProperty("sizeVariant", "mini");
        return comboBox;
    }

    private JSpinner createSpinner() {
        SpinnerModel model = new SpinnerNumberModel(parameterDefinition.getMinimumValue(), parameterDefinition.getMinimumValue(), parameterDefinition.getMaximumValue(), parameterDefinition.getStep());
        JSpinner spinner = new JSpinner(model);
        //spinner.putClientProperty("JComponent.sizeVariant", "mini");
        return spinner;
    }

    public JComboBox getFreeAirComboBox() {
        return freeAirComboBox;
    }

    public JComboBox getForestComboBox() {
        return forestComboBox;
    }

    public JSpinner getFreeAirMinSpinner() {
        return freeAirMinSpinner;
    }

    public JSpinner getFreeAirMaxSpinner() {
        return freeAirMaxSpinner;
    }

    public JSpinner getForestMinSpinner() {
        return forestMinSpinner;
    }

    public JSpinner getForestMaxSpinner() {
        return forestMaxSpinner;
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

    private Float getSpinnerValue(JSpinner spinner) {
        return new Float((Double) spinner.getValue());
    }

    public SimulationOrderParameter getOrderParameter() {
        SimulationOrderParameter simulationOrderParameter = new SimulationOrderParameter(getParameterDefinition());
        Float freeAirValue = getSelectedValue(getFreeAirComboBox());
        simulationOrderParameter.setFreeAirValue(freeAirValue);
        Float freeAirMin = getSpinnerValue(getFreeAirMinSpinner());
        simulationOrderParameter.setFreeAirMin(freeAirMin);
        Float freeAirMax = getSpinnerValue(getFreeAirMaxSpinner());
        simulationOrderParameter.setFreeAirMax(freeAirMax);
        if (parameterDefinition.isHasForest()) {
            Float forestValue = getSelectedValue(getForestComboBox());
            simulationOrderParameter.setFreeAirValue(forestValue);
            Float forestMin = getSpinnerValue(getForestMinSpinner());
            simulationOrderParameter.setForestMin(forestMin);
            Float forestMax = getSpinnerValue(getForestMaxSpinner());
            simulationOrderParameter.setForestMax(forestMax);
        }
        return simulationOrderParameter;
    }
}
