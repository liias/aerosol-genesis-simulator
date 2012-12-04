package ee.ut.physic.aerosol.simulator.ui;

import javax.swing.*;

public class StatusBar extends JPanel {

    JLabel processCountLabel = new JLabel();

    public StatusBar() {
        add(processCountLabel);
    }

    public void setCurrentProcessCount(int current, int total) {
        processCountLabel.setText("Running: " + current + "/" + total);
    }

    public void resetCurrentProcessCount() {
        processCountLabel.setText("");
    }
}
