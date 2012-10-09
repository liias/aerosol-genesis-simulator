package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
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

    @Autowired
    private SimulationOrderService simulationOrderService;

    public OrderForm(Collection<ParametersGroup> parametersGroups) {
        //LayoutManager layout = new BorderLayout();
        LayoutManager layout = new GridBagLayout();
        LayoutManager layout2 = new GridBagLayout();
        setLayout(layout2);
        JPanel paneel=new JPanel();
        JPanel paneel2=new JPanel();
        JPanel paneel3=new JPanel();
        paneel.setLayout(layout);
        paneel2.setLayout(layout);
        paneel3.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints2.weightx = 0.5;
        constraints2.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.anchor = GridBagConstraints.NORTHWEST;
        int i = 0;
        int j = 0;
        for (ParametersGroup parametersGroup : parametersGroups) {
            constraints.gridy = i;
            ParametersGroupPaneWithTitle parametersGroupPaneWithTitle = new ParametersGroupPaneWithTitle(parametersGroup);
            parametersGroupPanesWithTitle.add(parametersGroupPaneWithTitle);

            if (j == 1) {
                paneel2.add(parametersGroupPaneWithTitle, constraints);
                j = 0;
                i++;
            } else {
                paneel.add(parametersGroupPaneWithTitle, constraints);
                j = 1;
            }
        }
        constraints.gridx = 1;
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
        paneel3.add(commentLabel, constraints);
        constraints.gridx = 1;
        constraints.gridwidth= 4;
        paneel3.add(commentField, constraints);

        constraints.gridwidth= 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        paneel3.add(numberOfProcessesLabel, constraints);
        constraints.gridx = 1;
        paneel3.add(numberOfProcessesSpinner, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        paneel3.add(minmax, constraints);
        constraints.gridx = 1;
        paneel3.add(openM, constraints);
        constraints.gridx = 2;
        paneel3.add(saveM, constraints);
        constraints.gridx = 3;
        paneel3.add(defaultM, constraints);
        constraints.gridx = 4;
        paneel3.add(clearM, constraints);

        constraints.gridy = 3;
        constraints.gridx=0;
        paneel3.add(combo, constraints);
        constraints.gridx = 1;
        paneel3.add(openC, constraints);
        constraints.gridx = 2;
        paneel3.add(saveC, constraints);
        constraints.gridx = 3;
        paneel3.add(defaultC, constraints);
        constraints.gridx = 4;
        paneel3.add(clearC, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        paneel3.add(compare, constraints);
        constraints.gridx = 2;
        paneel3.add(best, constraints);
        constraints.gridx = 3;
        paneel3.add(impexp, constraints);
        constraints.gridx = 4;
        paneel3.add(simulateButton, constraints);

        constraints2.gridx=0;
        constraints2.gridy=0;
        add(paneel,constraints2);
        constraints2.gridx=1;
        add(paneel2,constraints2);
        constraints2.gridx=0;
        constraints2.gridy=1;
        constraints2.gridwidth=2;
        add(paneel3,constraints2);
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
