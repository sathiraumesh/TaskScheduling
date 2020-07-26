package heuristics.algorithms;

import org.cloudbus.cloudsim.DatacenterBroker;

public abstract class BaseBroker extends DatacenterBroker {


    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */
    public BaseBroker(String name) throws Exception {
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


     public abstract void scheduleTaskstoVms();


}
