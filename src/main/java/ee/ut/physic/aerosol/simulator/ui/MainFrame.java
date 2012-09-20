package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        createForm();
    }

    public void createForm() {
        ParametersConfiguration parametersConfiguration = Configuration.getInstance().getParametersConfiguration();
        Collection<ParametersGroup> parametersGroups = parametersConfiguration.getParametersGroups();

        OrderForm orderForm = new OrderForm(parametersGroups);
        //TODO: Add auto-injection to all objects
        ApplicationContextLoader loader = new ApplicationContextLoader();
        loader.load(orderForm, "META-INF/spring/beans.xml");


        setContentPane(orderForm);
    }
}