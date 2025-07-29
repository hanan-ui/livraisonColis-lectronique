// File: agents/AgentTri.java
package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import gui.AgentTriGUI;

public class AgentTri extends Agent {

    private AgentTriGUI gui;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " prêt à traiter les demandes.");

        // Enregistrer service au DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("tri-colis");
        sd.setName(getLocalName() + "-service-tri");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
            System.out.println(getLocalName() + " enregistré au DF.");
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Lancer GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            gui = new AgentTriGUI(this);
        });

        // Comportement pour recevoir messages
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if ("demanderLivraison".equals(msg.getOntology()) && msg.getPerformative() == ACLMessage.REQUEST) {
                        gui.log("Reçu REQUEST de " + msg.getSender().getLocalName());

                        ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                        inform.setOntology("traiterDemande");
                        inform.setContent("Livraison à effectuer pour : " + msg.getSender().getLocalName());
                        inform.addReceiver(new AID("AgentLivreur", AID.ISLOCALNAME));
                        send(inform);

                        gui.log("INFORM envoyé au livreur.");
                    } else if ("livrerColis".equals(msg.getOntology()) && msg.getPerformative() == ACLMessage.CONFIRM) {
                        gui.log("Reçu CONFIRM de " + msg.getSender().getLocalName());
                    }
                } else {
                    block();
                }
            }
        });
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
