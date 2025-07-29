
package gui;

import javax.swing.*;
import java.awt.*;
import agents.AgentTri;

public class AgentTriGUI extends JFrame {

    private JTextArea logArea;
    private AgentTri agent;

    public AgentTriGUI(AgentTri agent) {
        this.agent = agent;

        setTitle("AgentTri - Centre de tri");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(96, 96, 96)); 
        JLabel title = new JLabel("Agent de tri");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setBackground(new Color(245, 245, 245));
        logArea.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(logArea);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append( message + "\n");
        });
    }
}
