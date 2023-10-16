package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is used to maintain Part and Product inventories. It holds lists of Part objects and Product objects.
 */
public class Inventory {
    // Instance variable to hold a Part's ID value
    private static int uniquePartID = 1;
    // Instance variable to hold a Product's ID value
    private static int uniqueProductID = 1;
    // Instance variable that holds an ObservableList of Part objects
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    // Instance variable that holds an ObservableList of Product objects
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Generates an integer Part ID, incremented by 1 from the previous value.
     * @return integer part ID
     */
    public static int generateUniquePartID() {
        return uniquePartID++;
    }

    /** Generates an integer Product ID, incremented by 1 from the previous value.
     * @return integer product ID
     */
    public static int generateUniqueProductID() {
        return uniqueProductID++;
    }

    /** Adds a new Part object to the list allParts.
     * @param newPart Part object to be added to the list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a new Product object to the list allProducts.
     * @param newProduct Product object to be added to the list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Searches the allParts list for a specific Part using its ID.
     * @param partID integer part ID to be searched for
     * @return Part object matching the search ID
     */
    public static Part lookupPart(int partID){
        Part lookupPart = null;
        // Iterates through the allParts list
        for (Part part : allParts) {
            // If the current part ID matches the search ID, the Part is saved and returned
            if (part.getId() == partID) {
                lookupPart = part;
            }
        }
        return lookupPart;
    }

    /** Searches the allProducts list for a specific Product using its ID.
     * @param productID integer product ID to be searched for
     * @return Product object matching the search ID
     */
    public static Product lookupProduct(int productID){
        Product lookupProduct = null;
        // Iterates through the allProducts list
        for (Product product : allProducts) {
            // If the current product ID matches the search ID, the Product is saved and returned
            if (product.getId() == productID) {
                lookupProduct = product;
            }
        }
        return lookupProduct;
    }

    /** Searches the allParts list for a specific Part using its name.
     * @param partName string part name to be searched for
     * @return list of Part objects matching the search name
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> lookupPart = FXCollections.observableArrayList();
        // Iterates through the allParts list
        for (Part part : allParts) {
            // If the current part name matches the search name, the Part is saved to a list and the list is returned
            if (part.getName().equals(partName)) {
                lookupPart.add(part);
            }
        }
        return lookupPart;
    }

    /** Searches the allProducts list for a specific Product using its name.
     * @param productName string product name to be searched for
     * @return list of Product objects matching the search name
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> lookupProduct = FXCollections.observableArrayList();
        // Iterates through the allProducts list
        for (Product product : allProducts) {
            // If the current product name matches the search name, the Product is saved to a list and the list is returned
            if (product.getName().equals(productName)) {
                lookupProduct.add(product);
            }
        }
        return lookupProduct;
    }

    /** Replaces a Part in the allParts list at the specified index with the Part object given as a parameter.
     * @param index integer index of the Part that needs to be updated
     * @param selectedPart new Part object that replaces the old Part at the given index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Replaces a product in the allProducts list at the specified index with the Product object given as a parameter.
     * @param index integer index of the Product that needs to be updated
     * @param newProduct new Product object that replaces the old Product at the given index
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** Removes the Part object given as a parameter from the allParts list.
     * @param selectedPart Part object to be removed
     * @return true if Part is successfully removed, or false if not
     */
    public static boolean deletePart(Part selectedPart){
        // If the allParts list contains the selected Part, the Part is removed from the list and returns true
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        // If the allParts list does not contain the Part to be deleted, it returns false
        return false;
    }

    /** Removes the Product object given as a parameter from the allProducts list.
     * @param selectedProduct The Product to be removed
     * @return true if Product is successfully removed, or false if not
     */
    public static boolean deleteProduct(Product selectedProduct){
        // If the allProducts list contains the selected product, the Product is removed from the list and returns true
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        // If the allProducts list does not contain the Product to be deleted, it returns false
        return false;
    }

    /** Returns a list of all the Part objects in an inventory.
     * @return the allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Returns a list of all the Product objects in an inventory.
     * @return the allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
