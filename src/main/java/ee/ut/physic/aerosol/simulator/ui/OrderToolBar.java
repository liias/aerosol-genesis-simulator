package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderToolBar extends JToolBar {
    final Logger logger = LoggerFactory.getLogger(OrderToolBar.class);

    @Autowired
    private SimulationOrderService simulationOrderService;

    private OrderForm orderForm;

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;

    public OrderToolBar(OrderForm orderForm) {
        this.orderForm = orderForm;
        setFloatable(false);
        setRollover(true);

        JButton openButton = new JButton("Open");
        JButton saveButton = new JButton("Save");
        JButton defaultsButton = new JButton("Reset");
        JButton clearButton = new JButton("Clear");

        JButton compareButton = new JButton("Compare");
        JButton setBestButton = new JButton("Set Best");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");

        JLabel commentLabel = new JLabel("Comment: ");
        commentField = createCommentField();
        JLabel numberOfProcessesLabel = new JLabel("Number of simulations: ");
        numberOfProcessesSpinner = createNumberOfProcessesSpinner();
        JButton simulateButton = createSimulateButton();

        add(openButton);
        add(saveButton);
        add(defaultsButton);
        add(clearButton);
        addSeparator();
        add(compareButton);
        add(setBestButton);
        add(importButton);
        add(exportButton);
        addSeparator();
        add(commentLabel);
        add(commentField);
        add(numberOfProcessesLabel);
        add(numberOfProcessesSpinner);
        add(simulateButton);
    }

          /*
    protected JButton makeNavigationButton(String actionCommand,
                                           String toolTipText,
                                           String altText) {

        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        //button.addActionListener(this);
        button.setText(altText);
        return button;
    }
    */


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


    private String getComment() {
        return commentField.getText();
    }

    private int getNumberOfProcesses() {
        return (Integer) numberOfProcessesSpinner.getValue();
    }

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        logger.debug("Simulate button pressed");
        SimulationOrder simulationOrder = orderForm.createSimulationOrderWithData(getComment(), getNumberOfProcesses());
        simulationOrderService.simulate(simulationOrder);
    }
}
