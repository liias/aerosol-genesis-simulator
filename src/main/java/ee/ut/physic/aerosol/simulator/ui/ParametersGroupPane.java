package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ParametersGroupPane extends JPanel {
    Collection<ParameterDefinition> parameterDefinitions;
    Collection<ParameterLine> parameterLines = new ArrayList<ParameterLine>();
    GridBagConstraints constraints;

    public ParametersGroupPane(Collection<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridy = 0;
        createParameterLines();
    }

    public void addParameterLine(ParameterLine parameterLine) {
        constraints.gridx = 0;
        add(parameterLine.getLabel(), constraints);
        constraints.gridx = 1;
        add(parameterLine.getFreeAirComboBox(), constraints);
        constraints.gridx = 2;
        add(parameterLine.getFreeAirMinSpinner(), constraints);
        constraints.gridx = 3;
        add(parameterLine.getFreeAirMaxSpinner(), constraints);
        if (parameterLine.getParameterDefinition().isHasForest()) {
            constraints.gridx = 4;
            add(parameterLine.getForestComboBox(), constraints);
            constraints.gridx = 5;
            add(parameterLine.getForestMinSpinner(), constraints);
            constraints.gridx = 6;
            add(parameterLine.getForestMaxSpinner(), constraints);
        }
    }

    public void createParameterLines() {
        int i = 0;
        for (ParameterDefinition parameterDefinition : parameterDefinitions) {
            ParameterLine parameterLine = new ParameterLine(parameterDefinition);
            parameterLines.add(parameterLine);
            constraints.gridy = i;
            addParameterLine(parameterLine);
            i++;
        }
    }

    public Collection<ParameterLine> getParameterLines() {
        return parameterLines;
    }
}
