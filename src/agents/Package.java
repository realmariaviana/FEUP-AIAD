package agents;

import behaviours.PackageController;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utilities.Message;
import utilities.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Package extends Agent {

    private int priority;
    private int finalPosition_x;
    private int finalPosition_y;
    private Message message;
    private PackageController manager;

    protected void setup() {

        Object[] args = getArguments();
        if (args != null && args.length == 3) {
            priority = Integer.parseInt((String) args[0]);
            finalPosition_x = Integer.parseInt((String) args[1]);
            finalPosition_y = Integer.parseInt((String) args[2]);
        }
        else {
            priority = 0;
            finalPosition_x = 0;
            finalPosition_y = 0;
        }

        manager = new PackageController(this);

        System.out.println("New Package " + getAID().getName());
        System.out.println("Priority: " + priority + "\n");

        message = new Message(priority, finalPosition_x, finalPosition_y, 0, 0, getAID());
        Station.listOfPackagesToTreat.add(message);
        ArrayList<Message> temp =  Station.listOfPackagesToTreat;
        sortPackages();

        // Register the package in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("package");
        sd.setName("Package");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Add Package Behaviours
        addBehaviour(manager.new TreatPackage());
        addBehaviour(manager.new AcceptTruck());
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

    }

    public void sortPackages(){
        Collections.sort(Station.listOfPackagesToTreat, new Comparator<Message>()
        {
            @Override
            public int compare(Message m1, Message m2) {
                return m1.getPriority() - m2.getPriority();
            }
        });
    }

    public Message getMessage() { return message; }
}
