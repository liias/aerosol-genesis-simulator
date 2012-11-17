package ee.ut.physic.aerosol.simulator.ui.dialogs;

import javax.swing.*;

public class ExitConfirmationDialog {
    static String[] options = {
            "Stop Simulation and Exit",
            "Don't Exit"
    };

    public static int show() {
        JFrame parentFrame = null;
        return JOptionPane.showOptionDialog(parentFrame,
                "A simulation is still running. Are you sure you want to stop that simulation and exit?",
                "Confirm exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
    }
}