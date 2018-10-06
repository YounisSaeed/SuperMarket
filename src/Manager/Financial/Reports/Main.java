
package Manager.Financial.Reports;

import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Financial_Reports.fxml"));
       
        Scene scene = new Scene(root);
                Image icon = new Image("/icons/supermarket.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
         stage.setOnCloseRequest( event -> 
        {
            ButtonType yes = new ButtonType("نعم", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("لا", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes,no);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("طلب تأكيد");
            alert.setHeaderText("انتبه");
            alert.setContentText("هل تريد الخروج بالفعل؟");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== yes)
                stage.close();
            else
                event.consume();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
