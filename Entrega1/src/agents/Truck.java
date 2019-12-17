package agents;

import behaviours.DeliveryController;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import utilities.Message;

public abstract class Truck extends Agent {
    protected int capacity;
    protected int speed;
    protected int costPerKm;
    protected Message message;
    protected DeliveryController deliveryManager;
    protected int current_capacity=0;

    protected abstract void setup();

    protected abstract void takeDown();

    public int getCapacity() { return capacity; }

    public int getSpeed() { return speed; }

    public int getCostPerKm() { return costPerKm; }

    public AID[] getTrucks(String type) {
        AID[] agents = new AID[0];

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search( this, template);
            agents = new AID[result.length];
            for (int i = 0; i < result.length; ++i) {
                agents[i] = result[i].getName();
            }
        }

        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        return agents;
    }

    public void setCurrent_capacity(int current_capacity) {
        this.current_capacity = current_capacity;
    }

    public int getCurrent_capacity() {
        return current_capacity;
    }

    public AID[] listAllTrucks(String type) {
        AID[] agents = new AID[0];

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        template.addServices(sd);


        try {
            DFAgentDescription[] result = DFService.search(this, template);
            agents = new AID[result.length];
            for (int i = 0; i < result.length; ++i) {
                agents[i] = result[i].getName();
            }
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }


        return agents;
    }

    public AID[] getTrucksAgents() {
        AID[] agents = listAllTrucks("truck");

        if(agents != null)
            return agents;
        else
            return new AID[0];
    }

    public Message getMessage() { return message; }

    public boolean isFull(){
        if(current_capacity>=capacity)
            return true;
        else
            return false;
    }
}
