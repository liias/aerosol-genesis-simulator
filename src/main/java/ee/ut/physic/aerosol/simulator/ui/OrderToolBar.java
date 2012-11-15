package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationProcessService;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationResultService;
import ee.ut.physic.aerosol.simulator.service.simulation.ValidationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Map;

public class OrderToolBar extends JToolBar {
    final Logger logger = LoggerFactory.getLogger(OrderToolBar.class);

//    final static Color ERROR_COLOR = Color.PINK;

    @Autowired
    private SimulationOrderService simulationOrderService;
    @Autowired
    private SimulationProcessService simulationProcessService;
    @Autowired
    private SimulationResultService simulationResultService;
    @Autowired
    private ValidationService validationService;

    private OrderForm orderForm;
    private SaveAndWrite saveAndWrite;
    private ToolboxFrame toolboxFrame;

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;
    public JButton cancelButton;

    private JButton undoButton;
    private JButton redoButton;

    private JComboBox openByIdType;
    private JTextField orderOrProcessId;

    @SuppressWarnings("unchecked")
    public OrderToolBar(OrderForm orderForm, SaveAndWrite saveAndWrite, ToolboxFrame toolboxFrame) {
        this.orderForm = orderForm;
        this.saveAndWrite = saveAndWrite;
        this.toolboxFrame = toolboxFrame;
        setFloatable(false);
        setRollover(true);

        JButton toolboxButton = createToolboxButton();

        undoButton = createUndoButton();
        redoButton = createRedoButton();

        JButton openButton = createOpenButton();
        JButton saveButton = createSaveButton();
        JButton resetButton = createResetButton();
        JButton clearButton = createClearButton();

        JButton compareButton = createCompareButton();
        JButton viewBestButton = createViewBestButton();

        String imgLocation = "/images/toolbar/16/comment.png";
        URL imageURL = OrderToolBar.class.getResource(imgLocation);
        Icon commentIcon = new ImageIcon(imageURL);
        JLabel commentLabel = new JLabel(commentIcon, SwingConstants.LEFT);
        commentField = createCommentField();
        JLabel numberOfProcessesLabel = new JLabel(" # of runs: ");

        numberOfProcessesSpinner = createNumberOfProcessesSpinner();
        JButton simulateButton = createSimulateButton();
        cancelButton = createCancelButton();

        openByIdType = new JComboBox();
        openByIdType.addItem("Order");
        openByIdType.addItem("Process");

        orderOrProcessId = new JTextField();
        JButton openByIdButton = createOpenByIdButton();


        add(toolboxButton);
        add(compareButton);
        add(viewBestButton);

        addSeparator();
        add(openByIdType);
        add(orderOrProcessId);
        add(openByIdButton);

        addSeparator();
        add(undoButton);
        add(redoButton);
        add(openButton);
        add(saveButton);
        add(resetButton);
        add(clearButton);
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

    private JButton createToolboxButton() {
        JButton button = createButtonWithIcon("toolbox", "Toolbox");
        button.setToolTipText("Toolbox");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showToolbox();
            }
        });
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
        JButton resetButton = createButtonWithIcon("find_best", "Find Best Values");
        resetButton.setToolTipText("Searches database for results which are most close to the wanted reference results." +
                "It saves the results to a file.");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findBestValues();
            }
        });
        return resetButton;
    }


    private JButton createViewBestButton() {
        JButton resetButton = createButtonWithIcon("view_best", "View Best Process");
        resetButton.setToolTipText("View Best Process");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewBestProcess();
            }
        });
        return resetButton;
    }

    private JButton createOpenByIdButton() {
        JButton button = createButtonWithIcon("view", "View");
        button.setToolTipText("Open order or process with id");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openById();
            }
        });
        return button;
    }

    @Transactional
    private void openById() {
        String idString = orderOrProcessId.getText();
        Long id = new Long(idString);
        Map<String, Map<String, String>> parametersMap;
        if (openByIdType.getSelectedIndex() == 0) {
            //order
            parametersMap = simulationOrderService.getParametersMapById(id);
        } else {
            //process
            parametersMap = simulationProcessService.getParametersMapById(id);
        }
        orderForm.setAllParameterValues(parametersMap);
    }

    private JTextField createCommentField() {
        JTextField commentField = new JTextField(30);
//        commentField.setBackground(ERROR_COLOR);
        return commentField;
    }

    private JSpinner createNumberOfProcessesSpinner() {
        SpinnerModel model = new SpinnerNumberModel(1, 1, 1000000, 1);
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
            	try {
	            	validationService.validateOrder(orderForm);
	                simulate();
            	} catch(GeneralException except) {
	            	logger.warn("Fill all fields : ", except);
	            }
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

    private void showToolbox() {
        logger.debug("Toolbox button pressed");
        toolboxFrame.setVisible(true);
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
        String fileContent = simulationResultService.generateBestResultsFileAndSaveBestProcessId(10);
        logger.info("Found best results, prompting for save");
        saveAndWrite.promptSaveFileWithFileContent(fileContent);
    }

    private void viewBestProcess() {
        logger.debug("View Best Process button pressed");
        try {
            Long id = simulationProcessService.getBestProcessId();
            Map<String, Map<String, String>> parametersMap = simulationProcessService.getParametersMapById(id);
            orderForm.setAllParameterValues(parametersMap);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
    }

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        logger.debug("Simulate button pressed");
        int realSimulationCount = validationService.validateSimulationCount(orderForm, getNumberOfProcesses());
        SimulationOrder simulationOrder = orderForm.createSimulationOrderWithData(getComment(), realSimulationCount);
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
