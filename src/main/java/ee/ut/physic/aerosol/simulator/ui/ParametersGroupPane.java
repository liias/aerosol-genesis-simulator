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

    private Color stripeColor = Color.CYAN;

    public ParametersGroupPane(Collection<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
        stripeColor = getBackground().brighter();
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

    public void addParameterLine(ParameterLine parameterLine, int y, boolean hasBackground) {
        constraints.gridy = y;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        JLabel label = parameterLine.getLabel();
        label.setOpaque(true);
        if (hasBackground) {

            label.setBackground(stripeColor);
        }
        add(label, constraints);
        constraints.weightx = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        add(parameterLine.getFreeAirMin(), constraints);
        constraints.gridx = 2;
        add(parameterLine.getFreeAirMax(), constraints);
        if (parameterLine.getParameterDefinition().isHasForest()) {
            y++;
            constraints.gridy = y;
            constraints.gridx = 0;
            JLabel forestLabel = new JLabel("  in forest:");
            forestLabel.setOpaque(true);
            if (hasBackground) {
                forestLabel.setBackground(stripeColor);
            }
            add(forestLabel, constraints);
            constraints.gridx = 1;
            add(parameterLine.getForestMin(), constraints);
            constraints.gridx = 2;
            add(parameterLine.getForestMax(), constraints);
        }
    }

    public void createParameterLines() {



        int rowIndex = 0;
        boolean hasBackground = true;
        for (ParameterDefinition parameterDefinition : parameterDefinitions) {
            ParameterLine parameterLine = new ParameterLine(parameterDefinition);
            parameterLines.add(parameterLine);
            addParameterLine(parameterLine, rowIndex, hasBackground);
            if (parameterLine.getParameterDefinition().isHasForest()) {
                rowIndex += 2;
            } else {
                rowIndex++;
            }
            hasBackground = !hasBackground;
        }
    }

    public Collection<ParameterLine> getParameterLines() {
        return parameterLines;
    }
}
