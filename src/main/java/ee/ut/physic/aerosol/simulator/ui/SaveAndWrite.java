package ee.ut.physic.aerosol.simulator.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.util.Map;

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

    public void saveFile() {
        Gson gson = new Gson();
        String jsonAllValues = gson.toJson(orderForm.getAllParameterValues());
        promptSaveFileWithFileContent(jsonAllValues);
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
        JsonReader jsonReader = new JsonReader(streamReader);
        try {
            Gson gson = new Gson();
            Map<String, Map<String, String>> allValues = gson.fromJson(jsonReader, Map.class);
            orderForm.setAllParameterValues(allValues);
        } catch (JsonSyntaxException e) {
            logger.warn("File could not be parsed as a json file");
        }
    }
}
