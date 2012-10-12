package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class OrderForm extends JPanel {
    private Collection<ParametersGroupPaneWithTitle> parametersGroupPanesWithTitle = new ArrayList<ParametersGroupPaneWithTitle>();
    private ParametersConfiguration parametersConfiguration;

    public OrderForm(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel allParametersPanel = new JPanel(gridBagLayout);
        JPanel leftPanel = new JPanel(gridBagLayout);
        JPanel rightPanel = new JPanel(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.gridy = 0;
        addParametersGroup("general", leftPanel, constraints);
        addParametersGroup("ionization", rightPanel, constraints);
        constraints.gridy = 1;
        addParametersGroup("background", leftPanel, constraints);
        addParametersGroup("first_condensing", rightPanel, constraints);
        constraints.gridy = 2;
        addParametersGroup("conifer_forest", leftPanel, constraints);
        addParametersGroup("second_condensing", rightPanel, constraints);
        constraints.gridy = 3;
        addParametersGroup("presentation", leftPanel, constraints);
        constraints.gridy = 4;
        addParametersGroup("nucleation", leftPanel, constraints);

        GridBagConstraints leftAndRightConstraints = new GridBagConstraints();
        leftAndRightConstraints.anchor = GridBagConstraints.NORTHWEST;
        leftAndRightConstraints.gridx = 0;
        leftAndRightConstraints.gridy = 0;
        allParametersPanel.add(leftPanel, leftAndRightConstraints);
        leftAndRightConstraints.gridx = 1;
        allParametersPanel.add(rightPanel, leftAndRightConstraints);

        add(allParametersPanel);
    }

    private void addParametersGroup(String groupId, JPanel panel, GridBagConstraints constraints) {
        ParametersGroup parametersGroup = parametersConfiguration.getGroupById(groupId);
        ParametersGroupPaneWithTitle parametersGroupPaneWithTitle = new ParametersGroupPaneWithTitle(parametersGroup);
        parametersGroupPanesWithTitle.add(parametersGroupPaneWithTitle);
        panel.add(parametersGroupPaneWithTitle, constraints);
    }

    public SimulationOrder createSimulationOrderWithData(String comment, int numberOfProcesses) {
        SimulationOrder simulationOrder = new SimulationOrder();
        simulationOrder.setComment(comment);
        simulationOrder.setNumberOfProcesses(numberOfProcesses);
        Collection<SimulationOrderParameter> simulationOrderParameters = new ArrayList<SimulationOrderParameter>();
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                SimulationOrderParameter simulationOrderParameter = parameterLine.getOrderParameter();
                simulationOrderParameter.setSimulationOrder(simulationOrder);
                simulationOrderParameters.add(simulationOrderParameter);
            }
        }
        simulationOrder.setSimulationOrderParameters(simulationOrderParameters);
        return simulationOrder;
    }
}
