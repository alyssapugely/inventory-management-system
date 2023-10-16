package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** This class contains methods that start the application. This application is an Inventory Management System that is
 * used to temporarily store information about the parts and products in an inventory.
 *
 * LOCATION OF JAVADOCS FOLDER: Inside this project under the src folder
 */
public class main extends Application {

    /** Creates the primary FXML stage and loads the Main Screen.
     * @param stage The primary stage
     * @throws IOException Throws input/output exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/View/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the entry point to the program. InHouse, Outsourced, and Product objects can also be created within the
     * main method.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Creates test InHouse objects
        InHouse p1 = new InHouse(Inventory.generateUniquePartID(), "Computer Screen", 200.00, 17, 1, 50, 1234);
        Inventory.addPart(p1);

        InHouse p2 = new InHouse(Inventory.generateUniquePartID(), "Mouse", 50.00, 34, 1, 70, 2234);
        Inventory.addPart(p2);

        // Creates test Outsourced objects
        Outsourced p3 = new Outsourced(Inventory.generateUniquePartID(), "Hard Drive", 100.00, 49, 1, 100, "SanDisk");
        Inventory.addPart(p3);

        //Creates test Product objects
        Product pr1 = new Product(Inventory.generateUniqueProductID(), "Dell", 1300.00, 23, 1, 200);
        Inventory.addProduct(pr1);

        Product pr2 = new Product(Inventory.generateUniqueProductID(), "Mac", 1500.00, 40, 1, 300);
        Inventory.addProduct(pr2);

        Product pr3 = new Product(Inventory.generateUniqueProductID(), "Lenovo", 850.00, 37, 1, 200);
        Inventory.addProduct(pr3);

        // Launches the application
        launch(args);
    }
}