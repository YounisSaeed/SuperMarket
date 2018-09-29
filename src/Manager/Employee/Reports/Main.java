
package Manager.Employee.Reports;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =FXMLLoader.load(getClass().getResource("Employee_Reports.fxml"));
       
        Scene scene = new Scene(root);
                Image icon = new Image("/icons/supermarket.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
