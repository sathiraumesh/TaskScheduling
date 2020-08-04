package heuristics;

import heuristics.algorithms.*;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.util.WorkloadFileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Client {

    public enum Algorithm {
        MAXMIN,
        MINMIN,
        FCFS,
        TRETA,
        MET,
        MCT

    }
    public BaseBroker createBroker(Algorithm alg){

        BaseBroker broker = null;
        try {
            switch (alg){
                case MAXMIN:
                    broker = new MaxMinBroker("MaxMinBroker");
                    break;
                case MINMIN:
                    broker = new MinMinBroker("MinMinBroker");
                    break;
                case FCFS:
                    broker = new FCFSBroker("FCFSBroker");
                    break;
                case TRETA:
                    broker = new TRETABroker("TRETABroker");
                    break;
                case MET:
                    broker= new METBroker("METBroker");
                    break;
                case MCT:
                    broker= new MCTBroker("MCTBroker");
                    break;
                default:
                    broker =new FCFSBroker("FCFSBroker");;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }


    public  ArrayList<Cloudlet> createUserCloudlet(int reqTasks, int brokerId, String name){
        ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();


        //Cloudlet properties
        int id = 0;
        int pesNumber=1;
        ArrayList<Long> length = readFile(name+".txt");

        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();


        for(id=0;id<reqTasks;id++){

            Cloudlet task = new Cloudlet(id, length.get(id), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            task.setUserId(brokerId);


            //System.out.println("Task"+id+"="+(task.getCloudletLength()));

            //add the cloudlets to the list
            cloudletList.add(task);
        }

        System.out.println("SUCCESSFULLY Cloudletlist created :)");

        return cloudletList;

    }


    public  ArrayList<Cloudlet> createUserCloudletWorkload(int brokerId,int tasks){
        WorkloadFileReader wfr= null;
        try {
            wfr = new WorkloadFileReader("NASA-iPSC-1993-3.1-cln.swf", 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Cloudlet> cloudletList  = new ArrayList<>(wfr.generateWorkload());
        ArrayList<Cloudlet> required = new ArrayList<>();

        for (int i = 0; i <tasks ; i++) {
            cloudletList.get(i).setUserId(brokerId);
            required.add(cloudletList.get(i));
        }
        System.out.println("SUCCESSFULLY Cloudletlist created :)");
        return required;





    }



    private static void writeToFile(String name) throws IOException {
        File fout = new File(name+".txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));




        for (int i = 0; i <1000 ; i++) {
            int num  = genrateRandomIntBetween(1000,3000);
            bw.write(num+"");
            bw.newLine();
        }
        bw.close();

    }

    public static void main(String[] args) throws IOException {
        writeToFile("100");
    }
    private static int genrateRandomIntBetween(int lowerBound,int upperBound){
        Random rnd =new Random();
        int num = lowerBound+ rnd.nextInt(upperBound-lowerBound);
        return num;
    }

    public static ArrayList<Long> readFile(String filrName){
        ArrayList<Long> list = new ArrayList<>();
        try
        {
//the file to be opened for reading
            FileInputStream fis=new FileInputStream(filrName);
            Scanner sc=new Scanner(fis);    //file to be scanned
//returns true if there is another line to read
            while(sc.hasNextLong())
            {
                list.add( sc.nextLong());      //returns the line that was skipped
            }
            sc.close();     //closes the scanner
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

}
