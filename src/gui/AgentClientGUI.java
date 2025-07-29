
package gui;

import agents.AgentClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AgentClientGUI extends JFrame {

    private JTextArea logArea;
    private JButton sendRequestButton;
    private AgentClient agent;

    public AgentClientGUI(AgentClient agent) {
        this.agent = agent;

        setTitle("Client - Gestion des demandes");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30, 144, 255)); // bleu
        JLabel title = new JLabel("Agent Client");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

       
        sendRequestButton = new JButton(" Envoyer une demande de livraison");
        sendRequestButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendRequestButton.setFocusPainted(false);
        sendRequestButton.setBackground(new Color(0, 153, 76));
        sendRequestButton.setForeground(Color.WHITE);
        sendRequestButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sendRequestButton.addActionListener(this::sendRequest);

       
        sendRequestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendRequestButton.setBackground(new Color(0, 123, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendRequestButton.setBackground(new Color(0, 153, 76));
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(sendRequestButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendRequest(ActionEvent e) {
        sendRequestButton.setEnabled(false);
        agent.sendRequest();
        sendRequestButton.setEnabled(true);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }
}
