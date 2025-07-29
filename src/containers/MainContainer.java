// File: containers/MainContainer.java
package containers;

import jade.core.ProfileImpl;
import jade.core.Profile;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.GUI, "true"); // Activer GUI de JADE

        AgentContainer mainContainer = runtime.createMainContainer(profile);
        System.out.println("Main Container lancé.");

        mainContainer.createNewAgent("AgentClient", "agents.AgentClient", null).start();
        mainContainer.createNewAgent("AgentTri", "agents.AgentTri", null).start();
        mainContainer.createNewAgent("AgentLivreur", "agents.AgentLivreur", null).start();
    }
}
