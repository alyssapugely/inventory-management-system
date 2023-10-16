package Controller;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Main Screen, which is used to add, modify, or delete parts and products in
 * the inventory. It also contains a feature to search by part/product ID or name.
 */
public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button addPartButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private TableColumn<Part, Integer> partIDTC;
    @FXML
    private TableColumn<Part, Integer> partInventoryTC;
    @FXML
    private TableColumn<Part, String> partNameTC;
    @FXML
    private TableColumn<Part, Double> partPriceTC;
    @FXML
    private TextField partSearchTF;
    @FXML
    public TableView<Part> partsTableView;
    @FXML
    private TableColumn<Product, Integer> productIDTC;
    @FXML
    private TableColumn<Product, Integer> productInventoryTC;
    @FXML
    private TableColumn<Product, String> productNameTC;
    @FXML
    private TableColumn<Product, Double> productPriceTC;
    @FXML
    private TextField productSearchTF;
    @FXML
    private TableView<Product> productsTableView;

    /** If the user selects a part from the parts table and clicks the Delete button, an alert appears confirming part
     * deletion. If the Delete button is clicked when there is no part selected, an error message is displayed.
     * @param event Clicking the Delete button under the Part table
     */
    @FXML
    void deletePart (ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        // If a part is selected by user, displays alert confirming part deletion
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> userSelection = alert.showAndWait();
            if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                partsTableView.setItems(Inventory.getAllParts());
            }
            // If there is no part selected and button is clicked, displays alert informing user that part must be selected
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part must be selected to delete");
            alert.show();
        }
    }

    /** If the user selects a product from the products table and clicks the Delete button, an alert appears confirming
     * product deletion. If the Delete button is clicked when there is no product selected, an error message is displayed.
     * @param event Clicking the Delete button under the Products table
     */
    @FXML
    void deleteProduct (ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        // If a product is selected by user, displays alert confirming product deletion
        if (selectedProduct != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> userSelection = confirmation.showAndWait();
            // If user confirms product deletion, checks that there are no associated parts
            if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
                // Displays error message if product has associated parts
                if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Associated parts must be removed before product can be deleted");
                    error.show();
                    return;
                }
                // Product is deleted and table view is updated if there are no associated parts
                Inventory.deleteProduct(selectedProduct);
                productsTableView.setItems(Inventory.getAllProducts());
            }
            // If there is no product selected and button is clicked, displays alert informing user that product must be selected
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must be selected to delete");
            alert.show();
        }
    }

    /** Filters parts by results matching user input (partial or full name search) and displays the matching parts in the parts table.
     * @param searchName User input entered into partSearch text field
     * @return Returns a list of matching parts
     */
    public ObservableList<Part> searchPartsByName(String searchName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        // Iterates through the list of all parts in the inventory
        for (Part part : allParts) {
            String lowerCaseSearch = part.getName().toLowerCase();
            // Returns both upper and lower case matches to allow for more thorough search results
            if (part.getName().contains(searchName) || lowerCaseSearch.contains(searchName)) {
                matchingParts.add(part);
            }
        }
        // If no parts are found matching search input, an error message is displayed
        if (matchingParts.isEmpty()) {
            partNotFoundError();
        }
        return matchingParts;
    }

    /** Displays an error message if no parts are found matching search input.
     */
    public void partNotFoundError() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Part not found");
        alert.setContentText("No part was found matching your search");
        alert.showAndWait();
    }

    /** Filters parts by results matching user input (part ID search) and displays the matching part in the parts table.
     * @param searchID User input entered into partSearch text field
     * @return Returns a list of matching parts
     */
    public ObservableList<Part> searchPartsByID(int searchID) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        // Iterates through the list of all parts in the inventory
        for (Part part : allParts) {
            // Compares current part ID to the user input search ID
            if (part.getId() == searchID) {
                matchingParts.add(part);
            }
        }
        // If no parts are found matching search input, an error message is displayed
        if (matchingParts.isEmpty()) {
            partNotFoundError();
        }
        return matchingParts;
    }

    /** Calls either the above searchPartsById or searchPartsByName methods, depending on data type of user search input.
     * @param event User input entered into partSearch text field
     */
    @FXML
    public void searchParts (ActionEvent event) {
        // Saves user search input to string variable
        String partSearchInput = partSearchTF.getText();
        // If user input is an integer, uses the searchPartsByID method to search for existing parts with matching ID
        if (isInteger(partSearchInput)) {
            ObservableList <Part> searchPartsById = searchPartsByID(Integer.parseInt(partSearchInput));
            partsTableView.setItems(searchPartsById);
            partSearchTF.setText("");
        } else {
            /* If user input is a string, uses the searchPartsByName method to search for existing parts containing
            a matching string */
            ObservableList<Part> searchPartsByName = searchPartsByName(partSearchInput);
            partsTableView.setItems(searchPartsByName);
            partSearchTF.setText("");
        }
    }

    /** Displays an error message if no products are found matching the user search input in the productSearch text field.
     */
    public void productNotFoundError() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Product not found");
        alert.setContentText("No product was found matching your search");
        alert.showAndWait();
    }

    /** Filters products by results matching user input (partial or full name search) and displays the matching products
     * in the products table.
     * @param searchName User input entered into productSearch text field
     * @return Returns a list of matching products
     */
    private ObservableList<Product> searchProductsByName(String searchName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        // Iterates through the list of all products in the inventory
        for (Product product : allProducts) {
            String lowerCaseSearch = product.getName().toLowerCase();
            // Returns both upper and lower case matches to allow for more thorough search results
            if (product.getName().contains(searchName) || lowerCaseSearch.contains(searchName)) {
                matchingProducts.add(product);
            }
        }
        // If no products are found matching search input, an error message is displayed
        if (matchingProducts.isEmpty()) {
            productNotFoundError();
        }
        return matchingProducts;
    }

    /** Filters products by results matching user input (product ID search) and displays the matching product in the products table.
     * @param searchID User input entered into productSearch text field
     * @return Returns a list of matching products
     */
    private ObservableList<Product> searchProductsByID(int searchID) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        // Iterates through the list of all products in the inventory
        for (Product product : allProducts) {
            if (product.getId() == searchID) {
                matchingProducts.add(product);
            }
        }
        // If no products are found matching search input, an error message is displayed
        if (matchingProducts.isEmpty()) {
            productNotFoundError();
        }
        return matchingProducts;
    }

    /** Calls either the above searchProductsById or searchProductsByName methods, depending on date type of user search input.
     * @param event User input entered into productSearch text field
     */
    @FXML
    public void searchProducts (ActionEvent event) {
        // Saves user search input to string variable
        String productSearchInput = productSearchTF.getText();
        // If user input is an integer, uses the searchProductsByID method to search for existing products with matching ID
        if (isInteger(productSearchInput)) {
            ObservableList <Product> searchProductsById = searchProductsByID(Integer.parseInt(productSearchInput));
            productsTableView.setItems(searchProductsById);
            productSearchTF.setText("");
        } else {
            /* If user input is a string, uses the searchProductsByName method to search for existing products containing
            a matching string */
            ObservableList<Product> searchProductsByName = searchProductsByName(productSearchInput);
            productsTableView.setItems(searchProductsByName);
            productSearchTF.setText("");
        }
    }

    /** Checks if user input in search bar is a string or integer.
     * @param userInput User input entered into partSearch or productSearch text fields
     * @return Returns true if user input is an integer, or false if it is not
     */
    public static boolean isInteger(String userInput) {
        // If userInput has no value assigned to it, returns false
        if (userInput == null) {
            return false;
        }
        try {
            // If userInput is not an integer, throws NumberFormatException and returns false
            int integerInput = Integer.parseInt(userInput);
        } catch (NumberFormatException exception) {
            return false;
        }
        // If userInput is an integer, returns true
        return true;
    }

    /** Closes the application when the Exit button is clicked.
     * @param event Clicking the Exit button
     */
    @FXML
    void closeApplication (ActionEvent event) {
        System.exit(0);
    }

    /** Opens the Add Part Screen when the Add button under the Parts table is clicked.
     * @param event Clicking the Add button under the Parts table
     * @throws IOException Throws input/output exception
     */
    @FXML
    void openAddPartScreen (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Opens the Add Product Screen when the Add button under the Products table is clicked.
     * @param event Clicking the Add button under the Products table
     * @throws IOException Throws input/output exception
     */
    @FXML
    void openAddProductScreen (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** When a part is selected and the Modify Part button is clicked, the Modify Part Screen opens with text fields
     * populated with data corresponding to the selected part.
     * @param event Clicking the Modify button under the Parts table
     * @throws IOException Throws input/output exception
     */
    @FXML
    void openModifyPartScreen (ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        // If a part is selected by user, the Modify Part Screen opens
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPartScreen.fxml"));
            loader.load();

            ModifyPartScreenController modifyPartScreenController = loader.getController();
            modifyPartScreenController.sendSelectedPart(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            // If a part is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part must be selected to modify");
            alert.show();
        }
    }

    /** When a product is selected and the Modify Product button is clicked, the Modify Product Screen opens with text
     * fields populated with data corresponding to the selected product.
     * @param event Clicking the Modify button under the Products table
     * @throws IOException Throws input/output exception
     */
    @FXML
    void openModifyProductScreen (ActionEvent event) throws IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        // If a product is selected by user, the Modify Product Screen opens
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProductScreen.fxml"));
            loader.load();

            ModifyProductScreenController modifyProductScreenController = loader.getController();
            modifyProductScreenController.sendSelectedProduct(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            // If a product is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must be selected to modify");
            alert.show();
        }
    }

    /** Initializes the MainScreenController and populates the Parts and Products TableViews.
     * @param url The location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle The resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populates the Parts TableView and associated TableColumns
        partsTableView.setItems(Inventory.getAllParts());
        partIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Populates the Products TableView and associated TableColumns
        productsTableView.setItems(Inventory.getAllProducts());
        productIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
