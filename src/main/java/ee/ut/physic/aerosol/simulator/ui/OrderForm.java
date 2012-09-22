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

    //When simulate button is pressed
    public void simulate() {
        //Execute when button is pressed
        System.out.println("Simulate called");
        SimulationOrder simulationOrder = createSimulationOrderWithData();
        simulationOrderService.simulate(simulationOrder);
    }

    public SimulationOrder createSimulationOrderWithData() {
        SimulationOrder simulationOrder = new SimulationOrder();
        simulationOrder.setComment("test_comment");
        simulationOrder.setNumberOfProcesses(2);
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
