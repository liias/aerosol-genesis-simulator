package ee.ut.physic.aerosol.simulator.ui;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import ee.ut.physic.aerosol.simulator.service.simulation.MultipleOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveAndWrite {
    final Logger logger = LoggerFactory.getLogger(SaveAndWrite.class);
    private OrderForm orderForm;
    private JFileChooser fileChooser = new JFileChooser();

    @Autowired
    private MultipleOrderService multipleOrderService;

    public SaveAndWrite(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public void promptSaveFileWithFileContent(String fileContent) {
        int returnVal = fileChooser.showSaveDialog(orderForm);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            logger.debug("Save dialog canceled");
            return;
        }
        File file = fileChooser.getSelectedFile();
        logger.debug("Saving: " + file.getName());

        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            logger.warn("Could not write to path: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void promptSaveFileWithCSVContent(String fileContent) {
        int returnVal = fileChooser.showSaveDialog(orderForm);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            logger.debug("Save dialog canceled");
            return;
        }
        File file = fileChooser.getSelectedFile();
        logger.debug("Saving: " + file.getName());

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(file), ';');
            String[] entries = fileContent.split(";");
            writer.writeNext(entries);
            writer.close();

        } catch (IOException e) {
            logger.warn("Could not write to path: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void saveFile() {
        String fileString = "";
        for (String title : orderForm.getAllParameterValuesArray()) {
            fileString += title + ";";
        }
        promptSaveFileWithCSVContent(fileString);
    }

    public Reader promptOpenFileAsInputStreamReader() {
        int returnVal = fileChooser.showOpenDialog(orderForm);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            logger.debug("Open dialog canceled");
            return null;
        }
        File file = fileChooser.getSelectedFile();
        logger.debug("Opening: " + file.getName());
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new InputStreamReader(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openParametersFromFile() throws GeneralException {
        Reader streamReader = promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            // open dialog was canceled
            return;
        }
        CSVReader reader = new CSVReader(streamReader, ';');
        try {
            String[] fileRow = reader.readNext();
            if (fileRow == null) {
                throw new GeneralException("The CSV file you try to open seems to be empty");
            } else if (fileRow.length < 10) {
                throw new GeneralException("The CSV file you try to open seems to be invalid");
            }
            ArrayList<String> allValues = new ArrayList<String>();
            Collections.addAll(allValues, fileRow);
            // since last value from file may be "", it will get lost from end of line
            if (fileRow.length == 127) {
                allValues.add("");
            }

            orderForm.setAllParameterValues(allValues);
        } catch (IOException e) {
            throw new GeneralException("File could not be parsed as a CSV file");
        }
    }

    public void openMultipleOrdersAndSimulate(OrderToolBar toolbar) throws GeneralException {
        Reader streamReader = promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            return;
        }
        CSVReader reader = new CSVReader(streamReader, ';');
        try {
            List<String[]> file = reader.readAll();
            if (file.size() == 0) {
                throw new GeneralException("The CSV file you try to open seems to be empty");
            }
            ArrayList<ArrayList<String>> stringSet = new ArrayList<ArrayList<String>>();
            for (String[] row : file) {
                if (row == null) {
                    throw new GeneralException("The CSV file you try to open seems to be empty");
                } else if (row.length < 10) {
                    throw new GeneralException("The CSV file you try to open seems to be invalid");
                }
                ArrayList<String> allValues = new ArrayList<String>();
                logger.info("ROW SIZE : " + row.length);
                Collections.addAll(allValues, row);
                if (row.length == 127) {
                    allValues.add("");
                }
                stringSet.add(allValues);
            }

            multipleOrderService.init(orderForm, toolbar, stringSet);

        } catch (IOException e) {
            throw new GeneralException("File could not be parsed as a CSV file");
        }
    }
}
