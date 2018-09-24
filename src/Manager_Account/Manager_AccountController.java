/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager_Account;

import java.net.URL;
import java.util.ResourceBundle;
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

    @FXML
    private AnchorPane Account;
    @FXML
    private Label Code;
    @FXML
    private Label RPassword;
    @FXML
    private Label Password;
    @FXML
    private PasswordField T_Password;
    @FXML
    private PasswordField T_RPassword;
    @FXML
    private TextField T_Code;

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
    }
    
}
