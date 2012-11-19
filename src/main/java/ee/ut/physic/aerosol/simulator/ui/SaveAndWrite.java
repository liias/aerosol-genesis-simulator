package ee.ut.physic.aerosol.simulator.ui;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

	public void openFile() {
		Reader streamReader = promptOpenFileAsInputStreamReader();
		if (streamReader == null) {
			return;
		}
		CSVReader reader = new CSVReader(streamReader, ';');
		try {
			String[] fileRow = reader.readNext();
			ArrayList<String> allValues = new ArrayList<String>();
			for (String item : fileRow) {
				allValues.add(item);
			}
			// since last value from file may be "", it will get lost from end of line
			if(fileRow.length == 127) {
				allValues.add("");
			}

			orderForm.setAllParameterValues(allValues);
		} catch (IOException e) {
			logger.warn("File could not be parsed as a CSV file");
		}
	}
	
	public void openBigFile() {
		Reader streamReader = promptOpenFileAsInputStreamReader();
		if (streamReader == null) {
			return;
		}
		CSVReader reader = new CSVReader(streamReader, ';');
		try {
			List<String[]> file = reader.readAll();
			Set<ArrayList<String>> stringSet = new HashSet<ArrayList<String>>();
			for (String[] row : file) {
				ArrayList<String> allValues = new ArrayList<String>();
				logger.info("ROW SIZE : " + row.length);
				for(String item : row) {
					allValues.add(item);
				}
				stringSet.add(allValues);
			}
			
//			orderForm.setAllParameterValues(allValues);
		} catch (IOException e) {
			logger.warn("File could not be parsed as a CSV file");
		}
	}
}
