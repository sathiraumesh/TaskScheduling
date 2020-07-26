package heuristics;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Metrics {

    public static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;
        double min = 0;

        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
                "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
                Log.print("SUCCESS");

                Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
                        indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                        indent + indent + dft.format(cloudlet.getFinishTime()));
            }

        }

    }

    public static String  calculateMakeSpan(List<Cloudlet> list){
        int size = list.size();
        Cloudlet cloudlet;
        double makespan = 0;
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);;

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
                makespan =Math.max(cloudlet.getFinishTime(),makespan);
            }

        }
        System.out.println("MAKESPAN: "+dft.format(makespan));
        return dft.format(makespan);
    }



    public static String calculateCost(List<Cloudlet> list,List<Vm> vmlist) {
        int size = list.size();
        Cloudlet cloudlet;
        double cost = 0;

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            if(cloudlet.getStatus() ==Cloudlet.SUCCESS){


            Vm utilized=null;
            for (int j = 0; j <vmlist.size() ; j++) {
                Vm vm = vmlist.get(j);
                if (vm.getId()==cloudlet.getVmId()){
                    utilized =vm;
                }
            }

            cost+= Infrastructure.calculateCost(cloudlet,utilized);
            }
        }
        System.out.println("COST: "+ dft.format(cost));
        return dft.format(cost);
    }


    public static String calculateDegreeofImbalnce(List<Cloudlet> list,List<Vm> vmlist) {
        HashMap<Vm,Double> executionList  = new HashMap<>();
        int size = list.size();
        Cloudlet cloudlet;
        double cost = 0;

        for (int i = 0; i <vmlist.size() ; i++) {
            executionList.put(vmlist.get(i),0.0);
            vmlist.get(i).getHost();
        }

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);

            if (cloudlet.getStatus() == Cloudlet.SUCCESS) {
                Vm utilized = null;
                for (int j = 0; j < vmlist.size(); j++) {
                    Vm vm = vmlist.get(j);
                    if (vm.getId() == cloudlet.getVmId()) {
                        utilized = vm;
                    }
                }

                executionList.put(utilized, executionList.get(utilized) + cloudlet.getActualCPUTime());
            }
        }

        List<Double> executionValues = new ArrayList();
        for (int i = 0; i <vmlist.size() ; i++) {
            executionValues.add(executionList.get(vmlist.get(i)));
        }

        double min = Double.MAX_VALUE;
        double max = 0;
        double total =0;
        for (int i = 0; i <executionValues.size() ; i++) {
            min = Math.min(min,executionValues.get(i));
            max = Math.max(max,executionValues.get(i));
            total+=executionValues.get(i);
        }

        double di = (max-min)/(total/executionValues.size());
        System.out.println("DEGREE OF IMBALANCE: "+dft.format(di));
        return dft.format(di);
    }

    public static String calculateThrougput(List<Cloudlet> list ){
        DecimalFormat dft = new DecimalFormat("###.##");
        double maxFt = 0;
        int noOfCloudlets = list.size();
        double througput = 0;
        Cloudlet cloudlet =null;
        for (int i = 0; i <list.size() ; i++) {
            cloudlet = list.get(i);
            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
                double currentFt =cloudlet.getFinishTime();
                maxFt = Math.max(currentFt,maxFt);
            }

        }
        througput = noOfCloudlets/maxFt;
        System.out.println("THROUGHPUT "+dft.format(througput));
        return dft.format(througput);

    }



}
