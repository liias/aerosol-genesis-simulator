package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrderParameter;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderForm extends JPanel {
    private static final long serialVersionUID = 11313947889710019L;

    final Logger logger = LoggerFactory.getLogger(OrderForm.class);

    private SimulationOrder simulationOrder;
    private Collection<ParametersGroupPaneWithTitle> parametersGroupPanesWithTitle = new ArrayList<ParametersGroupPaneWithTitle>();
    private ParametersConfiguration parametersConfiguration;
    private OrderToolBar orderToolbar;
    private UndoManager undoManager;
    public boolean isInitialized = false;
    public StatusBar statusBar;

    public OrderForm(ParametersConfiguration parametersConfiguration) {
        this.parametersConfiguration = parametersConfiguration;
        initUndoManager();
        statusBar = new StatusBar();
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
        constraints.gridy = 5;
        leftPanel.add(statusBar, constraints);

        GridBagConstraints leftAndRightConstraints = new GridBagConstraints();
        leftAndRightConstraints.anchor = GridBagConstraints.NORTHWEST;
        leftAndRightConstraints.gridx = 0;
        leftAndRightConstraints.gridy = 0;
        allParametersPanel.add(leftPanel, leftAndRightConstraints);
        leftAndRightConstraints.gridx = 1;
        allParametersPanel.add(rightPanel, leftAndRightConstraints);

        add(allParametersPanel);
        isInitialized = true;
    }

    private void initUndoManager() {
        undoManager = new OrderFormUndoManager(this);
    }

    private void addParametersGroup(String groupId, JPanel panel, GridBagConstraints constraints) {
        ParametersGroup parametersGroup = parametersConfiguration.getGroupById(groupId);
        ParametersGroupPaneWithTitle parametersGroupPaneWithTitle = new ParametersGroupPaneWithTitle(this, parametersGroup, undoManager);
        parametersGroupPanesWithTitle.add(parametersGroupPaneWithTitle);
        panel.add(parametersGroupPaneWithTitle, constraints);
    }

    public SimulationOrder createSimulationOrderWithData(String comment, int numberOfProcesses) {
        simulationOrder = new SimulationOrder();
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

    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void reset() {
        for (ParametersGroupPaneWithTitle groupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : groupPaneWithTitle.getParameterLines()) {
                parameterLine.reset();
            }
        }
    }

    public void clear() {
        for (ParametersGroupPaneWithTitle groupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : groupPaneWithTitle.getParameterLines()) {
                parameterLine.clear();
            }
        }
    }

    public Map<String, Map<String, String>> getAllParameterValues() {
        Map<String, Map<String, String>> allValues = new HashMap<String, Map<String, String>>();
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                allValues.put(parameterLine.getName(), parameterLine.getAllValues());
            }
        }
        return allValues;
    }

    public ArrayList<String> getAllParameterValuesArray() {
        ArrayList<String> allValues = new ArrayList<String>();
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                allValues.addAll(parameterLine.getAllValuesArray());
            }
        }
        return allValues;
    }

    public void setAllParameterValues(Map<String, Map<String, String>> allValues) {
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                String name = parameterLine.getName();
                Map<String, String> parameterValues = allValues.get(name);
                String freeAirMin = parameterValues.get("freeAirMin");
                parameterLine.setFieldValue("freeAirMin", freeAirMin);
                String freeAirMax = parameterValues.get("freeAirMax");
                parameterLine.setFieldValue("freeAirMax", freeAirMax);
                if (parameterLine.isHasForest()) {
                    String forestMin = parameterValues.get("forestMin");
                    parameterLine.setFieldValue("forestMin", forestMin);
                    String forestMax = parameterValues.get("forestMax");
                    parameterLine.setFieldValue("forestMax", forestMax);
                }
            }
        }
    }

    public void setAllParameterValues(ArrayList<String> allValues) {
        int count = 0;
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                count = parameterLine.setAllValuesArray(allValues, count);
            }
        }
    }

    public ParameterLine getParameterLineByName(String name) {
        for (ParametersGroupPaneWithTitle groupPaneWithTitle : parametersGroupPanesWithTitle) {
            try {
                return groupPaneWithTitle.getParameterLineByName(name);
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    public void setToolbar(OrderToolBar orderToolBar) {
        this.orderToolbar = orderToolBar;
        undoManager.discardAllEdits();
    }

    public void setSimulationInProcess(boolean inProcess) {
        orderToolbar.setSimulationInProcess(inProcess);
        if (!inProcess) {
            statusBar.resetCurrentProcessCount();
        }
    }

    private void enableOrDisableUndoAndRedoButtons() {
        orderToolbar.enableUndoButton(undoManager.canUndo());
        orderToolbar.enableRedoButton(undoManager.canRedo());
    }

    public void undo() {
        undoManager.undo();
        enableOrDisableUndoAndRedoButtons();
    }

    public void redo() {
        undoManager.redo();
        enableOrDisableUndoAndRedoButtons();
    }

    public void undoableEditHappened() {
        if (orderToolbar == null) {
            return;
        }
        orderToolbar.enableUndoButton(true);
    }
}
