package Controller;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Add Product Screen, which is used to create new products and add their associated parts.
 */
public class AddProductScreenController extends MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    // Holds a temporary list of parts associated with the product
    private ObservableList<Part> tempAssocPartsList = FXCollections.observableArrayList();

    @FXML
    private Button addPartButton;
    @FXML
    private TextArea addProductIDTextArea;
    @FXML
    private TextField addProductInvTF;
    @FXML
    private TextField addProductMaxTF;
    @FXML
    private TextField addProductMinTF;
    @FXML
    private TextField addProductNameTF;
    @FXML
    private TextField addProductPriceTF;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private TableColumn<Part, Integer> assocPartIDTC;
    @FXML
    private TableColumn<Part, Integer> assocPartInventoryTC;
    @FXML
    private TableColumn<Part, String> assocPartNameTC;
    @FXML
    private TableColumn<Part, Double> assocPartPriceTC;
    @FXML
    private TableView<Part> assocPartsTableView;
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
    private TableView<Part> partsTableView;
    @FXML
    private Button removeAssocPartButton;

    /** If a part is selected in the Parts TableView, it is added to the Associated Parts TableView. If no part is
     * selected, an error message is displayed.
     * @param event Clicking the Add button
     */
    @FXML
    void addAssociatedPart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        /* If a part is selected by user, part is added to temporary associated parts list and displayed in the
        associated parts table */
        if (selectedPart != null) {
            tempAssocPartsList.add(selectedPart);
            assocPartsTableView.setItems(tempAssocPartsList);
        } else {
            // If a part is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part must be selected to add to Associated Parts");
            alert.show();
        }
    }

    /** If a part is selected in the Associated Parts TableView, an alert is displayed confirming deletion, and the part
     * is removed. If no part is selected, an error message is displayed.
     * @param event Clicking the Remove Associated Part button
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {
        Part selectedPart = assocPartsTableView.getSelectionModel().getSelectedItem();
        // If an associated part is selected by user, an alert is displayed confirming deletion
        if (selectedPart != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
            Optional<ButtonType> userSelection = confirmation.showAndWait();
            /* If user confirms part deletion, part is removed from temporary associated parts list and associated parts
            table view is updated */
            if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
                tempAssocPartsList.remove(selectedPart);
                assocPartsTableView.setItems(tempAssocPartsList);
            }
        } else {
            // If an associated part is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part must be selected to remove Associated Part");
            alert.show();
        }
    }

    /** Closes the Add Product Screen and opens the Main Screen.
     * @param event Clicking the Cancel button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void cancelAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Saves user input from the Add Product Screen, generates a new product object from user input, and adds the new
     * product to the inventory.
     * @param event Clicking the Save button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        String error = "";
        try {
            int id = Inventory.generateUniqueProductID();
            String name = addProductNameTF.getText();
            // Converts inventory, price, maximum, and minimum string variables to number data types
            error = "Inventory";
            int stock = Integer.parseInt(addProductInvTF.getText());
            error = "Price";
            Double price = Double.parseDouble(addProductPriceTF.getText());
            error = "Maximum";
            int max = Integer.parseInt(addProductMaxTF.getText());
            error = "Minimum";
            int min = Integer.parseInt(addProductMinTF.getText());

            // Input validation, displays error message if name field is blank
            if (name.isBlank()) {
                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setContentText("Name field cannot be empty");
                alert.showAndWait();
                return;
            }

            // Input validation, displays error message if minimum is not less than maximum
            if (min >= max) {
                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setContentText("Minimum must be less than maximum");
                alert.showAndWait();
                return;
            }

            // Input validation, displays error message if inventory is not between minimum and maximum
            if (stock < min || stock > max) {
                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setContentText("Inventory must be between minimum and maximum");
                alert.showAndWait();
                return;
            }

            // If all input is valid, a new product object is created
            Product product = new Product(id, name, price, stock, min, max);
            // All associated parts from the temporary list are added to the permanent list
            for (Part part : tempAssocPartsList) {
                product.addAssociatedPart(part);
            }
            // The new product is added to the inventory
            Inventory.addProduct(product);

            // When save button is clicked, the application returns to the Main Screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            // Input validation, displays error message if inventory, price, maximum, and minimum are not numbers
        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setContentText(error + " must be a number");
            alert.showAndWait();
            return;
        }
    }

    /** Displays parts in Parts TableView matching search criteria input by user (either a partial or full name search,
     * or an ID search).
     * @param event User input entered into partSearch text field
     */
    @FXML
    public void searchParts(ActionEvent event) {
        // Saves user search input to string variable
        String partSearchInput = partSearchTF.getText();
        // If user input is an integer, uses the searchPartsByID method to search for existing parts with matching ID
        if (isInteger(partSearchInput)) {
            ObservableList<Part> searchPartsById = searchPartsByID(Integer.parseInt(partSearchInput));
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

    /** Initializes the AddProductScreenController and populates the Part and Associated Part TableViews.
     * @param url The location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle The resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populates the Part TableView
        partsTableView.setItems(Inventory.getAllParts());
        partIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Populates the Associated Part TableView
        assocPartIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
