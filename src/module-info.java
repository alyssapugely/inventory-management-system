module com.example.inventory_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens Main to javafx.fxml;
    exports Main;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}