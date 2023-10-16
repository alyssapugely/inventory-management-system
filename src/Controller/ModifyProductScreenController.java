package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

/** This class contains control logic for the Modify Product Screen, which is used to modify the characteristics of
 * existing Product objects and add associated parts.
 */

public class ModifyProductScreenController extends MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    // Global variable to hold index of selected product
    int selectedProductIndex = 0;

    // Global variable to hold a temporary list of parts associated with the product
    ObservableList<Part> tempAssocPartsList;

    @FXML
    private TableColumn<Part, Integer> assocPartIDTC;
    @FXML
    private TableColumn<Part, Integer> assocPartInvTC;
    @FXML
    private TableColumn<Part, String> assocPartNameTC;
    @FXML
    private TableColumn<Part, Double> assocPartPriceTC;
    @FXML
    private TableView<Part> assocPartsTableView;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea modifyProductIDTextArea;
    @FXML
    private TextField modifyProductInvTF;
    @FXML
    private TextField modifyProductMaxTF;
    @FXML
    private TextField modifyProductMinTF;
    @FXML
    private TextField modifyProductNameTF;
    @FXML
    private TextField modifyProductPriceTF;
    @FXML
    private Button partAddButton;
    @FXML
    private TableColumn<Part, Integer> partIDTC;
    @FXML
    private TableColumn<Part, Integer> partInvTC;
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
    @FXML
    private Button saveButton;

    /** Calls either the searchPartsById or searchPartsByName methods, depending on data type of user search input.
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

    /** If a part is selected in the Parts TableView, the selected part is added to a temporary associated parts list
     * and displayed in the Associated Parts TableView. If no part is selected, an error message is displayed.
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

    /** Populates text fields with data corresponding to the product selected in the Main Screen Controller.
     * @param selectedProduct The product the user selects to modify in the Main Screen Controller
     */
    public void sendSelectedProduct(Product selectedProduct) {
        // Gets index of selected product and saves it to selectedProductIndex variable
        selectedProductIndex = Inventory.getAllProducts().indexOf(selectedProduct);
        // Gets all associated parts of selected product and saves it to tempAssocPartsList
        tempAssocPartsList = selectedProduct.getAllAssociatedParts();
        // Variables are converted to the appropriate data type and then populated in the remaining text fields
        modifyProductIDTextArea.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameTF.setText(selectedProduct.getName());
        modifyProductInvTF.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxTF.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTF.setText(String.valueOf(selectedProduct.getMin()));
        // Associated Parts TableView is populated with the associated parts of the selected product
        assocPartsTableView.setItems(tempAssocPartsList);
    }

    /** Closes the Modify Product Screen and opens the Main Screen when the user clicks the Cancel button.
     * @param event Clicking the Cancel button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void cancelAction (ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Saves user input from the Modify Product Screen and updates the product to reflect new input.
     * @param event Clicking the Save button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        String error = "";
        try {
            int id = Integer.parseInt(modifyProductIDTextArea.getText());
            String name = modifyProductNameTF.getText();
            // Converts inventory, price, maximum, and minimum string variables to number data types
            error = "Inventory";
            int stock = Integer.parseInt(modifyProductInvTF.getText());
            error = "Price";
            Double price = Double.parseDouble(modifyProductPriceTF.getText());
            error = "Maximum";
            int max = Integer.parseInt(modifyProductMaxTF.getText());
            error = "Minimum";
            int min = Integer.parseInt(modifyProductMinTF.getText());

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
            Inventory.updateProduct(selectedProductIndex, product);
            // All associated parts from the temporary list are added to the permanent list
            for (Part part : tempAssocPartsList) {
                product.addAssociatedPart(part);
            }

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

    /** Initializes the ModifyProductScreenController and populates the Part and Associated Part TableViews.
     * @param url The location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle The resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populates the Parts TableView
        partsTableView.setItems(Inventory.getAllParts());
        partIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Populates the Associated Part TableView
        assocPartIDTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvTC.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
