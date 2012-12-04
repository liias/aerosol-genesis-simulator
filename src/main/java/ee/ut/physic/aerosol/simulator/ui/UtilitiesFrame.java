package ee.ut.physic.aerosol.simulator.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationOrder;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationOrderService;
import ee.ut.physic.aerosol.simulator.service.simulation.SimulationResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UtilitiesFrame extends JFrame {
    final Logger logger = LoggerFactory.getLogger(UtilitiesFrame.class);
    @Autowired
    private SimulationOrderService simulationOrderService;
    @Autowired
    private SimulationResultService simulationResultsService;
    private SaveAndWrite saveAndWrite;
    private JTextField processIdField;
    GridBagConstraints toolconstraints;
    private OrderToolBar orderToolBar;

    public UtilitiesFrame(SaveAndWrite saveAndWrite) throws HeadlessException {
        super();
        this.saveAndWrite = saveAndWrite;


        URL iconUrl = getClass().getResource("/images/icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        setTitle("Utilities");
        setSize(180, 130);
        setMinimumSize(new Dimension(300, 200));

        GridBagLayout gridBagLayout = new GridBagLayout();

        toolconstraints = new GridBagConstraints();
        JPanel panel = new JPanel(gridBagLayout);

        JLabel databaseLabel = new JLabel("<html><b>Import or Export Database</b>");

        JButton importButton = new JButton("Import Database");
        importButton.setToolTipText("Import data from another database");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importOrders();
            }
        });


        JButton exportButton = new JButton("Export Database");

        exportButton.setToolTipText("Export data from your database");
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportOrders();
            }
        });

        JLabel resultsDescription = new JLabel("<html><b>Save simulation results to a file</b>");

        JLabel processIdLabel = new JLabel("Process ID: ");
        processIdField = new JTextField(5);

        JButton saveResultsFile = new JButton("Save Results");

        saveResultsFile.setToolTipText("Save results from specific process into a file");
        saveResultsFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveResults();
            }
        });

        final JCheckBox showToolbarLabels = new JCheckBox("Labels under toolbar buttons?");
        showToolbarLabels.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (orderToolBar != null) {

                }
                orderToolBar.showLabels(showToolbarLabels.isSelected());
            }
        });


        toolconstraints.gridx = 0;
        toolconstraints.fill = GridBagConstraints.VERTICAL;
        toolconstraints.gridy = 0;
        toolconstraints.gridx = 0;
        panel.add(databaseLabel, toolconstraints);
        toolconstraints.gridy = 1;
        panel.add(importButton, toolconstraints);


        toolconstraints.gridx = 1;
        panel.add(exportButton, toolconstraints);
        toolconstraints.gridx = 0;
        toolconstraints.gridy = 2;
        toolconstraints.weightx = 1.0;
        toolconstraints.ipady = 25;
        toolconstraints.anchor = GridBagConstraints.SOUTHWEST;
        toolconstraints.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(resultsDescription, toolconstraints);

        //reset
        toolconstraints.anchor = GridBagConstraints.CENTER;
        toolconstraints.ipady = 0;
        toolconstraints.weightx = 0;
        toolconstraints.gridwidth = 1;
        toolconstraints.gridy = 3;
        panel.add(processIdLabel, toolconstraints);
        toolconstraints.gridx = 1;

        panel.add(processIdField, toolconstraints);
        toolconstraints.gridx = 0;
        toolconstraints.gridy = 4;
        toolconstraints.gridwidth = 2;
        panel.add(saveResultsFile, toolconstraints);

        toolconstraints.gridy = 5;
        toolconstraints.gridwidth = 2;
        panel.add(showToolbarLabels, toolconstraints);


        add(panel);
    }

    private void saveResults() {
        String idString = processIdField.getText();
        Long id = new Long(idString);
        try {
            String resultsFileContent = simulationResultsService.getResultsFileContent(id);
            saveAndWrite.promptSaveFileWithFileContent(resultsFileContent);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
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

    public void setOrderToolBar(OrderToolBar orderToolBar) {
        this.orderToolBar = orderToolBar;
    }
}