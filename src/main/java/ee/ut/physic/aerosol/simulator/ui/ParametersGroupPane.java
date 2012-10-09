package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.jdesktop.swingx.JXCollapsiblePane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ParametersGroupPane extends JXCollapsiblePane {
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

    public void createParameterLines() {
        int i = 0;
        for (ParameterDefinition parameterDefinition : parameterDefinitions) {
            ParameterLine parameterLine = new ParameterLine(parameterDefinition);
            parameterLines.add(parameterLine);
            constraints.gridy = i;
            add(parameterLine, constraints);
            i++;

        }
    }

    public Collection<ParameterLine> getParameterLines() {
        return parameterLines;
    }
}
