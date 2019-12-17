package behaviours;
import agents.Package;
import jade.lang.acl.UnreadableException;
import launcher.Launcher;
import utilities.Message;
import utilities.Station;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class PackageController {
    Package agentPackage;
    private boolean beingSolved;

    public PackageController(Package pack){
        this.agentPackage = pack;
    }

    public class TreatPackage extends CyclicBehaviour{
        private int step = 0;
        @Override
        public void action() {
            switch(step){
                case 0:
                    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
                    ACLMessage msg = myAgent.receive(mt);
                    if (msg != null ) {
                        ACLMessage reply = msg.createReply();

                        if(beingSolved) {
                            reply.setPerformative(ACLMessage.REFUSE);
                        }
                        else{
                            beingSolved=true;
                            Station.listOfPackagesToTreat.remove(((Package)myAgent).getMessage());
                            Station.listOfPackagesTreated.add(((Package)myAgent).getMessage());
                            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

                            try {
                                reply.setContentObject(agentPackage.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        myAgent.send(reply);
                    }
                    else {
                        block();
                    }
            }
        }
    }

    public class AcceptTruck extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                try {
                    Message me = (Message) msg.getContentObject();
                    Launcher.out2.append(me.getPriority() + "," + me.getX() + "," + me.getY() + "," +me.getCostPerKm() + "," + me.getSpeed() + "," + me.getCapacity() + "\n");
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }

                myAgent.doDelete();
            }
            else {
                block();
            }
        }
    }
}
