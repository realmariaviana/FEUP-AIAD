import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import utilities.Station;

public class Launcher {

    private static ContainerController mainContainer;

    public static void main(String[] args) throws InterruptedException {

        try {
            Runtime myRuntime = Runtime.instance();

            Profile myProfile = new ProfileImpl();

            mainContainer = myRuntime.createMainContainer(myProfile);
            AgentController snif = mainContainer.createNewAgent("Sniffer", "jade.tools.sniffer.Sniffer", new Object[0]);
            AgentController ac = mainContainer.acceptNewAgent("myRMA", new jade.tools.rma.rma());

            //packages
            Object [] packageArguments1 = {""+4,""+5, ""+6};
            Object [] packageArguments2 = {""+10,""+3, ""+2};
            Object [] packageArguments3 = {""+7,""+2, ""+8};
            Object [] packageArguments4 = {""+1,""+2, ""+1};
            Object [] packageArguments5 = {""+13,""+10, ""+10};
            createAgent("Package1", "Package",  packageArguments1);
            Thread.sleep(1000);
            createAgent("Package2", "Package",  packageArguments2);
            Thread.sleep(1000);
            createAgent("Package3", "Package",  packageArguments3);
            Thread.sleep(1000);
            createAgent("Package4", "Package",  packageArguments4);
            //Thread.sleep(1000);
            //createAgent("Package5", "Package",  packageArguments5);
            Thread.sleep(3000);

            //trucks

            /*Object [] bigtruckArguments1 = {""+3,""+320};
            Object [] bigtruckArguments2 = {""+4,""+370};
            Object [] bigtruckArguments3 = {""+5,""+400};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            createAgent("truckbig3", "BigTruck",  bigtruckArguments3);
            Thread.sleep(1000);*/

            /*Object [] smalltruckArguments1 = {""+3,""+230};
            Object [] smalltruckArguments2 = {""+4,""+250};
            Object [] smalltruckArguments3 = {""+5,""+300};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);
            createAgent("small2", "SmallTruck",  smalltruckArguments2);
            Thread.sleep(1000);
            createAgent("small3", "SmallTruck",  smalltruckArguments3);
            Thread.sleep(1000);*/

            /*Object [] bigtruckArguments1 = {""+3,""+320};
            Object [] bigtruckArguments2 = {""+4,""+370};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            Object [] smalltruckArguments1 = {""+3,""+230};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);*/

           /* Object [] smalltruckArguments2 = {""+4,""+250};
            Object [] smalltruckArguments3 = {""+5,""+300};
            createAgent("small2", "SmallTruck",  smalltruckArguments2);
            Thread.sleep(1000);
            createAgent("small3", "SmallTruck",  smalltruckArguments3);
            Thread.sleep(1000);
            Object [] bigtruckArguments3 = {""+5,""+400};
            createAgent("truckbig3", "BigTruck",  bigtruckArguments3);
            Thread.sleep(1000);*/

           //SEGUNDO


            /*Object [] bigtruckArguments1 = {""+3,""+400};
            Object [] bigtruckArguments2 = {""+4,""+400};
            Object [] bigtruckArguments3 = {""+5,""+400};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            createAgent("truckbig3", "BigTruck",  bigtruckArguments3);
            Thread.sleep(1000);*/

            /*Object [] smalltruckArguments1 = {""+3,""+250};
            Object [] smalltruckArguments2 = {""+4,""+250};
            Object [] smalltruckArguments3 = {""+5,""+250};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);
            createAgent("small2", "SmallTruck",  smalltruckArguments2);
            Thread.sleep(1000);
            createAgent("small3", "SmallTruck",  smalltruckArguments3);
            Thread.sleep(1000);*/

            /*Object [] bigtruckArguments1 = {""+3,""+400};
            Object [] bigtruckArguments2 = {""+4,""+400};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            Object [] smalltruckArguments1 = {""+3,""+250};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);*/

            Object [] smalltruckArguments2 = {""+4,""+250};
            Object [] smalltruckArguments3 = {""+5,""+250};
            createAgent("small2", "SmallTruck",  smalltruckArguments2);
            Thread.sleep(1000);
            createAgent("small3", "SmallTruck",  smalltruckArguments3);
            Thread.sleep(1000);
            Object [] bigtruckArguments3 = {""+5,""+400};
            createAgent("truckbig3", "BigTruck",  bigtruckArguments3);
            Thread.sleep(1000);


            ac.start();
            snif.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.sleep(50000);
        System.out.println("Total cost of deliveries: " + (int) Station.totalCost/100 + "€.");
        System.exit(0);
    }

    public static void createAgent(String agentNick, String agentName, Object [] agentArguments) {

        try {
            AgentController ac = mainContainer.createNewAgent(agentNick, "agents." + agentName, agentArguments);
            ac.start();
        } catch (jade.wrapper.StaleProxyException e) {
            System.err.println("Error launching agent...");
        }
    }
}
