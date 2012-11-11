package ee.ut.physic.aerosol.simulator.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ToolboxFrame extends JFrame {
    final Logger logger = LoggerFactory.getLogger(ToolboxFrame.class);

    @Autowired
    private SimulationOrderService simulationOrderService;

    private SaveAndWrite saveAndWrite;

    public ToolboxFrame(SaveAndWrite saveAndWrite) throws HeadlessException {
        super();
        this.saveAndWrite = saveAndWrite;

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
                importOrders();
            }
        });


        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportOrders();
            }
        });

        panel.add(importButton);
        panel.add(exportButton);
        add(panel);
    }


    public void importOrders() {
        Reader streamReader = saveAndWrite.promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            return;
        }
        JsonReader jsonReader = new JsonReader(streamReader);
        try {
            Gson gson = new Gson();

            java.lang.reflect.Type listType = new TypeToken<ArrayList<SimulationOrder>>() {
            }.getType();
            List<SimulationOrder> orders = new Gson().fromJson(jsonReader, listType);
            //Collection<SimulationOrder> orders = gson.fromJson(jsonReader, Collection.class);
            System.out.println("Debugpoint");
            simulationOrderService.importOrders(orders);
        } catch (JsonSyntaxException e) {
            logger.warn("File could not be parsed as a json file");
        }
    }

    public void exportOrders() {
        String ordersInJson = simulationOrderService.getOrdersInJson();
        System.out.println("Retrieved all orders");
        saveAndWrite.promptSaveFileWithFileContent(ordersInJson);
    }
}
