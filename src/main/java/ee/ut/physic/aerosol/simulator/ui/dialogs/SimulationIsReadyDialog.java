package ee.ut.physic.aerosol.simulator.ui.dialogs;

import javax.swing.*;

public class SimulationIsReadyDialog {
    public static void show() {
        JFrame parentFrame = null;
        String message = "Simulation has finished running.";
        String title = "Simulation is Ready";
        JOptionPane.showMessageDialog(parentFrame,
                message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
