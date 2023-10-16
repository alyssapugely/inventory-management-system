package Model;

/** This abstract class serves as a model for InHouse and Outsourced classes to inherit from. A Part object cannot
 * be instantiated on its own, it must be implemented as an InHouse or Outsourced object.
 */
public abstract class Part {
    // Instance variable for Part's id
    private int id;
    // Instance variable for Part's name
    private String name;
    // Instance variable for Part's price
    private double price;
    // Instance variable for Part's stock
    private int stock;
    // Instance variable for stock's minimum
    private int min;
    // Instance variable for stock's maximum
    private int max;

    /** The Part constructor defines the necessary parameters to create a Part, implemented as either an InHouse or
     * Outsourced object.
     * @param id part ID
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min stock minimum
     * @param max stock maximum
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Returns the integer value of the Part object's ID.
     * @return integer value of the Part's ID
     */
    public int getId() {
        return id;
    }

    /** Sets the value of the Part object's id to the integer passed as a parameter.
     * @param id integer value of the Part's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the string value of the Part object's name.
     * @return string value of the Part's name
     */
    public String getName() {
        return name;
    }

    /** Sets the value of the Part object's name to the string passed as a parameter.
     * @param name string value of the Part's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the double value of the Part object's price.
     * @return double value of the Part's price
     */
    public double getPrice() {
        return price;
    }

    /** Sets the value of the Part object's price to the double passed as a parameter.
     * @param price double value of the Part's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Returns the integer value of the Part object's stock.
     * @return integer value of the Part's stock
     */
    public int getStock() {
        return stock;
    }

    /** Sets the value of the Part object's stock to the integer passed as a parameter.
     * @param stock integer value of the Part's stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Returns the integer value of the stock's minimum.
     * @return integer value of the stock's minimum
     */
    public int getMin() {
        return min;
    }

    /** Sets the value of the stock's minimum to the integer passed as a parameter.
     * @param min integer value of the stock's minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Returns the integer value of the stock's maximum.
     * @return integer value of the stock's maximum
     */
    public int getMax() {
        return max;
    }

    /** Sets the value of the stock's maximum to the integer passed as a parameter.
     * @param max integer value of the stock's maximum
     */
    public void setMax(int max) {
        this.max = max;
    }
}