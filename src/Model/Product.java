package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is used to create Product objects. These objects can contain lists of Part objects associated with the product.
 */
public class Product {
    // Instance variable containing a list of Part objects associated with the product
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    // Instance variable for Product object's machine ID
    private int id;
    // Instance variable for Product object's name
    private String name;
    // Instance variable for Product object's price
    private double price;
    // Instance variable for Product object's stock
    private int stock;
    // Instance variable for Product object's minimum
    private int min;
    // Instance variable for Product object's maximum
    private int max;

    /** The Product object constructor defines the necessary parameters to create a Product object.
     * @param id product ID
     * @param name product name
     * @param price product price
     * @param stock product stock
     * @param min stock minimum
     * @param max stock maximum
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Adds the Part object given as a parameter to the associatedParts list.
     * @param part Part object to be added to the associatedParts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Removes the Part object given as a parameter from the associatedParts list.
     * @param selectedAssociatedPart Part object to removed from the associatedParts list
     * @return true if the Part is successfully removed, otherwise returns false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        // If the associatedParts list contains selectedAssociatedPart, Part is removed from the list and returns true
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        // If the associatedParts list does not contain selectedAssociatedPart, returns false
        return false;
    }

    /** Returns a list of all the Part objects associated with a Product.
     * @return associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /** Returns the integer value of the Product object's ID.
     * @return integer value of the Product's ID
     */
    public int getId() {
        return id;
    }

    /** Sets the value of the Product object's id to the integer passed as a parameter.
     * @param id integer value of the Product's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the string value of the Product object's name.
     * @return string value of the Product's name
     */
    public String getName() {
        return name;
    }

    /** Sets the value of the Product object's name to the string passed as a parameter.
     * @param name string value of the Product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the double value of the Product object's price.
     * @return double value of the Product's price
     */
    public double getPrice() {
        return price;
    }

    /** Sets the value of the Product object's price to the double passed as a parameter.
     * @param price double value of the Product's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Returns the integer value of the Product object's stock.
     * @return integer value of the Product's stock
     */
    public int getStock() {
        return stock;
    }

    /** Sets the value of the Product object's stock to the integer passed as a parameter.
     * @param stock integer value of the Product's stock
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
