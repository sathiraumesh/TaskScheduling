package heuristics;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Infrastructure{


    public Datacenter createUserDatacenter(String name){

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
        //    our machine
        List<Host> hostList = new ArrayList<Host>();

        // 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 4000;

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
        peList.add(new Pe(1, new PeProvisionerSimple(mips)));
        peList.add(new Pe(2, new PeProvisionerSimple(mips)));
        peList.add(new Pe(3, new PeProvisionerSimple(mips)));

        //4. Create Host with its id and list of PEs and add them to the list of machines
        int hostId=0;
        int ram = 2048; //host memory (MB)
        long storage = 1000000; //host storage
        int bw = 10000;

        for (int i = 0; i <20 ; i++) {

            hostList.add(
                    new Host(
                            i,
                            new RamProvisionerSimple(ram),
                            new BwProvisionerSimple(bw),
                            storage,
                            peList,
                            new VmSchedulerSpaceShared(peList)
                    )
            ); // This is our machine
        }



        // 5. Create a DatacenterCharacteristics object that stores the
        //    properties of a data center: architecture, OS, list of
        //    Machines, allocation policy: time- or space-shared, time zone
        //    and its price (G$/Pe time unit).
        String arch = "x86";      // system architecture
        String os = "Linux";          // operating system
        String vmm = "Xen";
        double time_zone = 10.0;         // time zone this resource located
        double cost = 3.0;              // the cost of using processing in this resource
        double costPerMem = 0.05;		// the cost of using memory in this resource
        double costPerStorage = 0.001;	// the cost of using storage in this resource
        double costPerBw = 0.0;			// the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Success!! DatacenterCreator is executed!!");
        return datacenter;
    }


    public ArrayList<Vm> createRequiredVms(int reqVms, int brokerId){

        ArrayList<Vm> vmlist = new ArrayList<Vm>();

        //VM description
        int vmid = 0;
        //int mips = 1000;
        int[] mips={
                1000, 1000, 1000, 1000, 1000,
                2000, 2000, 2000, 2000, 2000,
                3000, 3000, 3000, 3000, 3000,
                4000, 4000, 4000, 4000, 4000
        };
        long size = 1000; //image size (MB)
        int ram = 512; //vm memory (MB)
        long bw = 1000;
        int[] pesNumber = {1,1,2,3,2,1,2,2,3,1,1,1,2,3,1,1,1,2,3,1}; //number of cpus
        String vmm = "Xen"; //VMM name



        for(vmid=0;vmid<reqVms;vmid++){
            //add the VMs to the vmListn

            vmlist.add(new Vm(vmid, brokerId, mips[vmid], pesNumber[vmid], ram, bw,
                    size, vmm, new CloudletSchedulerSpaceShared()));
        }

        System.out.println("VmsCreator function Executed... SUCCESS:)");
        return vmlist;

    }

    public static double calculateCost(Cloudlet cloudlet,Vm vm){
        double unitCost = 0;
        int  pc = (int) (vm.getCurrentRequestedTotalMips());
        switch (pc){
            case 1000:
                unitCost =1;
                break;
            case 2000:
                unitCost =2;
                break;
            case 3000:
                unitCost =3;
                break;
            case 4000:
                unitCost =4;
                break;
            case 5000:
                unitCost =5;
                break;
            case 6000:
                unitCost =6;
                break;
            case 7000:
                unitCost =7;
                break;
            case 8000:
                unitCost =8;
                break;
            case 9000:
                unitCost =9;
                break;
            case 10000:
                unitCost =10;
                break;
            case 11000:
                unitCost =11;
                break;
            case 12000:
                unitCost =12;
                break;
            default:
                System.out.println("nothing");
                break;
        }

        return unitCost*cloudlet.getActualCPUTime();
    }

}
