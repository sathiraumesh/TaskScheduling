package heuristics.algorithms;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

public class MaxMinBroker extends BaseBroker {


    public MaxMinBroker(String name) throws Exception {
        super(name);
    }

    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */

    @Override
    public void scheduleTaskstoVms() {

        System.out.println("Max Min Scheduler");
        int reqTasks = cloudletList.size();
        int reqVms = vmList.size();
        //int k=0;

        ArrayList<Cloudlet> clist = new ArrayList<Cloudlet>();
        ArrayList<Vm> vlist = new ArrayList<Vm>();

        for (Cloudlet cloudlet : getCloudletList()) {
            clist.add(cloudlet);
            //System.out.println("clist:" +clist.get(k).getCloudletId());
            //k++;
        }
        //k=0;
        for (Vm vm : getVmList()) {
            vlist.add(vm);
            //System.out.println("vlist:" +vlist.get(k).getId());
            //k++;
        }


        double completionTime[][] = new double[reqTasks][reqVms];
        double execTime[][] = new double[reqTasks][reqVms];
        double time = 0.0;

        for (int i = 0; i < reqTasks; i++) {
            for (int j = 0; j < reqVms; j++) {
                time = getCompletionTime(clist.get(i), vlist.get(j));
                completionTime[i][j] = time;
                time = getExecTime(clist.get(i), vlist.get(j));
                execTime[i][j] = time;

                System.out.print(execTime[i][j] + ",");

            }
            System.out.println();

        }


        for (int c = 0; c < clist.size(); c++) {

            double max = 0;
            double tempMax = 0;
            int maxCloudlet = 0;
            int maxVm = 0;
            int minCVm = 0;
            for (int i = 0; i < clist.size(); i++) {

                for (int j = 0; j < vlist.size(); j++) {
                    tempMax = max;
                    max = Math.max(max, completionTime[i][j]);
                    if (max != tempMax) {
                        maxCloudlet = i;
                        maxVm = j;
                    }
                }


            }


            double minC = Double.MAX_VALUE;
            double tempMinC = Double.MAX_VALUE;
            for (int j = 0; j < vlist.size(); j++) {
                tempMinC = minC;
                minC = Math.min(minC, completionTime[maxCloudlet][j]);
                if (minC != tempMinC) {
                    minCVm = j;
                }
            }
            bindCloudletToVm(maxCloudlet,minCVm);

            for (int i = 0; i <clist.size() ; i++) {
                completionTime[i][minCVm] +=minC  ;
            }
            for (int i = 0; i <vlist.size() ; i++) {
                completionTime[maxCloudlet][i] = 0;
            }

        }


    }

    private double getCompletionTime(Cloudlet cloudlet, Vm vm) {

        double waitingTime = cloudlet.getWaitingTime();
        double execTime = cloudlet.getCloudletLength() / (vm.getMips() * vm.getNumberOfPes());

        double completionTime = execTime + waitingTime;

        return completionTime;
    }

    private double getExecTime(Cloudlet cloudlet, Vm vm) {
        return cloudlet.getCloudletLength() / (vm.getMips() * vm.getNumberOfPes());
    }


}
