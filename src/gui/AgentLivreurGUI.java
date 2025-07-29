
package gui;

import javax.swing.*;
import java.awt.*;
import agents.AgentLivreur;

public class AgentLivreurGUI extends JFrame {

    private JTextArea logArea;
    private DefaultListModel<String> deliveryListModel;
    private JList<String> deliveryList;
    private JButton confirmButton;

    private AgentLivreur agent;

    public AgentLivreurGUI(AgentLivreur agent) {
        this.agent = agent;

        setTitle(" AgentLivreur - Gestion des livraisons");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 153, 76));
        JLabel title = new JLabel("Agent Livreur");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

       
        deliveryListModel = new DefaultListModel<>();
        deliveryList = new JList<>(deliveryListModel);
        deliveryList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deliveryList.setBackground(new Color(240, 255, 240));
        deliveryList.setSelectionBackground(new Color(0, 123, 50));
        deliveryList.setSelectionForeground(Color.WHITE);
        JScrollPane deliveryScrollPane = new JScrollPane(deliveryList);

       
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setBackground(new Color(245, 245, 245));
        JScrollPane logScrollPane = new JScrollPane(logArea);

      
        confirmButton = new JButton("Confirmer la livraison");
        confirmButton.setBackground(new Color(0, 153, 76));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

       
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmButton.setBackground(new Color(0, 123, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmButton.setBackground(new Color(0, 153, 76));
            }
        });

        confirmButton.addActionListener(e -> confirmSelectedDelivery());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(deliveryScrollPane, BorderLayout.CENTER);
        centerPanel.add(confirmButton, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel, logScrollPane);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }

    public void addDelivery(String deliveryInfo, String sender) {
        SwingUtilities.invokeLater(() -> {
            deliveryListModel.addElement(deliveryInfo);
            log("Nouvelle livraison ajoutée : " + deliveryInfo);
        });
    }

    private void confirmSelectedDelivery() {
        String selected = deliveryList.getSelectedValue();
        if (selected != null) {
            agent.confirmDelivery(selected);
            deliveryListModel.removeElement(selected);
            log("Livraison confirmée : " + selected);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une livraison.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }
}
