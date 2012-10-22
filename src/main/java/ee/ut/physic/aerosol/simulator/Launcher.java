package ee.ut.physic.aerosol.simulator;

import ee.ut.physic.aerosol.simulator.ui.MainFrame;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        // Instantiate configuration
        Configuration.getInstance();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
