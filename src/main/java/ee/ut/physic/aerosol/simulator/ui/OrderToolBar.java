package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.errors.ParametersExistException;
import ee.ut.physic.aerosol.simulator.service.simulation.*;
import ee.ut.physic.aerosol.simulator.ui.dialogs.SimulationIsReadyDialog;
import ee.ut.physic.aerosol.simulator.ui.dialogs.SimulationIsRunningDialog;
import ee.ut.physic.aerosol.simulator.ui.help.HelpWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Map;

public class OrderToolBar extends JToolBar {
    private static final long serialVersionUID = 1177215375547938677L;

    final Logger logger = LoggerFactory.getLogger(OrderToolBar.class);

    @Autowired
    public SimulationOrderService simulationOrderService;
    @Autowired
    private SimulationProcessService simulationProcessService;
    @Autowired
    private SimulationResultService simulationResultService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private MultipleOrderService multipleOrderService;

    private OrderForm orderForm;
    private SaveAndWrite saveAndWrite;
    private UtilitiesFrame utilitiesFrame;

    private JTextField commentField;
    private JSpinner numberOfProcessesSpinner;
    public JButton simulateButton;

    private JButton undoButton;
    private JButton redoButton;

    private JComboBox openByIdType;
    private JTextField orderOrProcessId;
    public JButton cancelButton;
    private JButton stopCurrentProcessButton;
    private JButton stopCurrentProcessAsFailedButton;

    public OrderToolBar(OrderForm orderForm, SaveAndWrite saveAndWrite, UtilitiesFrame utilitiesFrame) {
        this.orderForm = orderForm;
        this.saveAndWrite = saveAndWrite;
        this.utilitiesFrame = utilitiesFrame;
        setFloatable(false);
        setRollover(true);
        createAndAddWidgets();
    }

    private void createAndAddWidgets() {
        JButton toolboxButton = createToolboxButton();

        undoButton = createUndoButton();
        redoButton = createRedoButton();

        JButton openManyButton = createOpenManyButton();
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

        simulateButton = createSimulateButton();
        cancelButton = createCancelButton();
        stopCurrentProcessButton = createStopCurrentProcessButton();
        stopCurrentProcessAsFailedButton = createStopCurrentProcessAsFailedButton();

        openByIdType = new JComboBox();
        openByIdType.addItem("Order");
        openByIdType.addItem("Process");

        orderOrProcessId = new JTextField();
        JButton openByIdButton = createOpenByIdButton();
        JButton helpButton = createHelpButton();


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
        add(openManyButton);
        add(simulateButton);
        add(stopCurrentProcessButton);
        add(stopCurrentProcessAsFailedButton);
        add(cancelButton);
        add(helpButton);
    }

    private JButton createButtonWithIcon(String imageName, String altText) {
        //Look for the image.
        String imgLocation = "/images/toolbar/16/" + imageName + ".png";
        URL imageURL = OrderToolBar.class.getResource(imgLocation);
        //Create and initialize the button.
        JButton button = new JButton();
        button.setVerticalTextPosition(BOTTOM);
        button.setHorizontalTextPosition(CENTER);
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
        JButton button = createButtonWithIcon("toolbox", "Utilities");
        button.setToolTipText("<html>Utilities<br> Import/Export database, save results of an existing process etc");
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

    private JButton createOpenManyButton() {
        JButton openButton = createButtonWithIcon("run_from_file", "Run from file");
        openButton.setToolTipText("Run many from CSV");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMany();
            }
        });
        return openButton;
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
        JButton resetButton = createButtonWithIcon("find_best", "Find Best");
        resetButton.setToolTipText("<html>Find Best Results <br>Searches database for results which are most close to the wanted reference results.<br>" +
                "It saves the results to a file.");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findBestValues();
            }
        });
        return resetButton;
    }


    private JButton createViewBestButton() {
        JButton resetButton = createButtonWithIcon("view_best", "Open Best");
        resetButton.setToolTipText("Open Best");
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
                try {
                    openById();
                } catch (GeneralException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return button;
    }

    private JButton createHelpButton() {
        JButton button = createButtonWithIcon("help", "Help");
        button.setToolTipText("Help");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });
        return button;
    }

    @Transactional
    private void openById() throws GeneralException {
        String idString = orderOrProcessId.getText();
        try {
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
        } catch (NumberFormatException e) {
            throw new GeneralException("ID must be an integer number");
        } catch (GeneralException except) {
            logger.warn("Invalid ID: ", except);
        }

    }

    private JTextField createCommentField() {
        JTextField commentField = new JTextField(30);
        return commentField;
    }

    private JSpinner createNumberOfProcessesSpinner() {
        SpinnerModel model = new SpinnerNumberModel(1, 1, 99999, 1);
        JSpinner spinner = new JSpinner(model);
        String toolTip = "Number of Simulations";
        spinner.setToolTipText(toolTip);
        return new JSpinner(model);
    }

    private JButton createSimulateButton() {
        JButton simulateButton = createButtonWithIcon("start", "Run");
        simulateButton.setToolTipText("Start Simulation");
        simulateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    simulate();
                } catch (GeneralException except) {
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

    private JButton createStopCurrentProcessAsFailedButton() {
        JButton cancelButton = createButtonWithIcon("set_failed", "Set Failed");
        cancelButton.setEnabled(false);
        cancelButton.setToolTipText("<html>Kills current process, sets it's status to failed <br>" +
                "and continues with the next process . Use when a simulation hasn't been progressed.");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopCurrentProcessAsFailed();
            }
        });
        return cancelButton;
    }

    private JButton createStopCurrentProcessButton() {
        JButton cancelButton = createButtonWithIcon("stop", "Stop One");
        cancelButton.setEnabled(false);
        cancelButton.setToolTipText("Stop current Process");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopCurrentProcess();
            }
        });
        return cancelButton;
    }

    public String getComment() {
        return commentField.getText();
    }

    private int getNumberOfProcesses() {
        return (Integer) numberOfProcessesSpinner.getValue();
    }

    private void showToolbox() {
        logger.debug("Toolbox button pressed");
        utilitiesFrame.setVisible(true);
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

    private void openMany() {
        logger.debug("Open button pressed");
        saveAndWrite.openBigFile(this);
    }

    public void save() {
        logger.debug("Save button pressed");
        saveAndWrite.saveFile();
    }

    public void clear() {
        logger.debug("Clear button pressed");
        orderForm.clear();
    }

    public void showHelp() {
        logger.debug("Help button pressed");
        URL index = ClassLoader.getSystemResource("help/index.html");
        new HelpWindow("User Manual", index);
    }

    public void reset() {
        logger.debug("Reset button pressed");
        orderForm.reset();
    }

    private void findBestValues() {
        logger.debug("Find Best Values button pressed");
        try {
            String fileContent = simulationResultService.generateBestResultsFileAndSaveBestProcessId(10);
            logger.info("Found best results, prompting for save");
            saveAndWrite.promptSaveFileWithFileContent(fileContent);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
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
    public void simulate() throws GeneralException {
        setSimulationInProcess(true);
        //Execute when button is pressed
        logger.debug("Simulate button pressed");
        try {
            validationService.validateOrder(orderForm);
            int realSimulationCount = validationService.validateSimulationCount(orderForm, getNumberOfProcesses());
            SimulationOrder simulationOrder = orderForm.createSimulationOrderWithData(getComment(), realSimulationCount);
            simulationOrderService.simulate(simulationOrder);
            if (!multipleOrderService.isRunning()) {
                SimulationIsRunningDialog.show();
            }
        } catch (ParametersExistException e) {
            setSimulationInProcess(false);
            throw new GeneralException(e.getMessage());
        } catch (GeneralException e) {
            setSimulationInProcess(false);
            throw new GeneralException(e.getMessage());
        }
    }

    private void stopCurrentProcess() {
        logger.debug("Stop Current Process button pressed");

        SimulationOrder simulationOrder = orderForm.getSimulationOrder();
        if (simulationOrder != null) {
            simulationOrderService.stopCurrentProcess(simulationOrder);
        }
    }

    private void stopCurrentProcessAsFailed() {
        logger.debug("Stop Current Process as failed button pressed");
        SimulationOrder simulationOrder = orderForm.getSimulationOrder();
        if (simulationOrder != null) {
            simulationOrderService.stopCurrentProcessAsFailed(simulationOrder);
        }
    }

    public void stopSimulation() {
        logger.debug("Stop Simulate button pressed");

        SimulationOrder simulationOrder = orderForm.getSimulationOrder();
        if (simulationOrder != null) {
            simulationOrderService.stopSimulation(simulationOrder);
        }
    }

    //process here means progress
    public void setSimulationInProcess(boolean inProcess) {
        if (inProcess) {
            logger.debug("In process");
            simulateButton.setEnabled(false);
            cancelButton.setEnabled(true);
            stopCurrentProcessButton.setEnabled(true);
            stopCurrentProcessAsFailedButton.setEnabled(true);
        } else {
            logger.debug("Not in process");
            simulateButton.setEnabled(true);
            cancelButton.setEnabled(false);
            stopCurrentProcessButton.setEnabled(false);
            stopCurrentProcessAsFailedButton.setEnabled(false);
            if (!multipleOrderService.isRunning()) {
                SimulationIsReadyDialog.show();
            }
        }
    }

    // Not the most reliable way...
    public boolean isSimulationInProcess() {
        return !simulateButton.isEnabled();
    }

    public void showLabels(boolean isShown) {
        Component c;
        int i = 0;
        while ((c = getComponentAtIndex(i++)) != null) {
            if (c.getClass().isAssignableFrom(JButton.class)) {
                JButton button = (JButton) c;
                if (isShown) {
                    if (button.getIcon().getClass().isAssignableFrom(ImageIcon.class)) {
                        ImageIcon icon = (ImageIcon) button.getIcon();
                        String text = icon.getDescription();
                        button.setText(text);
                    }
                } else {
                    button.setText(null);
                }
            }


        }
    }
}
