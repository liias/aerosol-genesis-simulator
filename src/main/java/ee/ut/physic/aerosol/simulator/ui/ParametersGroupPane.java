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

    public int addParameterLine(int y, ParameterLine parameterLine) {
        constraints.gridy = y;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        JLabel label = parameterLine.getLabel();
        label.setOpaque(true);

        if (y % 2 == 1) {
            label.setBackground(Color.GRAY);
        } else {
            label.setBackground(Color.LIGHT_GRAY);
        }

        add(label, constraints);
//        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;

        add(parameterLine.getFreeAirMin(), constraints);
//        add(parameterLine.getFreeAirComboBox(), constraints);
        constraints.gridx = 2;
        add(parameterLine.getFreeAirMax(), constraints);
//        add(parameterLine.getFreeAirMin(), constraints);
//        constraints.gridx = 3;
//        add(parameterLine.getFreeAirMax(), constraints);
        if (parameterLine.getParameterDefinition().isHasForest()) {
            y++;
            constraints.gridy = y;
            constraints.gridx = 0;
            JLabel forestLabel = new JLabel("  in forest:");
            forestLabel.setOpaque(true);
            forestLabel.setBackground(Color.GREEN);
          /*  if (y % 2 == 1) {
                forestLabel.setBackground(Color.GRAY);
            } else {
                forestLabel.setBackground(Color.LIGHT_GRAY);
            }*/
            add(forestLabel, constraints);
            constraints.gridx = 1;
            add(parameterLine.getForestMin(), constraints);
//            add(parameterLine.getForestComboBox(), constraints);
            constraints.gridx = 2;
            add(parameterLine.getForestMax(), constraints);
//            add(parameterLine.getForestMin(), constraints);
//            constraints.gridx = 6;
//            add(parameterLine.getForestMax(), constraints);
        }
        return y;
    }

    public void createParameterLines() {
        int i = 0;
        for (ParameterDefinition parameterDefinition : parameterDefinitions) {
            ParameterLine parameterLine = new ParameterLine(parameterDefinition);
            parameterLines.add(parameterLine);
//            constraints.gridy = i;
            int y = addParameterLine(i, parameterLine);
            i = y + 1;
//            i++;
        }
    }

    public Collection<ParameterLine> getParameterLines() {
        return parameterLines;
    }
}
