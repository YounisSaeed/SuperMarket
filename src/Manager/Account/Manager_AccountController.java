
package Manager.Account;

import Manager.Main.HomeController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class Manager_AccountController implements Initializable {
    HomeController x = new HomeController();
    @FXML
    private AnchorPane Account;
    @FXML
    private Label Code;
    @FXML
    private Label RPassword;
    @FXML
    private Label Password;
    @FXML
    private TextField T_Code;
    @FXML
    private PasswordField T_Password;
    @FXML
    private PasswordField T_RPassword;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Edit_Account(ActionEvent event) {
    }

    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(Account,"/Manager/Main/Home.fxml");
    }
    
}
