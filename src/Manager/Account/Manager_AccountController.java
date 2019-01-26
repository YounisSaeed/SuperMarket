
package Manager.Account;

import Classes.Alerts;
import Manager.Main.HomeController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class Manager_AccountController implements Initializable {
    HomeController x = new HomeController(); // used for load windows

    @FXML
    private AnchorPane Account;
    @FXML
    private Label RPassword;
    @FXML
    private Label Password;
    @FXML
    private PasswordField T_Password;
    @FXML
    private PasswordField T_RPassword;

    /**
     * Initializes the controller class.
     */
    String pass="admin@55555";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    


    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(Account,"/Manager/Main/Home.fxml");
    }
    private boolean validation(){
        return T_Password.getText().equals(pass) && T_RPassword.getText().equals(pass);
    }

    @FXML
    private void Backup(ActionEvent event) {
        if(!T_Password.getText().equals("") && !T_RPassword.getText().equals("")){
            if(validation()){
                try {
                    Runtime.getRuntime().exec("xcopy D:\\walid\\Last2\\Supermarket_Management_System\\database D:\\walid\\Last2\\DB /s/h/e/k/f/c/y/r");
                    Alerts.showAlert("تم حفظ نسخة احتياطية من البرنامج",1);
                } catch (IOException ex) {
                    Logger.getLogger(Manager_AccountController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else Alerts.showAlert("كلمة السر غير صحيحة",3);
        }else Alerts.showAlert("لم يتم ادخال كلمة السر",3);
    }

    @FXML
    private void Restore(ActionEvent event) {
        if(!T_Password.getText().equals("") && !T_RPassword.getText().equals("")){
            if(validation()){
                try {
                    Runtime.getRuntime().exec("xcopy D:\\walid\\Last2\\DB D:\\walid\\Last2\\Supermarket_Management_System\\database /s/h/e/k/f/c/y/r");
                    Alerts.showAlert("تم استرجاع بيانات النظام الى اخر نسخة محفوظة",1);
                } catch (IOException ex) {
                    Logger.getLogger(Manager_AccountController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else Alerts.showAlert("كلمة السر غير صحيحة",3);
        }
    }
    
}
