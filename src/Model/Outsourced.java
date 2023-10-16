package Model;

/** This class inherits from the Part class, and is used to create Outsourced Part objects.
 */
public class Outsourced extends Part {
    // String instance variable for Outsourced object's company name
    private String companyName;

    /** The Outsourced object constructor defines the necessary parameters to create an Outsourced object.
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min stock minimum
     * @param max stock maximum
     * @param companyName part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Sets the value of the Outsourced object's company name to the string passed as a parameter.
     * @param companyName string value of the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Returns the string value of the Outsourced object's company name.
     * @return string value of the company name
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
