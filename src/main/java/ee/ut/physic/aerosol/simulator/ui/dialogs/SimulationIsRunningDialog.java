package ee.ut.physic.aerosol.simulator.ui.dialogs;

import javax.swing.*;

public class SimulationIsRunningDialog {
    public static void show() {
        JFrame parentFrame = null;
        String message = "Simulation is running. You can stop it by clicking the stop button.";
        String title = "Simulation is Running";
        JOptionPane.showMessageDialog(parentFrame,
                message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
