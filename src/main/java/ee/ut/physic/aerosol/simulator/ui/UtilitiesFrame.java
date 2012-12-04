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
    GridBagConstraints constraints;
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

        constraints = new GridBagConstraints();
        JPanel panel = new JPanel(gridBagLayout);

        JLabel databaseLabel = new JLabel("<html><b>Import or Export Database</b>");

        JButton importButton = new JButton("Import Database");
        importButton.setToolTipText("Import data from another database");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    importOrders();
                } catch (GeneralException e1) {
                    e1.printStackTrace();
                }
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
                try {
                    saveResults();
                } catch (GeneralException e1) {
                    e1.printStackTrace();
                }
            }
        });

        final JCheckBox showToolbarLabels = new JCheckBox("Labels under toolbar buttons?");
        showToolbarLabels.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (orderToolBar != null) {
                    orderToolBar.showLabels(showToolbarLabels.isSelected());
                }
            }
        });


        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        panel.add(databaseLabel, constraints);
        constraints.gridy = 1;
        panel.add(importButton, constraints);


        constraints.gridx = 1;
        panel.add(exportButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1.0;
        constraints.ipady = 25;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(resultsDescription, constraints);

        //reset
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipady = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        constraints.gridy = 3;
        panel.add(processIdLabel, constraints);
        constraints.gridx = 1;

        panel.add(processIdField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(saveResultsFile, constraints);

        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(showToolbarLabels, constraints);


        add(panel);
    }

    private void saveResults() throws GeneralException {
        String idString = processIdField.getText();
        try {
            Long id = new Long(idString);
            String resultsFileContent = simulationResultsService.getResultsFileContent(id);
            saveAndWrite.promptSaveFileWithFileContent(resultsFileContent);
        } catch (GeneralException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new GeneralException("Process ID must be a number");
        }
    }

    public void importOrders() throws GeneralException {
        Reader streamReader = saveAndWrite.promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            return;
        }
        JsonReader jsonReader = new JsonReader(streamReader);
        try {
            java.lang.reflect.Type listType = new TypeToken<ArrayList<SimulationOrder>>() {
            }.getType();
            List<SimulationOrder> orders = new Gson().fromJson(jsonReader, listType);
            simulationOrderService.importOrders(orders);
        } catch (JsonSyntaxException e) {
            logger.warn("File could not be parsed as a json file");
            throw new GeneralException("Couldn't import, because selected file is not valid json file.");
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
