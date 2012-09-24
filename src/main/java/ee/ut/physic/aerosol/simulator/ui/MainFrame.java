package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersGroup;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Collection;

public class MainFrame extends JFrame {
    final Logger logger = LoggerFactory.getLogger(MainFrame.class);


    public MainFrame() {
        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Aerosol Burst Simulator");
        setSize(600, 400);
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
        //TODO: Load Spring application context into some other object not orderForm?
        long startTime = System.currentTimeMillis() / 1000;
        ApplicationContextLoader loader = new ApplicationContextLoader();
        loader.load(orderForm, "META-INF/spring/beans.xml");
        long endTime = System.currentTimeMillis() / 1000;
        logger.debug("Spring context loading took {} seconds", endTime - startTime);
        setContentPane(orderForm);
    }
}