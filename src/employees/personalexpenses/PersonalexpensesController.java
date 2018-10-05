
package employees.personalexpenses;

import Classes.Alerts;
import Classes.Employee;
import Serial_dinamic.NewSerial;
import static Serial_dinamic.NewSerial.gettDate;
import database.*;
import employees.account.AccountController;
import employees.main.EmployeesController;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class PersonalexpensesController extends AccountController implements Initializable {
    EmployeesController x = new EmployeesController();
    @FXML
    private AnchorPane loadPane;
    @FXML
    private TableColumn<Employee, Double> t_value;
    @FXML
    private TableColumn<Employee, String> t_reason;
    @FXML
    private TextField value;
    @FXML
    private TextArea reason;
    DatabaseHandler databaseHandler;
    @FXML
    private TableView<Employee> personal_table;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
        DataHelper.loadpersonalExpensesData(personal_table,gettDate(),EmpCode);
        showTable();
    }
    private void showTable(){
        t_value.setCellValueFactory(new PropertyValueFactory<>("employeeExpensesCost"));
        t_reason.setCellValueFactory(new PropertyValueFactory<>("employeeExpensesReason"));
    }    

    @FXML
    private void loadMAinOfExpenses(ActionEvent event) {
        //loadWindow("/employees/main/employees.fxml");
        x.loadwindow(loadPane,"/employees/main/employees.fxml");
    }

    @FXML
    private void loadBack(ActionEvent event) {
        EmpCode="";
        EmpName="";
        x.loadwindow(loadPane,"/employees/account/accepted/acoountaccept.fxml");
        //x.loadwindow(loadPane,"/employees/account/accepted/accountaccept.fxml");
    }
    
  /* void loadWindow(String loc)
    {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(loc));
            loadPane.getChildren().setAll(pane);
           
        } catch (IOException ex) {
            Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    private void add(){
                if (!reason.getText().equals("") && !value.getText().equals("")){
                if (Double.parseDouble(value.getText())>0){
        
                Employee e = new Employee();
                Date today = Date.valueOf(getDate());
                e.setDate(today);
                e.setEmployeeExpensesCost(Double.parseDouble(value.getText()));
                e.setEmployeeExpensesReason(reason.getText());
                e.setEmployeeName(EmpName);
                e.setEmployeeId(EmpCode);
                boolean result = DataHelper.insertNewPersonalExpences(e);
                personal_table.getItems().add(e);
<<<<<<< HEAD
                if(result)
                    Alerts.showInfoAlert("تم اضافة المصاريف الشخصية");
                
=======
                if(result){
                    
                    Alert AT=new Alert(Alert.AlertType.INFORMATION);
                    AT.setHeaderText(null);
                    AT.setContentText("تم اضافة المصاريف الشخصية");
                    AT.showAndWait();
                    clear();
                    return;
                }
                }else {
                    Alerts.showInfoAlert("القيمة التي أدخلتها غير صحيحة");
                }
                }else {
                    Alerts.showInfoAlert("يرجى التأكد من ملئ جميع الحقول المطلوبة");
                }
>>>>>>> 5b11aec721fe918b9921d930156e72be6b685fa6
            }
    @FXML
    private void confirm(ActionEvent event) {
        this.add();
    }
    
     private void clear(){
      value.setText("");
      reason.setText("");
     }
     
   @FXML
    private void Key_pressed(KeyEvent event) {
            try{
        if(event.getCode().equals(KeyCode.CONTROL)){  // Save when Pressing Control
          this.add();
        }
       
    }catch(Exception e){}
    }  
    
    
    private String getDate(){
        java.util.Date today;
        SimpleDateFormat simpleDF;
        today = new java.util.Date();
        simpleDF = new SimpleDateFormat ("yyyy-MM-dd");
        return simpleDF.format(today);
    }
    }
