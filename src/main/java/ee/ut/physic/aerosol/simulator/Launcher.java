package ee.ut.physic.aerosol.simulator;

import ee.ut.physic.aerosol.simulator.ui.UIRunner;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        // Instantiate configuration
        Configuration.getInstance();
        UIRunner uiRunner = new UIRunner();
        SwingUtilities.invokeLater(uiRunner);
    }
}
