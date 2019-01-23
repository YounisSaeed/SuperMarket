
package employees.account.accepted;

import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Serial_dinamic.Serial_B;
import Serial_dinamic.Serial_S;

public class MainAccountAccept extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("acoountaccept.fxml"));
        
        Scene scene = new Scene(root);
        Image icon = new Image("/icons/supermarket.png");
        stage.getIcons().add(icon);
        stage.setTitle("حسابى");
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
            
  if(result.get()== yes){
                if(Serial_B.stat)
                    Serial_B.timer.cancel();
                if(Serial_S.stat)
                    Serial_S.timer.cancel();
                stage.close();}
            else
                event.consume();
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}