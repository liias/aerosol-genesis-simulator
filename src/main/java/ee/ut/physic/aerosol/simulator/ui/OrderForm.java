package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class OrderForm extends JPanel {
    private Collection<ParametersGroupPaneWithTitle> parametersGroupPanesWithTitle = new ArrayList<ParametersGroupPaneWithTitle>();

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;
    private JButton simulateButton;

    @Autowired
    private SimulationOrderService simulationOrderService;

    public OrderForm(Collection<ParametersGroup> parametersGroups) {
        //LayoutManager layout = new BorderLayout();
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        int i = 0;
        for (ParametersGroup parametersGroup : parametersGroups) {
            constraints.gridy = i;
            ParametersGroupPaneWithTitle parametersGroupPaneWithTitle = new ParametersGroupPaneWithTitle(parametersGroup);
            parametersGroupPanesWithTitle.add(parametersGroupPaneWithTitle);
            add(parametersGroupPaneWithTitle, constraints);
            i++;
        }
        JLabel commentLabel = new JLabel("Comment:");
        commentField = createCommentField();
        JLabel numberOfProcessesLabel = new JLabel("Number of simulations:");
        numberOfProcessesSpinner = createNumberOfProcessesSpinner();
        simulateButton = createSimulateButton();
        constraints.gridx = 2;
        constraints.gridy = 0;
        add(commentLabel, constraints);
        constraints.gridy = 1;
        add(commentField, constraints);
        constraints.gridy = 2;
        add(numberOfProcessesLabel, constraints);
        constraints.gridy = 3;
        add(numberOfProcessesSpinner, constraints);
        constraints.gridy = 4;
        add(simulateButton, constraints);
    }

    private JTextField createCommentField() {
        JTextField commentField = new JTextField();
        Dimension minSize = new Dimension(200, 10);
        commentField.setMinimumSize(minSize);
        return commentField;
    }

    private JSpinner createNumberOfProcessesSpinner() {
        SpinnerModel model = new SpinnerNumberModel(1, 1, 10, 1);
        return new JSpinner(model);
    }

    private JButton createSimulateButton() {
        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });
        return simulateButton;
    }

    public JTextField getCommentField() {
        return commentField;
    }

    public JSpinner getNumberOfProcessesSpinner() {
        return numberOfProcessesSpinner;
    }

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        System.out.println("Simulate called");
        SimulationOrder simulationOrder = createSimulationOrderWithData();
        simulationOrderService.simulate(simulationOrder);
    }

    public SimulationOrder createSimulationOrderWithData() {
        SimulationOrder simulationOrder = new SimulationOrder();
        simulationOrder.setComment(getCommentField().getText());
        simulationOrder.setNumberOfProcesses((Integer) getNumberOfProcessesSpinner().getValue());
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
