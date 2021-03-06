package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.Configuration;
import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParametersConfiguration;
import ee.ut.physic.aerosol.simulator.ui.dialogs.ExitConfirmationDialog;
import ee.ut.physic.aerosol.simulator.util.ApplicationContextLoader;
import config.SpringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -4965460578713007705L;
    final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private OrderForm orderForm;
    private OrderToolBar orderToolBar;
    private SaveAndWrite saveAndWrite;
    private UtilitiesFrame utilitiesFrame;
    private ApplicationContextLoader loader;

    public MainFrame() {
        initSpringApplicationContext();
        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Aerosol Burst Simulator");
        setSize(1024, 748);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        createForm();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                closeWindowIfApplicable();
            }
        });
    }

    public void closeWindowIfApplicable() {
        if (orderToolBar.isSimulationInProcess()) {
            int exitSelection = ExitConfirmationDialog.show();
            if (exitSelection == 0) {
                orderToolBar.stopSimulation();
                quit();
            }
        } else {
            quit();
        }
    }

    private void quit() {
        dispose();
        System.exit(0);
    }

    public void createForm() {
        ParametersConfiguration parametersConfiguration = Configuration.getInstance().getParametersConfiguration();
        orderForm = new OrderForm(parametersConfiguration);
        saveAndWrite = new SaveAndWrite(orderForm);
        loader.injectDependencies(saveAndWrite);
        utilitiesFrame = new UtilitiesFrame(saveAndWrite);
        loader.injectDependencies(utilitiesFrame);
        orderToolBar = new OrderToolBar(orderForm, saveAndWrite, utilitiesFrame);
        loader.injectDependencies(orderToolBar);
        orderToolBar.simulationOrderService.setOrderForm(orderForm);
        utilitiesFrame.setOrderToolBar(orderToolBar);
        orderForm.setToolbar(orderToolBar);
        JScrollPane scrollPane = new JScrollPane(orderForm);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.add(orderToolBar, BorderLayout.PAGE_START);
        orderPanel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(orderPanel);
        System.out.println("Aerosol Genesis Simulator started");
    }

    private void initSpringApplicationContext() {
        long startTime = System.currentTimeMillis() / 1000;
        loader = ApplicationContextLoader.getInstance();
        loader.loadApplicationContext(SpringConfiguration.class);
        long endTime = System.currentTimeMillis() / 1000;
        logger.info("Spring context loading took {} seconds", endTime - startTime);
    }


}