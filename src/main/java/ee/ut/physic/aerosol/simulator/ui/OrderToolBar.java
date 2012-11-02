package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class OrderToolBar extends JToolBar {
    final Logger logger = LoggerFactory.getLogger(OrderToolBar.class);

//    final static Color ERROR_COLOR = Color.PINK;

    @Autowired
    private SimulationOrderService simulationOrderService;
    @Autowired
    private SimulationResultService simulationResultService;

    private OrderForm orderForm;
    private SaveAndWrite saveAndWrite;

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;
    public JButton cancelButton;

    private JButton undoButton;
    private JButton redoButton;


    public OrderToolBar(OrderForm orderForm, SaveAndWrite saveAndWrite) {
        this.orderForm = orderForm;
        this.saveAndWrite = saveAndWrite;
        setFloatable(false);
        setRollover(true);

        undoButton = createUndoButton();
        redoButton = createRedoButton();

        JButton openButton = createOpenButton();
        JButton saveButton = createSaveButton();
        JButton resetButton = createResetButton();
        JButton clearButton = createClearButton();

        JButton compareButton = createCompareButton();
        JButton setBestButton = createOpenBestButton();
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");

        JLabel commentLabel = new JLabel("Comment: ");
        commentField = createCommentField();
        JLabel numberOfProcessesLabel = new JLabel(" # of runs: ");

        numberOfProcessesSpinner = createNumberOfProcessesSpinner();
        JButton simulateButton = createSimulateButton();
        cancelButton = createCancelButton();

        add(compareButton);
        add(importButton);
        add(exportButton);
        addSeparator();
        add(undoButton);
        add(redoButton);
        add(openButton);
        add(saveButton);
        add(resetButton);
        add(clearButton);
        add(setBestButton);
        addSeparator();
        add(commentLabel);
        add(commentField);
        add(numberOfProcessesLabel);
        add(numberOfProcessesSpinner);
        add(simulateButton);
        add(cancelButton);
    }

    private JButton createButtonWithIcon(String imageName, String altText) {
        //Look for the image.
        String imgLocation = "/images/toolbar/16/" + imageName + ".png";
        URL imageURL = OrderToolBar.class.getResource(imgLocation);
        //Create and initialize the button.
        JButton button = new JButton();
        if (imageURL != null) {                      //image found
            Icon icon = new ImageIcon(imageURL, altText);
            button.setIcon(icon);
        } else {                                     //no image found
            button.setText(altText);
            logger.warn("Resource not found: " + imgLocation);
        }
        return button;
    }

    private JButton createUndoButton() {
        JButton button = createButtonWithIcon("undo", "Undo");
        button.setEnabled(false);
        button.setToolTipText("Undo");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        return button;
    }

    public void enableUndoButton(boolean isEnabled) {
        undoButton.setEnabled(isEnabled);
    }

    public void enableRedoButton(boolean isEnabled) {
        redoButton.setEnabled(isEnabled);
    }

    private JButton createRedoButton() {
        JButton button = createButtonWithIcon("redo", "Redo");
        button.setEnabled(false);
        button.setToolTipText("Redo");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });
        return button;
    }

    private JButton createOpenButton() {
        JButton openButton = createButtonWithIcon("open", "Open");
        openButton.setToolTipText("Open Parameters");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        return openButton;
    }

    private JButton createSaveButton() {
        JButton saveButton = createButtonWithIcon("save", "Save");
        saveButton.setToolTipText("Save Parameters");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        return saveButton;
    }

    private JButton createClearButton() {
        JButton clearButton = createButtonWithIcon("clear", "Clear");
        clearButton.setToolTipText("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        return clearButton;
    }

    private JButton createResetButton() {
        JButton resetButton = createButtonWithIcon("reset", "Reset");
        resetButton.setToolTipText("Defaults");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        return resetButton;
    }

    private JButton createCompareButton() {
        JButton resetButton = createButtonWithIcon("find_best_value", "Find Best Values");
        resetButton.setToolTipText("Searches database for results which are most close to the wanted reference results." +
                "If it finds the result you can later open the parameters to achieve that result by using button Open Best (the thumbs up button).");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findBestValues();
            }
        });
        return resetButton;
    }

    private JButton createOpenBestButton() {
        JButton resetButton = createButtonWithIcon("open_best", "Open Best");
        resetButton.setToolTipText("Set field values to values with best known simulation results");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importBestValues();
            }
        });
        return resetButton;
    }


    private JTextField createCommentField() {
        JTextField commentField = new JTextField(30);
//        commentField.setBackground(ERROR_COLOR);
        return commentField;
    }

    private JSpinner createNumberOfProcessesSpinner() {
        SpinnerModel model = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner spinner = new JSpinner(model);
        String toolTip = "Number of Simulations";
        spinner.setToolTipText(toolTip);
        return new JSpinner(model);
    }

    private JButton createSimulateButton() {
        JButton simulateButton = createButtonWithIcon("start", "Simulate");
        simulateButton.setToolTipText("Start Simulation");
        simulateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });
        return simulateButton;
    }

    private JButton createCancelButton() {
        JButton cancelButton = createButtonWithIcon("stop", "Stop");
        cancelButton.setEnabled(false);
        cancelButton.setToolTipText("Stop Simulation");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });
        return cancelButton;
    }

    private String getComment() {
        return commentField.getText();
    }

    private int getNumberOfProcesses() {
        return (Integer) numberOfProcessesSpinner.getValue();
    }

    private void undo() {
        logger.debug("Undo button pressed");
        orderForm.undo();
    }

    private void redo() {
        logger.debug("Redo button pressed");
        orderForm.redo();
    }

    private void open() {
        logger.debug("Open button pressed");
        saveAndWrite.openFile();
    }

    public void save() {
        logger.debug("Save button pressed");
        saveAndWrite.saveFile();
    }

    public void clear() {
        logger.debug("Clear button pressed");
        orderForm.clear();
    }

    public void reset() {
        logger.debug("Reset button pressed");
        orderForm.reset();
    }

    private void findBestValues() {
        logger.debug("Find Best Values button pressed");
        simulationResultService.compareWithReference();
    }

    private void importBestValues() {
        logger.debug("Set Best button pressed");
        orderForm.importBestValues();
    }

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        logger.debug("Simulate button pressed");
        SimulationOrder simulationOrder = orderForm.createSimulationOrderWithData(getComment(), getNumberOfProcesses());
        simulationOrderService.setOrderForm(orderForm);
        simulationOrderService.simulate(simulationOrder);
    }

    public void stopSimulation() {
        logger.debug("Stop Simulate button pressed");
        SimulationOrder simulationOrder = orderForm.getSimulationOrder();
        if (simulationOrder != null) {
            simulationOrderService.stopSimulation(simulationOrder);
        }
    }
}
