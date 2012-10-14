package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import org.jdesktop.swingx.JXCollapsiblePane;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ParametersGroupPaneWithTitle extends JPanel {
    private ParametersGroup parametersGroup;
    private ParametersGroupCollapsiblePane parametersGroupPane;
    private JButton toggleButton;

    public ParametersGroupPaneWithTitle(ParametersGroup parametersGroup) {
        this.parametersGroup = parametersGroup;
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        createParametersGroupPane();
        createTitle();
    }

    private void createParametersGroupPane() {
        parametersGroupPane = new ParametersGroupCollapsiblePane(getParametersGroup().getDefinitions());
    }

    public ParametersGroup getParametersGroup() {
        return parametersGroup;
    }

    public void createTitle() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder());
        Color titleBackgroundColor = getBackground().darker();
        titlePanel.setBackground(titleBackgroundColor);
        String title = getParametersGroup().getName();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        Font f = titleLabel.getFont();
        titleLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        Action toggleAction = parametersGroupPane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
        // use the collapse/expand icons from the JTree UI
        toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));
        toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));
        toggleButton = new JButton(toggleAction);
        toggleButton.setText("");
        toggleButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        titlePanel.add(toggleButton , constraints);
        constraints.gridx = 1;
        constraints.weightx = 1.0;
        titlePanel.add(titleLabel , constraints);

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(titlePanel, constraints);
        constraints.gridy = 1;
        add(parametersGroupPane, constraints);
    }

    public Collection<ParameterLine> getParameterLines() {
        return parametersGroupPane.getParameterLines();
    }
}
