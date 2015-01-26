package unalcol.gui.log;

import java.awt.BorderLayout;

import javax.swing.*;

/**
 * <p>Title: LogPanel</p>
 *
 * <p>Description: A panel for showing output and error messages</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class LogPanel extends JPanel {
    /**
     * Creates a panel for showing output and error messages
     */
    public LogPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(logPanelLayout);
        jOutTextArea = new JTextArea(4, 200);
        jErrorTextArea = new JTextArea(4, 200);
        jOutPanel.setLayout(outBorderLayout);
        jOutPanel.add(new JScrollPane(jOutTextArea),
                      java.awt.BorderLayout.CENTER);
        jErrorPanel.setLayout(errorBorderLayout);
        this.add(jLogPaneTab, java.awt.BorderLayout.CENTER);
        jLogPaneTab.add(jOutPanel, "out");
        jLogPaneTab.add(jErrorPanel, "error");
        jErrorPanel.add(new JScrollPane(jErrorTextArea),
                        java.awt.BorderLayout.CENTER);
    }

    JTabbedPane jLogPaneTab = new JTabbedPane();
    BorderLayout logPanelLayout = new BorderLayout();
    JPanel jOutPanel = new JPanel();
    JPanel jErrorPanel = new JPanel();
    BorderLayout outBorderLayout = new BorderLayout();
    JTextArea jOutTextArea;
    BorderLayout errorBorderLayout = new BorderLayout();
    JTextArea jErrorTextArea;

    /**
     * Gets the TextArea used for showing the output messages
     * @return TextArea used for showing the output messages
     */
    public JTextArea getOutArea() {
        return this.jOutTextArea;
    }

    /**
     * Gets the TextArea used for showing the error messages
     * @return TextArea used for showing the error messages
     */
    public JTextArea getErrorArea() {
        return this.jErrorTextArea;
    }

}
