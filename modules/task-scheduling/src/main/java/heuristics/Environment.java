package heuristics;

import heuristics.algorithms.BaseBroker;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.Calendar;
import java.util.List;

public class Environment {

    /** The cloudletList. */
    private static List<Cloudlet> cloudletList;

    /** The vmlist. */
    private static List<Vm> vmlist;

    private static Infrastructure inf = new Infrastructure();

    private static Client cli = new Client();


    public static void main(String[] args) {

        try {
            // First step: Initialize the CloudSim package. It should be called
            // before creating any entities.
            int num_user = 1;   // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

            // Second step: Create Datacenters
            //Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
            @SuppressWarnings("unused")
            Datacenter datacenter0 = inf.createUserDatacenter("Data_Center_0");

            //Third step: Create Broker
            BaseBroker broker = cli.createBroker(Client.Algorithm.);
            int brokerId = broker.getId();

            //Fourth step: Create one virtual machine
            vmlist = inf.createRequiredVms(20,brokerId);


            //submit vm list to the broker
            broker.submitVmList(vmlist);


            //Fifth step: Create two Cloudlets
            cloudletList = cli.createUserCloudletWorkload(brokerId,100);

            //submit cloudlet list to the broker
            broker.submitCloudletList(cloudletList);


            //call the scheduling function via the broker
            broker.scheduleTaskstoVms();


            // Sixth step: Starts the simulation
            CloudSim.startSimulation();


            // Final step: Print results when simulation is over
            List<Cloudlet> resultList = broker.getCloudletReceivedList();
            List<Vm>  vmist =  broker.getVmList();


            CloudSim.stopSimulation();

             Metrics.printCloudletList(resultList);
             Metrics.calculateMakeSpan(resultList);
             Metrics.calculateThrougput(resultList);




        }
        catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
        }
    }
}
