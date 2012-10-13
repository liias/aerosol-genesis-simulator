package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import org.jdesktop.swingx.JXCollapsiblePane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;

public class ParametersGroupPaneWithTitle extends JPanel {
    private ParametersGroup parametersGroup;
    private ParametersGroupCollapsiblePane parametersGroupPane;
    private JButton title;

    public ParametersGroupPaneWithTitle(ParametersGroup parametersGroup) {
        this.parametersGroup = parametersGroup;
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        createTitle();

    }

    private void createParametersGroupPane() {
        parametersGroupPane = new ParametersGroupCollapsiblePane(getParametersGroup().getDefinitions());
    }

    public JButton getTitle() {
        return title;
    }

    public ParametersGroup getParametersGroup() {
        return parametersGroup;
    }

    public void createTitle() {
        title = new JButton();
        //title.putClientProperty("JComponent.sizeVariant", "mini");
        title.setText(getParametersGroup().getName());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridy = 0;
        add(title, constraints);
//        JLabel label = new JLabel(getParametersGroup().getName());
//        add(label, constraints);
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        createParametersGroupPane();
        add(parametersGroupPane, constraints);
        Action toggleAction = parametersGroupPane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);

        title.setBorder(new EmptyBorder(2,2,2,2));
        title.addActionListener(toggleAction);




        //title.setIcon((Icon) toggleAction.getValue("collapseIcon"));
        // use the collapse/expand icons from the JTree UI
        //toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));
        //toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));
    }

    public Collection<ParameterLine> getParameterLines() {
        return parametersGroupPane.getParameterLines();
    }
}
