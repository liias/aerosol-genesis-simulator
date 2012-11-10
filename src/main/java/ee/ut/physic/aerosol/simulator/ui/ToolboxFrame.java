package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
public class ToolboxFrame extends JFrame {

    @Autowired
    private SimulationOrderService simulationOrderService;

    public ToolboxFrame() throws HeadlessException {
        super();

        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Toolbox");
        setSize(300, 200);
        setMinimumSize(new Dimension(200, 50));

        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel(gridBagLayout);


        JButton importButton = new JButton("Import");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.util.List<SimulationOrder> simulationOrders = simulationOrderService.getAllSimulationOrders();
                System.out.println("Retrieved all orders");
            }
        });

        panel.add(importButton);
        panel.add(exportButton);
        add(panel);
    }
}
