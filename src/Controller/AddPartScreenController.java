package Controller;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class contains control logic for the Add Part Screen, which is used to create In House and Outsourced parts.
 */
public class AddPartScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRB;
    @FXML
    private RadioButton outsourcedRB;
    @FXML
    private TextArea addPartIDTextArea;
    @FXML
    private Button addPartCancelButton;
    @FXML
    private Label changeLabel;
    @FXML
    private TextField addPartInvTF;
    @FXML
    private TextField changeLabelTF;
    @FXML
    private TextField addPartMaxTF;
    @FXML
    private TextField addPartMinTF;
    @FXML
    private TextField addPartNameTF;
    @FXML
    private TextField addPartPriceTF;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private ToggleGroup toggleGroup;

    /** When user clicks the cancel button, Add Part Screen closes and the Main Screen opens.
     * @param event Clicking cancel button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void cancelAction (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** When user selects the In House radio button, the bottom label text is set to "Machine ID".
     * @param actionEvent Selecting the In House radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeLabel.setText("Machine ID");
    }

    /** When user selects the Outsourced radio button, the bottom label text is set to "Company Name".
     * @param actionEvent Selecting the Outsourced radio button
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeLabel.setText("Company Name");
    }

    /** Saves user input from the Add Part Screen, generates an InHouse or Outsourced object from user input, and
     * adds the new object to the inventory.
     * @param event Clicking the save button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void savePart (ActionEvent event) throws IOException {
        String error = "";
        try {
            int id = Inventory.generateUniquePartID();
            String name = addPartNameTF.getText();
            String changeLabelString = changeLabelTF.getText();
            // Converts inventory, price, maximum, and minimum string variables to number data types
            error = "Inventory";
            int stock = Integer.parseInt(addPartInvTF.getText());
            error = "Price";
            Double price = Double.parseDouble(addPartPriceTF.getText());
            error = "Maximum";
            int max = Integer.parseInt(addPartMaxTF.getText());
            error = "Minimum";
            int min = Integer.parseInt(addPartMinTF.getText());

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

            //Input validation, displays error message if inventory is not between minimum and maximum
            if (stock < min || stock > max) {
                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setContentText("Inventory must be between minimum and maximum");
                alert.showAndWait();
                return;
            }

            // If the In House radio button is selected, a new InHouse object is created
            if (inHouseRB.isSelected()) {
                try {
                    int machineID = Integer.parseInt(changeLabelString);
                    InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(inHousePart);
                    // Input validation, displays error message if Machine ID isn't an integer
                } catch (NumberFormatException numberFormatException) {
                    Alert alert = new Alert((Alert.AlertType.ERROR));
                    alert.setContentText("Machine ID must be a number");
                    alert.showAndWait();
                    return;
                }
            }

            // If the Outsourced radio button is selected, a new Outsourced object is created
            if (outsourcedRB.isSelected()) {
                // Input validation, displays error message if Company Name field is blank
                if (changeLabelString.isBlank()) {
                    Alert alert = new Alert((Alert.AlertType.ERROR));
                    alert.setContentText("Company Name field cannot be empty");
                    alert.showAndWait();
                    return;
                }
                Outsourced outsourcedPart = new Outsourced(id, name, price, stock, min, max, changeLabelString);
                Inventory.addPart(outsourcedPart);
            }

            // When save button is clicked, the application returns to the Main Screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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

    /** Initializes the AddPartScreenController.
     * @param url The location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle The resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}