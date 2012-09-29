package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.swing.*;

public class ParameterLine extends JPanel {
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
        add(label);
        add(freeAirComboBox);
        add(freeAirMinSpinner);
        add(freeAirMaxSpinner);
        if (parameterDefinition.isHasForest()) {
            add(forestComboBox);
            add(forestMinSpinner);
            add(forestMaxSpinner);
        }
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
        return comboBox;
    }

    private JSpinner createSpinner() {
        SpinnerModel model = new SpinnerNumberModel(0, parameterDefinition.getMinimumValue(), parameterDefinition.getMaximumValue(), parameterDefinition.getStep());
        return new JSpinner(model);
    }

    public JComboBox getFreeAirComboBox() {
        return freeAirComboBox;
    }

    public void setFreeAirComboBox(JComboBox freeAirComboBox) {
        this.freeAirComboBox = freeAirComboBox;
    }

    public JComboBox getForestComboBox() {
        return forestComboBox;
    }

    public void setForestComboBox(JComboBox forestComboBox) {
        this.forestComboBox = forestComboBox;
    }

    public JSpinner getFreeAirMinSpinner() {
        return freeAirMinSpinner;
    }

    public void setFreeAirMinSpinner(JSpinner freeAirMinSpinner) {
        this.freeAirMinSpinner = freeAirMinSpinner;
    }

    public JSpinner getFreeAirMaxSpinner() {
        return freeAirMaxSpinner;
    }

    public void setFreeAirMaxSpinner(JSpinner freeAirMaxSpinner) {
        this.freeAirMaxSpinner = freeAirMaxSpinner;
    }

    public JSpinner getForestMinSpinner() {
        return forestMinSpinner;
    }

    public void setForestMinSpinner(JSpinner forestMinSpinner) {
        this.forestMinSpinner = forestMinSpinner;
    }

    public JSpinner getForestMaxSpinner() {
        return forestMaxSpinner;
    }

    public void setForestMaxSpinner(JSpinner forestMaxSpinner) {
        this.forestMaxSpinner = forestMaxSpinner;
    }

    public ParameterDefinition getParameterDefinition() {
        return parameterDefinition;
    }

    public void setParameterDefinition(ParameterDefinition parameterDefinition) {
        this.parameterDefinition = parameterDefinition;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
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
        Float freeAirValue = getSelectedValue(getFreeAirComboBox());
        simulationOrderParameter.setFreeAirValue(freeAirValue);
        //TODO: shorten the code
        Float freeAirMin = new Float((Double) getFreeAirMinSpinner().getValue());
        simulationOrderParameter.setFreeAirMin(freeAirMin);
        Float freeAirMax = new Float((Double) getFreeAirMaxSpinner().getValue());
        simulationOrderParameter.setFreeAirMax(freeAirMax);
        if (parameterDefinition.isHasForest()) {
            Float forestValue = getSelectedValue(getForestComboBox());
            simulationOrderParameter.setFreeAirValue(forestValue);
            Float forestMin = new Float((Double) getForestMinSpinner().getValue());
            simulationOrderParameter.setForestMin(forestMin);
            Float forestMax = new Float((Double) getForestMaxSpinner().getValue());
            simulationOrderParameter.setForestMax(forestMax);
        }
        return simulationOrderParameter;
    }
}
