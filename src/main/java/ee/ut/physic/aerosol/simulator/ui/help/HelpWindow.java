package ee.ut.physic.aerosol.simulator.ui.help;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

public class HelpWindow extends JFrame implements ActionListener {
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private JEditorPane editorPane;
    private URL helpURL;
//////////////////////////////////////////////////////////////////

    /**
     * HelpWindow constructor
     *
     * @param String and URL
     */
    public HelpWindow(String title, URL hlpURL) {
        super(title);
        helpURL = hlpURL;
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        try {
            editorPane.setPage(helpURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //anonymous inner listener
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent ev) {
                try {
                    if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        editorPane.setPage(ev.getURL());
                    }
                } catch (IOException ex) {
                    //put message in window
                    ex.printStackTrace();
                }
            }
        });
        getContentPane().add(new JScrollPane(editorPane));
        addButtons();
        // no need for listener just dispose
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // dynamically set location
        calculateLocation();
        setVisible(true);
        // end constructor
    }

    /**
     * An Actionlistener so must implement this method
     */
    public void actionPerformed(ActionEvent e) {
        String strAction = e.getActionCommand();
        URL tempURL;
        try {
            if ("Contents".equals(strAction)) {
                tempURL = editorPane.getPage();
                editorPane.setPage(helpURL);
            }
            if ("Close".equals(strAction)) {
                // more portable if delegated
                processWindowEvent(new WindowEvent(this,
                        WindowEvent.WINDOW_CLOSING));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * add buttons at the south
     */
    private void addButtons() {
        JButton btncontents = new JButton("Contents");
        btncontents.addActionListener(this);
        JButton btnclose = new JButton("Close");
        btnclose.addActionListener(this);
        //put into JPanel
        JPanel panebuttons = new JPanel();
        panebuttons.add(btncontents);
        panebuttons.add(btnclose);
        //add panel south
        getContentPane().add(panebuttons, BorderLayout.SOUTH);
    }

    /**
     * locate in middle of screen
     */
    private void calculateLocation() {
        Dimension screendim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(WIDTH, HEIGHT));
        int locationx = (screendim.width - WIDTH) / 2;
        int locationy = (screendim.height - HEIGHT) / 2;
        setLocation(locationx, locationy);
    }

}