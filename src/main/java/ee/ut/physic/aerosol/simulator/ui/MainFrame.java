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
    private OrderForm orderForm;
    private SaveAndWrite saveAndWrite;

    public MainFrame() {
        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Aerosol Burst Simulator");
        setSize(1024, 748);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        createForm();
    }

    public void createForm() {
        ParametersConfiguration parametersConfiguration = Configuration.getInstance().getParametersConfiguration();
        orderForm = new OrderForm(parametersConfiguration);
        saveAndWrite = new SaveAndWrite(orderForm);
        JScrollPane scrollPane = new JScrollPane(orderForm);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        JPanel orderPanel = new JPanel(new BorderLayout());
        OrderToolBar orderToolBar = new OrderToolBar(orderForm, saveAndWrite);
        orderPanel.add(orderToolBar, BorderLayout.PAGE_START);
        orderPanel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(orderPanel);
        loadSpringApplicationContext(orderToolBar);
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