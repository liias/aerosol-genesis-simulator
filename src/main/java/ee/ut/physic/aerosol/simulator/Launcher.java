package ee.ut.physic.aerosol.simulator;

import ee.ut.physic.aerosol.simulator.ui.UIRunner;

import javax.swing.*;
import java.util.Locale;

public class Launcher {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        locale = Locale.ENGLISH;
        Locale.setDefault(locale);

        // Instantiate configuration
        Configuration.getInstance();
        UIRunner uiRunner = new UIRunner();
        SwingUtilities.invokeLater(uiRunner);
    }
}
