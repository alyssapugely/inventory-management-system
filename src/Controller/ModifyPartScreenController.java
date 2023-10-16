package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class contains control logic for the Modify Part Screen, which is used to modify the characteristics of
 * existing Part objects.
 */

public class ModifyPartScreenController extends MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    // Global variable to hold index of selected part
    int selectedPartIndex = 0;

    @FXML
    private Label changeLabel;
    @FXML
    private RadioButton inHouseRB;
    @FXML
    private Button modifyPartCancelButton;
    @FXML
    private TextArea modifyPartIDTextArea;
    @FXML
    private TextField modifyPartInvTF;
    @FXML
    private TextField changeLabelTF;
    @FXML
    private TextField modifyPartMaxTF;
    @FXML
    private TextField modifyPartMinTF;
    @FXML
    private TextField modifyPartNameTF;
    @FXML
    private TextField modifyPartPriceTF;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton outsourcedRB;

    /** Sets the bottom label text to "Machine ID" when the user selects the In House radio button.
     * @param actionEvent Selecting the In House radio button
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeLabel.setText("Machine ID");
    }

    /** Sets the bottom label text to "Company Name" when the user selects the Outsourced radio button.
     * @param actionEvent Selecting the Outsourced radio button
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeLabel.setText("Company Name");
    }

    /** Closes the Modify Part Screen and opens the Main Screen when the user clicks the Cancel button.
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

    /** Saves user input from the Modify Part Screen and updates the part to reflect new input.
     * @param event Clicking the Save button
     * @throws IOException Throws input/output exception
     */
    @FXML
    void savePart (ActionEvent event) throws IOException {
        String error = "";
        try {
            int id = Integer.parseInt(modifyPartIDTextArea.getText());
            String name = modifyPartNameTF.getText();
            String changeLabelString = changeLabelTF.getText();
            // Converts inventory, price, maximum, and minimum string variables to number data types
            error = "Inventory";
            int stock = Integer.parseInt(modifyPartInvTF.getText());
            error = "Price";
            Double price = Double.parseDouble(modifyPartPriceTF.getText());
            error = "Maximum";
            int max = Integer.parseInt(modifyPartMaxTF.getText());
            error = "Minimum";
            int min = Integer.parseInt(modifyPartMinTF.getText());

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
                    Inventory.updatePart(selectedPartIndex, inHousePart);
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
                Inventory.updatePart(selectedPartIndex, outsourcedPart);
            }
            // Input validation, displays error message if inventory, price, maximum, and minimum are not numbers
        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setContentText(error + " must be a number");
            alert.showAndWait();
            return;
        }
        // When save button is clicked, the application returns to the Main Screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Populates text fields with data corresponding to the part selected in the Main Screen Controller.
     * @param selectedPart The part the user selects to modify in the Main Screen Controller
     */
    public void sendSelectedPart(Part selectedPart) {
        // Gets index of selected part and saves it to selectedPartIndex variable
        selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);
        /* If the selected part is In House, the In House radio button is selected, the bottom label text is set to
        Machine ID, the Machine ID field is populated */
        if (selectedPart instanceof InHouse) {
            inHouseRB.setSelected(true);
            changeLabel.setText("Machine ID");
            changeLabelTF.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
            /* If the selected part is Outsourced, the Outsourced radio button is selected, the bottom label text is
            set to Company Name, and the Company Name field is populated */
        } else {
            outsourcedRB.setSelected(true);
            changeLabel.setText("Company Name");
            changeLabelTF.setText(((Outsourced) selectedPart).getCompanyName());
        }
        // Variables are converted to the appropriate data type and then populated in the remaining text fields
        modifyPartIDTextArea.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameTF.setText(selectedPart.getName());
        modifyPartInvTF.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceTF.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxTF.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinTF.setText(String.valueOf(selectedPart.getMin()));
    }

    /** Initializes the ModifyPartScreenController.
     * @param url The location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle The resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
