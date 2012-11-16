package ee.ut.physic.aerosol.simulator.ui;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SaveAndWrite {
    final Logger logger = LoggerFactory.getLogger(SaveAndWrite.class);
    private OrderForm orderForm;
    private JFileChooser fileChooser = new JFileChooser();

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
            writer = new CSVWriter(new FileWriter(file), '\t');
            String[] entries = fileContent.split("#");
            writer.writeNext(entries);
            writer.close();

        } catch (IOException e) {
            logger.warn("Could not write to path: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void saveFile() {
        String fileString = "";
        for (String title : orderForm.getAllParameterValues().keySet()) {
            if (fileString == "") {
                fileString += title;
            } else {
                fileString += "#" + title;
            }
            Map<String, String> valueMap = orderForm.getAllParameterValues().get(title);
            for (String name : valueMap.keySet()) {
                fileString += "#" + name + "#" + valueMap.get(name);
            }
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

    public void openFile() {
        Reader streamReader = promptOpenFileAsInputStreamReader();
        if (streamReader == null) {
            return;
        }
        CSVReader reader = new CSVReader(streamReader, '\t');
        try {
            String[] fileRow = reader.readNext();
            Map<String, Map<String, String>> allValues = new HashMap<String, Map<String, String>>();
            Map<String, String> values = new HashMap<String, String>();

            String mainName = "";
            String lastValueName = "";
            for (String item : fileRow) {
                if (item.startsWith("forest") || item.startsWith("free")) {
                    lastValueName = item;
                } else if (Pattern.compile("^[a-zA-Z]").matcher(item).find()) {
                    if (mainName != "") {
                        allValues.put(mainName, values);
                    }
                    mainName = item;
                    values = new HashMap<String, String>();
                } else {
                    values.put(lastValueName, item);
                }
            }
            allValues.put(mainName, values);

            orderForm.setAllParameterValues(allValues);
        } catch (IOException e) {
            logger.warn("File could not be parsed as a CSV file");
        }
    }
}
