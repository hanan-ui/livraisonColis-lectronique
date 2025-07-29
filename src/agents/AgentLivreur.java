// File: agents/AgentLivreur.java
package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.AID; // ✅ Import nécessaire
import jade.lang.acl.ACLMessage;
import gui.AgentLivreurGUI;

public class AgentLivreur extends Agent {

    private AgentLivreurGUI gui;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " prêt à livrer.");

        javax.swing.SwingUtilities.invokeLater(() -> {
            gui = new AgentLivreurGUI(this);
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if ("traiterDemande".equals(msg.getOntology()) && msg.getPerformative() == ACLMessage.INFORM) {
                        String content = msg.getContent();
                        gui.log("Reçu INFORM : " + content);
                        gui.addDelivery(content, msg.getSender().getLocalName());
                    }
                } else {
                    block();
                }
            }
        });
    }

    // Méthode appelée depuis la GUI pour confirmer livraison
    public void confirmDelivery(String deliveryInfo) {
        ACLMessage confirm = new ACLMessage(ACLMessage.CONFIRM);
        confirm.setOntology("livrerColis");
        confirm.setContent(deliveryInfo);
        confirm.addReceiver(new AID("AgentTri", AID.ISLOCALNAME)); // ✅ corrigé
        send(confirm);

        gui.log("CONFIRM envoyé pour : " + deliveryInfo);
    }
}
