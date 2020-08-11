package heuristics.algorithms;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

public class MinMinBroker extends BaseBroker {

    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */
    public MinMinBroker(String name) throws Exception {
        super(name);
    }

    @Override
    public void scheduleTaskstoVms() {
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
            int minCloudlet = 0;
            int minVm = 0;
            double min = Double.MAX_VALUE;
            double tempMin = Double.MAX_VALUE;
            for (int i = 0; i < clist.size(); i++) {
                for (int j = 0; j < (vlist.size()); j++) {
                    tempMin = min;
                    min = Math.min(completionTime[i][j], min);
                    if (min != tempMin) {
                        minCloudlet = i;
                        minVm = j;
                    }


                }

            }


            bindCloudletToVm(clist.get(minCloudlet).getCloudletId(), minVm);



            for (int i = 0; i < clist.size(); i++) {

                completionTime[i][minVm] += min;

            }

            for (int i = 0; i < vlist.size(); i++) {
                completionTime[minCloudlet][i] = Double.MAX_VALUE;
            }


        }

    }

    private double getCompletionTime(Cloudlet cloudlet, Vm vm) {

        double waitingTime = cloudlet.getWaitingTime();
        double execTime = cloudlet.getCloudletLength() / (vm.getMips());

        double completionTime = execTime + waitingTime;

        return completionTime;
    }

    private double getExecTime(Cloudlet cloudlet, Vm vm) {
        return cloudlet.getCloudletLength() / (vm.getMips() );
    }
}
