
package Manager.date;

import Classes.Alerts;
import Manager.Main.HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DataHelper;
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
import database.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateController implements Initializable {
    HomeController x = new HomeController (); // used for load main page
    @FXML
    private AnchorPane Product_Quantity;
    @FXML
    private Label D_Quantity;
    @FXML
    private JFXTextField D_TSearch;
    @FXML
    private Label LName;
    @FXML
    private JFXDatePicker D_Date;
    DatabaseHandler databaseHandler;
    @FXML
    private Label D_Quantity1;
    @FXML
    private Label D_EXP;
    @FXML
    private Label D_Quantity11;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
        DataHelper.checkDataBar(D_TSearch); // get barcode of all products
    }    

    @FXML
    private void AutoCompSearch(KeyEvent event) {
        searrch();
    }

    private void searrch(){
        DataHelper.fillSalesWithInfoOfProduct(D_TSearch.getText(),LName,D_EXP);// 
        // fill Search Field with barcodes of all products in market
        // pri : initialize price of item,packet,box of specific product you search about
    }
    

    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(Product_Quantity, "/Manager/Products/Manager_Products.fxml");
    
    }

    @FXML
    private void EditDate(ActionEvent event) {
        if (!D_TSearch.getText().equals("") && !D_Date.getValue().equals("")   ){
        String da1=D_Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean x=DataHelper.updateExpireDate(D_TSearch.getText(),da1);
        if(x){
            Alerts.showAlert("تم تعديل تاريخ الانتهاء",1);
            DataHelper.fillSalesWithInfoOfProduct(D_TSearch.getText(),LName,D_EXP);// 
            clear();
        }
        else
            Alerts.showAlert("لم يتم التعديل",3);
    }else 
         Alerts.showAlert("تأكد من ملئ جميع الحقول المطلوبة",3);  
        
    }

    @FXML
    private void MainPage(ActionEvent event) {
        
        x.loadwindow(Product_Quantity, "/Manager/Main/Home.fxml");
    }

    @FXML
    private void SearhButton(ActionEvent event) {
        searrch();
    }

    private void clear(){
       D_TSearch.setText("");
       LName.setText("");
       D_EXP.setText("");
       
       
       
        
    }
    
}
