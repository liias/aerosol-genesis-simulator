package ee.ut.physic.aerosol.simulator.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationResultService;
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
    @Autowired
    private SimulationResultService simulationResultsService;
    private SaveAndWrite saveAndWrite;
    private JTextField processIdField;
    GridBagConstraints toolconstraints;

    public ToolboxFrame(SaveAndWrite saveAndWrite) throws HeadlessException {
        super();
        this.saveAndWrite = saveAndWrite;


        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Utilities");
        setSize(180, 120);
        setMinimumSize(new Dimension(180, 120));

        GridBagLayout gridBagLayout = new GridBagLayout();

        toolconstraints = new GridBagConstraints();
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

        JLabel processIdLabel = new JLabel("Process ID: ");
        processIdField = new JTextField(5);

        JButton saveResultsFile = new JButton("Save Results");
        saveResultsFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveResults();
            }
        });
        toolconstraints.gridx=0;
        toolconstraints.fill = GridBagConstraints.HORIZONTAL;

        toolconstraints.fill = GridBagConstraints.VERTICAL;
        toolconstraints.gridy=0;
        panel.add(importButton, toolconstraints);
        toolconstraints.gridx=1;
        panel.add(exportButton, toolconstraints);
        toolconstraints.gridx=0;
        toolconstraints.gridy=1;
        panel.add(processIdLabel, toolconstraints);
        toolconstraints.gridx=1;
        toolconstraints.gridy=1;
        panel.add(processIdField, toolconstraints);
        toolconstraints.gridx=0;
        toolconstraints.gridy=2;
        toolconstraints.gridwidth=2;
        panel.add(saveResultsFile, toolconstraints);
        add(panel);
    }

    private void saveResults() {
        String idString = processIdField.getText();
        Long id = new Long(idString);
        String resultsFileContent = simulationResultsService.getResultsFileContent(id);
        saveAndWrite.promptSaveFileWithFileContent(resultsFileContent);
    }

    public void importOrders() {
        Reader streamReader = saveAndWrite.promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            return;
        }
        JsonReader jsonReader = new JsonReader(streamReader);
        try {
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
