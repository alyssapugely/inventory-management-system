package Model;

/** This class inherits from the Part class, and is used to create InHouse Part objects.
 */
public class InHouse extends Part {
    // Integer instance variable for InHouse object's machine ID
    private int machineID;

    /** The InHouse object constructor defines the necessary parameters to create an InHouse object.
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min stock minimum
     * @param max stock maximum
     * @param machineID part machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** Sets the value of the InHouse object's machine ID to the integer passed as a parameter.
     * @param machineID integer value of the machine ID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /** Returns the integer value of the InHouse object's machine ID.
     * @return integer value of the machine ID
     */
    public int getMachineID() {
        return this.machineID;
    }
}
