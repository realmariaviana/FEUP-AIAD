package behaviours;

import jade.core.behaviours.TickerBehaviour;
import launcher.Launcher;
import utilities.Station;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import jade.core.AID;
import agents.Truck;
import java.io.IOException;
import utilities.Message;
import jade.lang.acl.UnreadableException;


public class DeliveryController {

    Truck truck;
    Message pendentDelivery;
    Message currentDelivery;

    public DeliveryController(Truck truck) {
        this.truck = truck;
    }

    public double calculateDistance(Message packageMessage) {
        double distanceToDelivery = Math.sqrt(Math.pow(packageMessage.getX(), 2) + Math.pow(packageMessage.getY(), 2));
        double distanceTotal = distanceToDelivery * 2;
        return distanceTotal;
    }

    public double calculateCost(Message truckMessage, Message packageMessage){
        double cost = 0;
        double number_packages=1;

        if(truck.isFull())
            return 9999999;
        else if(truck.getCapacity()>1)
            number_packages =2;

        cost = (calculateDistance(packageMessage) * truckMessage.getCostPerKm())/number_packages;

        return cost;
    }

    public class RequestDelivery extends Behaviour{
        private int step = 0;
        private MessageTemplate mt;

        @Override
        public void action() {
            switch(step){
                case 0:
                    if(Station.listOfPackagesToTreat.size()>0) {
                        ACLMessage cfp = new ACLMessage(ACLMessage.PROPOSE);
                        pendentDelivery = Station.listOfPackagesToTreat.get(Station.listOfPackagesToTreat.size() - 1);
                        cfp.addReceiver(pendentDelivery.getSenderID());
                        System.out.println("Proposing delivery to package " + pendentDelivery.getSenderID().getLocalName());
                        cfp.setConversationId("delivery");
                        cfp.setReplyWith("propose" + System.currentTimeMillis());
                        myAgent.send(cfp);

                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId("delivery"),
                                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = 1;
                    }
                    else if(Station.listOfPackagesToTreat.size()==0) {
                        step = 2;
                        myAgent.removeBehaviour(this);
                    }
                    break;

                case 1:

                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                            myAgent.addBehaviour(new InformTrucks());
                            step =2;
                        } else if (reply.getPerformative() == ACLMessage.REFUSE) {
                            pendentDelivery = null;
                            step = 0;
                        }
                    }
            }
        }

        @Override
        public boolean done() {
            return (step>1);
        }
    }



    public class InformTrucks extends Behaviour {
        private int numberReplies =0;
        private int step = 0;
        private MessageTemplate mt;
        private ArrayList<ACLMessage> trucksMessage = new ArrayList<ACLMessage>();
        AID bestTruck = truck.getAID();

        @Override
        public void action() {
            switch (step) {
                case 0:
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    AID[] agents = truck.getTrucksAgents();

                    if (agents.length == 1) {
                        break;
                    }

                    for (int i = 0; i < agents.length; ++i) {
                        if (!agents[i].equals(myAgent.getAID()))
                            cfp.addReceiver(agents[i]);
                    }

                    cfp.setConversationId("information_about_truck");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis());

                    try {
                        cfp.setContentObject(pendentDelivery);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myAgent.send(cfp);
                    step = 1;
                    break;

                case 1:
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("information_about_truck"),MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
                    ACLMessage reply = myAgent.receive(mt);

                    if (reply != null){
                        if(reply.getPerformative() == ACLMessage.PROPOSE){
                            trucksMessage.add(reply);
                            numberReplies++;
                        }

                        if(numberReplies >= truck.getTrucksAgents().length-1){
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;
                case 2:

                    double cost = calculateCost(truck.getMessage(), pendentDelivery);

                    for(int i = 0; i < trucksMessage.size(); ++i){
                        if(Double.parseDouble(trucksMessage.get(i).getContent()) < cost){
                            cost = Double.parseDouble(trucksMessage.get(i).getContent());
                            bestTruck = trucksMessage.get(i).getSender();
                        }
                    }

                    System.out.println("Truck " + bestTruck.getLocalName() + " will attend delivery " + pendentDelivery.getSenderID().getLocalName() + ". Cost: " +  cost/100 + "€.");
                    Station.totalCost += cost;
                    if(bestTruck.equals(myAgent.getAID())) {
                        step = 3;
                    } else {
                        step = 4;
                    }

                    break;

                case 3:

                    ACLMessage refuse = new ACLMessage(ACLMessage.REFUSE);
                    for(int i = 0; i < trucksMessage.size(); ++i){
                        if(!trucksMessage.get(i).getSender().equals(myAgent.getAID()))
                            refuse.addReceiver(trucksMessage.get(i).getSender());
                    }

                    refuse.setConversationId("information_about_truck");


                    refuse.setReplyWith("refuse" + System.currentTimeMillis());
                    myAgent.send(refuse);

                    step = 5;

                    currentDelivery = pendentDelivery;
                    Launcher.out2.append(currentDelivery.getPriority() + "," + currentDelivery.getX() + "," + currentDelivery.getY() + "," +truck.getCostPerKm() + "," + truck.getSpeed() + "," + truck.getCapacity() + ","+"yes" +"\n");
                    truck.setCurrent_capacity(truck.getCurrent_capacity()+1);
                    if(truck.isFull() || Station.listOfPackagesToTreat.size() == 0)
                        myAgent.addBehaviour(new MakingDelivery());
                    break;
                case 4:

                    ACLMessage ref = new ACLMessage(ACLMessage.REFUSE);
                    ACLMessage accept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);

                    for (int i = 0; i < trucksMessage.size(); ++i) {
                        if(!trucksMessage.get(i).getSender().equals(myAgent.getAID()) && !trucksMessage.get(i).getSender().equals(bestTruck)) {
                            ref.addReceiver(trucksMessage.get(i).getSender());
                        }
                        else if(trucksMessage.get(i).getSender().equals(bestTruck)) {
                            accept.addReceiver(trucksMessage.get(i).getSender());
                        }
                    }
                    Launcher.out2.append(pendentDelivery.getPriority() + "," + pendentDelivery.getX() + "," + pendentDelivery.getY() + "," +truck.getCostPerKm() + "," + truck.getSpeed() + "," + truck.getCapacity() + ","+"no" +"\n");
                    ref.setConversationId("information_about_truck");
                    myAgent.send(ref);
                    accept.setConversationId("information_about_truck");


                    try {
                        accept.setContentObject(pendentDelivery);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myAgent.send(accept);

                    step = 5;
                    currentDelivery = pendentDelivery;
                    break;
            }
        }

        @Override
        public boolean done() {
            return (step > 4);
        }
    }

    public class ChooseOtherTrucks extends CyclicBehaviour {

        private int step = 0;
        private Message position;

        @Override
        public void action() {
            switch (step){
                case 0:
                    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                    ACLMessage msg = myAgent.receive(mt);

                    if (msg != null){
                        ACLMessage reply = msg.createReply();
                        position = new Message();

                        try {
                            position = (Message) msg.getContentObject();
                        } catch (UnreadableException e1) {
                            e1.printStackTrace();
                        }

                        double cost = calculateCost(truck.getMessage(), position);
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent(Double.toString(cost));

                        myAgent.send(reply);
                        step = 1;

                    } else {
                        block();
                    }

                    break;

                case 1:
                    mt = MessageTemplate.MatchConversationId("information_about_truck");

                    msg = myAgent.receive(mt);
                    if (msg != null) {

                        if(msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                            try {
                                currentDelivery = (Message) msg.getContentObject();
                                truck.setCurrent_capacity(truck.getCurrent_capacity()+1);
                                Launcher.out2.append(currentDelivery.getPriority() + "," + currentDelivery.getX() + "," + currentDelivery.getY() + "," +truck.getCostPerKm() + "," + truck.getSpeed() + "," + truck.getCapacity() + ","+"yes"+ "\n");
                            } catch (UnreadableException e) {
                                e.printStackTrace();
                            }
                            if(truck.isFull() || Station.listOfPackagesToTreat.size() == 0)
                                myAgent.addBehaviour(new MakingDelivery());


                            step = 0;
                        }else{
                            Launcher.out2.append(position.getPriority() + "," + position.getX() + "," + position.getY() + "," +truck.getCostPerKm() + "," + truck.getSpeed() + "," + truck.getCapacity() + ","+"no" +"\n");
                            step=0;
                        }
                    }
                    else {
                        block();
                    }
                    break;
            }
        }
    }

    public class MakingDelivery extends Behaviour{
        int step = 0;

        @Override
        public void action(){

            double total_time = calculateDistance(currentDelivery) + truck.getSpeed() * truck.getCurrent_capacity();
            Station.times.add(total_time);
            myAgent.addBehaviour(new TickerBehaviour(myAgent, (long) (total_time * 1000)) {
                protected void onTick() {
                    currentDelivery = null;
                    truck.setCurrent_capacity(0);
                    myAgent.removeBehaviour(this);
                }
            });

            System.out.println("Truck " + myAgent.getAID().getLocalName() + " making a delivery.");
            step = 1;

        }

        @Override
        public boolean done(){
            return (step > 0);
        }

    }
}
