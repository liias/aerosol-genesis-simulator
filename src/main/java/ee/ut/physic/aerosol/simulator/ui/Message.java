package ee.ut.physic.aerosol.simulator.ui;

import javax.swing.*;

public class Message {
    public static void showError(String message) {
        showError(message, null);
    }

    public static void showError(String message, String title) {
        if (title == null) {
            title = "Error";
        }
        JOptionPane.showMessageDialog(null,
                message, title,
                JOptionPane.ERROR_MESSAGE);
    }
}
