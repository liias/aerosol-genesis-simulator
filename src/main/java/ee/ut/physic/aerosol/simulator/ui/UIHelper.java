package ee.ut.physic.aerosol.simulator.ui;

import javax.swing.*;
import java.awt.*;

public class UIHelper {
    final static Color INVALID_COLOR = Color.PINK;
    final static Color VALID_COLOR = Color.WHITE;

    public static void setInvalid(JTextField textField) {
        textField.setBackground(INVALID_COLOR);
    }

    public static void setInvalid(JComboBox comboBox) {
        comboBox.setBackground(INVALID_COLOR);
    }

    public static void setInvalid(JComboBox... comboBoxes) {
        for (JComboBox comboBox : comboBoxes) {
            setInvalid(comboBox);
        }
    }

    public static void setValid(JTextField textField) {
        textField.setBackground(VALID_COLOR);
    }

    public static void setValid(JComboBox... comboBoxes) {
        for (JComboBox comboBox : comboBoxes) {
            setValid(comboBox);
        }
    }

    public static void setValid(JComboBox comboBox) {
        comboBox.setBackground(VALID_COLOR);
    }
}
