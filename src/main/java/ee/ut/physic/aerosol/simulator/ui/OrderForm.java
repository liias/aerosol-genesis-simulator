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

    @Autowired
    private SimulationOrderService simulationOrderService;

    public OrderForm(Collection<ParametersGroup> parametersGroups) {
        //LayoutManager layout = new BorderLayout();
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
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

        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });
        constraints.gridx = 2;
        add(simulateButton, constraints);
    }

    /*public Map<String, ParameterLine> gedtAllParameterLines() {
        Map<String, ParameterLine> parameterLines = new HashMap<String, ParameterLine>();
        for (ParametersGroupPaneWithTitle parametersGroupPaneWithTitle : parametersGroupPanesWithTitle) {
            for (ParameterLine parameterLine : parametersGroupPaneWithTitle.getParameterLines()) {
                String name = parameterLine.getParameterName();
                parameterLines.put(name, parameterLine);
            }
        }
        return parameterLines;
    }*/

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        System.out.println("Simulate called");
        SimulationOrder simulationOrder = generateOrder();
        simulationOrderService.simulate(simulationOrder);
        //simulationOrderService
        // order.generateProcesses();
    }

    public SimulationOrder generateOrder() {
        SimulationOrder simulationOrder = new SimulationOrder();
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
