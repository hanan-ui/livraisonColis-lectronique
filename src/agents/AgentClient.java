
package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import gui.AgentClientGUI;

public class AgentClient extends Agent {

    private AgentClientGUI gui;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " prêt.");

        
        javax.swing.SwingUtilities.invokeLater(() -> {
            gui = new AgentClientGUI(this);
        });
    }

    
    public void sendRequest() {
        new Thread(() -> {
            try {
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("tri-colis");
                template.addServices(sd);

                DFAgentDescription[] result = DFService.search(this, template);

                if (result.length > 0) {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.setOntology("demanderLivraison");
                    msg.setContent("Je souhaite faire livrer un colis.");
                    msg.addReceiver(result[0].getName());
                    send(msg);

                    gui.log("REQUEST envoyé à " + result[0].getName().getLocalName());
                } else {
                    gui.log("Aucun agent de tri trouvé.");
                }
            } catch (FIPAException fe) {
                fe.printStackTrace();
                gui.log("Erreur : " + fe.getMessage());
            }
        }).start();
    }

}
