package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class OrderForm extends JPanel {
    final Logger logger = LoggerFactory.getLogger(OrderForm.class);

    private Collection<ParametersGroupPaneWithTitle> parametersGroupPanesWithTitle = new ArrayList<ParametersGroupPaneWithTitle>();

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;
    private JButton simulateButton;
    private ParametersConfiguration parametersConfiguration;

    @Autowired
    private SimulationOrderService simulationOrderService;

    public OrderForm(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;

//        setBackground(Color.ORANGE);
//        setBorder(new EmptyBorder(0,0,0,0));

        //LayoutManager layout = new BorderLayout();
        LayoutManager layout = new GridBagLayout();
        LayoutManager layout2 = new GridBagLayout();
        JPanel main = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        main.setLayout(layout2);
        leftPanel.setLayout(layout);
        rightPanel.setLayout(layout);
        buttonsPanel.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints2.weightx = 0.5;
        constraints2.weighty = 0.5;
        constraints2.anchor = GridBagConstraints.NORTHWEST;
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

        constraints.gridx = 0;
        JLabel commentLabel = new JLabel("Comment (Max 50 char.):");
        commentField = createCommentField();
        JLabel numberOfProcessesLabel = new JLabel("Number of simulations:");
        numberOfProcessesSpinner = createNumberOfProcessesSpinner();
        simulateButton = createSimulateButton();

        JButton openM = new JButton("Open");
        JButton openC = new JButton("Open");
        JButton saveM = new JButton("Save");
        JButton saveC = new JButton("Save");
        JButton defaultM = new JButton("Default");
        JButton defaultC = new JButton("Default");
        JButton clearM = new JButton("Clear");
        JButton clearC = new JButton("Clear");
        JButton compare = new JButton("Compare");
        JButton best = new JButton("Set best");
        JButton impexp = new JButton("Import/Export");
        JLabel minmax = new JLabel("Min Max Values:");
        JLabel combo = new JLabel("Combobox Values:");

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonsPanel.add(commentLabel, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 4;
        buttonsPanel.add(commentField, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        buttonsPanel.add(numberOfProcessesLabel, constraints);
        constraints.gridx = 1;
        buttonsPanel.add(numberOfProcessesSpinner, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        buttonsPanel.add(minmax, constraints);
        constraints.gridx = 1;
        buttonsPanel.add(openM, constraints);
        constraints.gridx = 2;
        buttonsPanel.add(saveM, constraints);
        constraints.gridx = 3;
        buttonsPanel.add(defaultM, constraints);
        constraints.gridx = 4;
        buttonsPanel.add(clearM, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        buttonsPanel.add(combo, constraints);
        constraints.gridx = 1;
        buttonsPanel.add(openC, constraints);
        constraints.gridx = 2;
        buttonsPanel.add(saveC, constraints);
        constraints.gridx = 3;
        buttonsPanel.add(defaultC, constraints);
        constraints.gridx = 4;
        buttonsPanel.add(clearC, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        buttonsPanel.add(compare, constraints);
        constraints.gridx = 2;
        buttonsPanel.add(best, constraints);
        constraints.gridx = 3;
        buttonsPanel.add(impexp, constraints);
        constraints.gridx = 4;
        buttonsPanel.add(simulateButton, constraints);

        constraints2.gridx = 0;
        constraints2.gridy = 0;
        main.add(leftPanel, constraints2);
        constraints2.gridx = 1;
        main.add(rightPanel, constraints2);
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        constraints2.gridwidth = 2;
        main.add(buttonsPanel, constraints2);
        add(main);
    }

    private void addParametersGroup(String groupId, JPanel panel, GridBagConstraints constraints) {
        ParametersGroup parametersGroup = parametersConfiguration.getGroupById(groupId);
        ParametersGroupPaneWithTitle parametersGroupPaneWithTitle = new ParametersGroupPaneWithTitle(parametersGroup);
        parametersGroupPanesWithTitle.add(parametersGroupPaneWithTitle);
        panel.add(parametersGroupPaneWithTitle, constraints);
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
        logger.debug("Simulate button pressed");
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
