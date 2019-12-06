package agents;

import behaviours.DeliveryController;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utilities.Message;

public class BigTruck extends Truck {

    protected void setup() {

        Object[] args = getArguments();
        if (args != null && args.length == 2) {
            speed = Integer.parseInt((String) args[0]);
            costPerKm = Integer.parseInt((String) args[1]);
        }
        else {
            speed = 2;
            costPerKm =5 ;
        }

        capacity = 2;
        System.out.println("Big Truck " + getAID().getName() + " is ready.");
        System.out.println("Capacity: " + capacity + "\n");
        System.out.println("Speed: " + speed + "\n");
        System.out.println("Cost: " + costPerKm + "\n");

        message = new Message(0, 0, 0, costPerKm, speed, getAID());
        deliveryManager = new DeliveryController(this);


        // Register the big truck in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("truck");
        sd.setName("BTruck");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(deliveryManager.new ChooseOtherTrucks());
        addBehaviour(new TickerBehaviour(this, 20000) {
            protected void onTick() {
                addBehaviour(deliveryManager.new RequestDelivery());
            }
        });
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("Big Truck " + getAID().getName() + " terminating.");
    }
}
