/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employees.account;

import Classes.Alerts;
import database.DataHelper;
import database.DatabaseHandler;
import employees.main.EmployeesController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class AccountController implements Initializable {
    public static String EmpCode="";
    public static String EmpName="";
    
    
    EmployeesController x = new EmployeesController();
    @FXML
    private AnchorPane loadPane;
    @FXML
    private TextField code_employee;

    /**
     * Initializes the controller class.
     */
    DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
    }    

    @FXML
    private void loadMainOfAccount(ActionEvent event) {
        //loadWindow("/employees/main/employees.fxml");
        x.loadwindow(loadPane,"/employees/main/employees.fxml");
    }

/*
    private void loadAccount(ActionEvent event) {
       // loadWindow("/employees/account/accepted/accountaccept.fxml");
        x.loadwindow(loadPane,"/employees/account/accepted/accountaccept.fxml");
    }
  */
    /*
    void loadWindow()
=======

//    private void loadAccount(ActionEvent event) {
//       // loadWindow("/employees/account/accepted/accountaccept.fxml");
//        x.loadwindow(loadPane,"/employees/account/accepted/accountaccept.fxml");
//    }
    
    /*void loadWindow(String loc)
>>>>>>> ddddd22311a4a4397c781f601ff993f363fd9946
    {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/employees/account/accepted/accountaccept.fxml"));
            loadPane.getChildren().setAll(pane);
           
        } catch (IOException ex) {
            Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    @FXML
    private void confirm(ActionEvent event){
        
        String id =  code_employee.getText();
        if (DataHelper.isEmployeeisEXits(id))
        {
            EmpCode=code_employee.getText();
            EmpName=DataHelper.getEmpName(EmpCode);
            System.out.println(EmpName);
            //Alerts.showInfoAlert("كود صحيح");
             x.loadwindow(loadPane,"/employees/account/accepted/acoountaccept.fxml");
            
        }
        else{
            Alerts.showAlert("كود خطأ",3);
        }    
        
    }
}
