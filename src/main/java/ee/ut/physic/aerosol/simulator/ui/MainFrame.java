package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame {
    final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    public MainFrame() {
        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Aerosol Burst Simulator");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        createForm();
    }

    public void createForm() {
        ParametersConfiguration parametersConfiguration = Configuration.getInstance().getParametersConfiguration();
        OrderForm orderForm = new OrderForm(parametersConfiguration);
        JScrollPane scrollPane = new JScrollPane(orderForm);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);
        loadSpringApplicationContext(orderForm);
        System.out.println("Aerosol Genesis Simulator started");
    }

    private void loadSpringApplicationContext(Object object) {
        long startTime = System.currentTimeMillis() / 1000;
        ApplicationContextLoader loader = ApplicationContextLoader.getInstance();
        loader.load(object, "META-INF/spring/beans.xml");
        long endTime = System.currentTimeMillis() / 1000;
        logger.info("Spring context loading took {} seconds", endTime - startTime);
    }
}