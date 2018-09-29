/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager.date;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class DateController implements Initializable {

    @FXML
    private AnchorPane Product_Quantity;
    @FXML
    private Label D_Quantity;
    @FXML
    private JFXTextField D_TSearch;
    @FXML
    private RadioButton R_item;
    @FXML
    private ToggleGroup choiceQuan;
    @FXML
    private RadioButton R_packet;
    @FXML
    private RadioButton R_box;
    @FXML
    private Label LName;
    @FXML
    private Label L_InP;
    @FXML
    private Label L_PnB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AutoCompSearch(KeyEvent event) {
    }

    @FXML
    private void AllowEditRadio(MouseEvent event) {
    }

    @FXML
    private void Back(ActionEvent event) {
    }
    
}
