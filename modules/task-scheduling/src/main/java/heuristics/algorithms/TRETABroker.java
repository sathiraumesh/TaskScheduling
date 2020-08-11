package heuristics.algorithms;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TRETABroker extends BaseBroker {
    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */
    public TRETABroker(String name) throws Exception {
        super(name);
    }

    @Override
    public void scheduleTaskstoVms() {
        System.out.println("Time aware scheduling");
        HashMap<Vm, Double> resourceUsage = new HashMap<>();
        List<Vm> vms = getVmList();
        List<Cloudlet> cloudlets = getCloudletList();

        for (int i = 0; i < vms.size(); i++) {
            resourceUsage.put(vms.get(i), 0.0);
        }


        for (int i = 0; i < cloudlets.size(); i++) {
            Cloudlet cloudlet = cloudlets.get(i);


            double max = Double.MAX_VALUE;
            Vm selectedVm = null;
            for (int j = 0; j < vms.size(); j++) {
                double temp = max;
                Vm vm = vms.get(j);


                double usage = resourceUsage.get(vm);
                double cpu = cloudlet.getCloudletLength() / vm.getMips();
                max = Math.min(cpu + usage, max);
                if (max != temp) {
                    selectedVm = vm;
                }

            }

            resourceUsage.put(selectedVm, resourceUsage.get(selectedVm) + cloudlet.getCloudletLength()/ selectedVm.getMips());
            bindCloudletToVm(cloudlet.getCloudletId(), selectedVm.getId());

        }


    }


}
