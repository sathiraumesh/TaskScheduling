package heuristics.algorithms;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.ArrayList;

public class FCFSBroker  extends BaseBroker{
    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */
    public FCFSBroker(String name) throws Exception {
        super(name);
    }

    @Override
    public void scheduleTaskstoVms() {
        ArrayList<Cloudlet> clist = new ArrayList<Cloudlet>();

        for (Cloudlet cloudlet : getCloudletSubmittedList()) {
            clist.add(cloudlet);
            //System.out.println("cid:"+ cloudlet.getCloudletId());
        }

        setCloudletReceivedList(clist);
    }
}
