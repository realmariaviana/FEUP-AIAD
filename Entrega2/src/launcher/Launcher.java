package launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import utilities.Station;

public class Launcher {

    //private static ContainerController mainContainer;

    /*public static void main(String[] args) throws InterruptedException {

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
            createAgent("Package1", "Package",  packageArguments1);
            Thread.sleep(1000);
            createAgent("Package2", "Package",  packageArguments2);
            Thread.sleep(1000);
            createAgent("Package3", "Package",  packageArguments3);
            Thread.sleep(1000);
            createAgent("Package4", "Package",  packageArguments4);
            Thread.sleep(3000);

            //trucks

           /* Object [] bigtruckArguments1 = {""+3,""+320};
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

           /* Object [] bigtruckArguments1 = {""+3,""+320};
            Object [] bigtruckArguments2 = {""+4,""+370};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            Object [] smalltruckArguments1 = {""+3,""+230};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);*/

            /*Object [] smalltruckArguments2 = {""+4,""+250};
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

           /* Object [] smalltruckArguments1 = {""+3,""+250};
            Object [] smalltruckArguments2 = {""+4,""+250};
            Object [] smalltruckArguments3 = {""+5,""+250};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);
            createAgent("small2", "SmallTruck",  smalltruckArguments2);
            Thread.sleep(1000);
            createAgent("small3", "SmallTruck",  smalltruckArguments3);
            Thread.sleep(1000);*/

           /* Object [] bigtruckArguments1 = {""+3,""+400};
            Object [] bigtruckArguments2 = {""+4,""+400};
            createAgent("truckbig1", "BigTruck",  bigtruckArguments1);
            Thread.sleep(1000);
            createAgent("truckbig2", "BigTruck",  bigtruckArguments2);
            Thread.sleep(1000);
            Object [] smalltruckArguments1 = {""+3,""+250};
            createAgent("small1", "SmallTruck",  smalltruckArguments1);
            Thread.sleep(1000);*/

            /*Object [] smalltruckArguments2 = {""+4,""+250};
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
}*/



//classificação: ao receber uma encomenda prever se vai ser ele a ficar com ela ou não: assim dava para renegociação

    private static jade.core.Runtime runtime;
    private static Profile profile;
    private static ContainerController mainContainer;
    private static ArrayList<Integer> costPerKmM = new ArrayList<Integer>();
    private static ArrayList<Integer> priorityM = new ArrayList<Integer>();
    public static ArrayList<Integer> speedM = new ArrayList<Integer>();
    public static ArrayList<Double> distanceM = new ArrayList<Double>();
    private static int numberPackages = 0;
    private static int numberSmallTrucks = 0;
    private static int numberBigTrucks = 0;
    public static PrintWriter out;
    public static PrintWriter out2;

    public static void main(String [] args) throws InterruptedException, IOException {
        createJade();
        parseText();
    }


   public static double calculateAvgCostPerKm() {
        double soma=0;
        for(int i = 0; i< costPerKmM.size(); i++) {
            soma+= costPerKmM.get(i);
        }
        return soma/(numberBigTrucks + numberSmallTrucks);
    }

    public static double calculateAvgPriority() {
        double soma=0;
        for(int i=0; i<priorityM.size();i++) {
            soma+= priorityM.get(i);
        }
        return soma/numberPackages;
    }

    public static double calculateAvgSpeed() {
        double soma=0;
        for(int i=0; i<speedM.size();i++) {
            soma+= speedM.get(i);
        }
        return soma/(numberBigTrucks + numberSmallTrucks);
    }

    public static double calculateAvgDistance() {
        double soma=0;
        for(int i=0; i<distanceM.size();i++) {
            soma+= distanceM.get(i);
        }
        return soma/numberPackages;
    }

    public static double calculateAvgTime() {
        double soma=0;
        for(int i = 0; i< Station.times.size(); i++) {
            soma+= Station.times.get(i);
        }
        return soma/numberPackages;
    }


    public static void parseText() throws InterruptedException, IOException {

        File log = new File("main_log.csv");
        File log2 = new File("class_log.csv");

        File file = new File("data.txt");
        if(log.exists()==false){
            try {
                System.out.println("não criou");
                log.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] info = line.split("-");

                out = new PrintWriter(new FileWriter(log, true));
                out2 =new PrintWriter(new FileWriter(log2, true));

                random_generator(Integer.parseInt(info[0]),Integer.parseInt(info[1]),Integer.parseInt(info[2]));

                if(numberPackages < 15)
                    Thread.sleep(180000);
                else if(numberPackages < 20)
                    Thread.sleep(150000);
                else
                    Thread.sleep(200000);
                out.append(numberBigTrucks + "," + numberSmallTrucks + "," + numberPackages + "," + calculateAvgDistance() + "," + calculateAvgTime() + "," + calculateAvgSpeed() + "," + calculateAvgPriority() +  "," + calculateAvgCostPerKm() + "," + Station.totalCost/100 + "\n");
                try {
                    mainContainer.kill();
                } catch (StaleProxyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                profile = new ProfileImpl();
                profile.setParameter(Profile.CONTAINER_NAME, "TestContainer");
                profile.setParameter(Profile.MAIN_HOST, "localhost");
                mainContainer = runtime.createMainContainer(profile);
                out.close();
                out2.close();
                Station.totalCost=0;
                distanceM.clear();
                Station.times.clear();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void random_generator(int numBigTrucks, int numSmallTrucks, int numPackages) {

        int smallTruck = 1;
        int bigTruck = 1;
        int packages = 1;
        Random rand = new Random();
        String agentNick;
        numberSmallTrucks = numSmallTrucks;
        numberBigTrucks = numBigTrucks;
        numberPackages = numPackages;
        costPerKmM = new ArrayList<Integer>();
        priorityM = new ArrayList<Integer>();
        speedM =new ArrayList<Integer>();

        for(int i = 0; i < numBigTrucks; i++) {
            int costPerKm = rand.nextInt(40) + 20;
            int speed = rand.nextInt(7) + 5;
            costPerKmM.add(costPerKm);
            speedM.add(speed);
            agentNick = "bigtruck" + bigTruck;
            bigTruck++;
            Object [] agentArguments = {""+speed, ""+costPerKm};
            createAgent(agentNick,"BigTruck", agentArguments);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < numSmallTrucks; i++) {
            int costPerKm = rand.nextInt(20) + 4;
            int speed = rand.nextInt(12) + 7;
            costPerKmM.add(costPerKm);
            speedM.add(speed);
            agentNick = "smalltruck" + smallTruck;
            smallTruck++;
            Object [] agentArguments = {""+speed, ""+costPerKm};
            createAgent(agentNick,"SmallTruck", agentArguments);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        for(int i = 0; i < numberPackages; i++) {
            int x= rand.nextInt(30)+1;
            int y=rand.nextInt(30)+1;
            int priority= rand.nextInt(10)+1;
            priorityM.add(priority);
            double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))*2;
            distanceM.add(distance);
            agentNick = "package" + packages;
            packages++;
            Object [] agentArguments = {""+priority,""+x, ""+y};
            createAgent(agentNick,"Package", agentArguments);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    public static void createJade() {
        runtime = jade.core.Runtime.instance();
        profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, "TestContainer");
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        mainContainer = runtime.createMainContainer(profile);
    }

    public static void createAgent(String agentNick, String agentName, Object [] agentArguments) {

        try {
            AgentController ac = mainContainer.createNewAgent(agentNick, "agents." + agentName, agentArguments);
            ac.start();
        } catch (jade.wrapper.StaleProxyException e) {
            System.err.println("Error launching agent...");
        }
    }}
