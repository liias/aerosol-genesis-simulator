package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import org.jdesktop.swingx.JXCollapsiblePane;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ParametersGroupPaneWithTitle extends JPanel {
    ParametersGroup parametersGroup;
    ParametersGroupPane parametersGroupPane;
    private JButton title;

    public ParametersGroupPaneWithTitle(ParametersGroup parametersGroup) {
        this.parametersGroup = parametersGroup;
        LayoutManager layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridy = 0;
        createTitle();
        add(title, constraints);
        constraints.gridy = 1;
        createParametersGroupPane();
        add(parametersGroupPane, constraints);
        title.addActionListener(parametersGroupPane.getActionMap().get(
                JXCollapsiblePane.TOGGLE_ACTION));
    }

    private void createParametersGroupPane() {
        parametersGroupPane = new ParametersGroupPane(getParametersGroup().getDefinitions());

    }

    public JButton getTitle() {
        return title;
    }

    public void setTitle(JButton title) {
        this.title = title;
    }

    public ParametersGroup getParametersGroup() {
        return parametersGroup;
    }

    public void setParametersGroup(ParametersGroup parametersGroup) {
        this.parametersGroup = parametersGroup;
    }

    public void createTitle() {
        title = new JButton();
        title.setText(getParametersGroup().getName());
    }

    public Collection<ParameterLine> getParameterLines() {
        return parametersGroupPane.getParameterLines();
    }
}
