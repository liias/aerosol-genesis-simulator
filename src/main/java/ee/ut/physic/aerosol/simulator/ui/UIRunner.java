package ee.ut.physic.aerosol.simulator.ui;

import javax.swing.*;

public class UIRunner implements Runnable {
    @Override
    public void run() {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(lookAndFeel)) {
            lookAndFeel = "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel";
            UIManager.put("Synthetica.window.decoration", false);
            //SyntheticaLookAndFeel.setFont("Dialog", 12);
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            System.out.println("No such lookAndFeel class: " + lookAndFeel);
            //e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
