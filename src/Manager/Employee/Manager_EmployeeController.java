package Manager.Employee;

import Classes.Additional;

import Classes.Alerts;
import Classes.Employee;
import Classes.Validations;
import Manager.Main.HomeController;
import database.DataHelper;
import database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class Manager_EmployeeController implements Initializable {
     HomeController x = new HomeController();
    @FXML
    private AnchorPane Manger_Employee;
    @FXML
    private VBox Vbox;
    @FXML
    private Label Employees;
    @FXML
    private Label E_name;
    @FXML
    private Label E_Code;
    @FXML
    private Label E_phone;
    @FXML
    private Label E_Address;
    @FXML
    private Label E_Salary;
    @FXML
    private TableView<Employee> E_tables;
    private static String oldBar="";
    @FXML
    private TextField E_Tname;
    @FXML
    private TextField E_Tcode;
    @FXML
    private TextField E_Tphone;
    @FXML
    private TextArea E_Taddress;
    @FXML
    private TextField E_Tsalary;
     @FXML
    private TableColumn<Employee, String> t_address;
     @FXML
    private TableColumn<Employee, Double> t_salary;
     @FXML
    private TableColumn<Employee, String> t_phone;
     @FXML
    private TableColumn<Employee, String> t_code;
     @FXML
    private TableColumn<Employee, String> t_name;

     /**
     * Initializes the controller class.
     */
    
    DatabaseHandler databaseHandler; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
     databaseHandler= DatabaseHandler.getInstance();
     t_name.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
     t_code.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
     t_phone.setCellValueFactory(new PropertyValueFactory<>("employeePhone"));
     t_salary.setCellValueFactory(new PropertyValueFactory<>("employeeSalaryHours"));
     t_address.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
     DataHelper.loadEmployeesData(E_tables);  
    }    
    
   
    
    
    
    
    
    //Method To load Employee Reports page
    @FXML
    private void Employee_Reports(ActionEvent event) {
        if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){
        x.loadwindow(Manger_Employee,"/Manager/Employee/Reports/Employee_Reports.fxml");
    }
    }
    //Method To load Admin home page
    @FXML
    private void Manager_Home(ActionEvent event) {
        if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){
        x.loadwindow(Manger_Employee,"/Manager/Main/Home.fxml");
    }
    }
    //Method to call add employee method
    @FXML
    private void Add_Employee(ActionEvent event) throws SQLException {
        this.AddEmployee();
    }
    //Method to call delete employee method
    @FXML
    private void Delete_Employee(ActionEvent event){
        this.Delete_Employee();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    /*
    *
    Let's Stsart to The implementation of methods
    *
    */
    
    
    
    /////////////////////////////////////////////////// Buttons Methods//////////////////////////////////////////////////////////////////
    
    
    
    
    
    /******************************************************** Add new Employee****************************************************/
      
    
    
    private void AddEmployee() throws SQLException{

             if(Validations.textInputNotEmpty(E_Tname,E_Tcode,E_Tphone,E_Tsalary,E_Taddress)){
             try{
             if ( Validations.isPositive(E_Tsalary)){ //to make sure that salary is  a posutive number
                  
                    Employee E =new Employee();
                    E.setEmployeeName(E_Tname.getText().trim());
                    E.setEmployeeId(E_Tcode.getText().trim());
                    E.setEmployeePhone(E_Tphone.getText().trim());
                    E.setEmployeeSalaryHours(Double.parseDouble(E_Tsalary.getText().trim()));
                    E.setEmployeeAddress(E_Taddress.getText().trim());
                    boolean result = DataHelper.insertNewemployee(E);
                    
                    if(result){
                        E_tables.getItems().add(E);
                        Alerts.showAlert("تمت الأضافة بنجاح",1);
                        Additional.clearTextfieldContent(E_Tname,E_Tcode,E_Tphone,E_Tsalary,E_Taddress);
                        
                    }
             }
//             else{  Alerts.showAlert("لقد ادخلت قيمة غير صحيحة!! ",3);  }
                
             
            } catch (NumberFormatException es){ Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3); }
             }  
             
//            } else {  Alerts.showAlert("برجاء ملىء جميع الحقول المطلوبة",3); }
                 
                

                }

     
    
    
    
    
    
     /********************************************************Edit Employee************************************************/
    
    
    
    
    
    @FXML
    private void Edit_Employee(ActionEvent event) throws SQLException {
         if ( Validations.textInputNotEmpty(E_Tname,E_Tcode,E_Tphone,E_Tsalary,E_Taddress)  ){   //TO check of text fields is empty
            
             //TO ckef if there another Employee with same ID 
//               while(rs.next()){
//                    String x1=rs.getString("emp_id"); 
//                   if (E_Tcode.getText().trim().equals(x1)){
//                       Alerts.showErrorAlert("يوجد موظف اخر بنفس الكود");
//                   }}
               
               
            try{   
            if (Validations.isPositive(E_Tsalary)  ){
                 String name=E_Tname.getText().trim();
                  String phone=E_Tphone.getText().trim();
                  String id =E_Tcode.getText().trim();
                  String address=E_Taddress.getText().trim();
                  double salary=Double.parseDouble(E_Tsalary.getText());
      
                 Employee e = new Employee(id,name,phone,address,salary);
                boolean result=DataHelper.updateEmployee(e,oldBar);
                
                if(result){
                    Alerts.showAlert("تم تعديل بيانات :"+e.getEmployeeName(),3);
                    DataHelper.loadEmployeesData(E_tables);
                    Additional.clearTextfieldContent(E_Tname,E_Tcode,E_Tphone,E_Tsalary,E_Taddress);
                    
                    
                } else{ Alerts.showAlert("لقد أدخلت قيما غير صحيحة",3); }
            }
//               }  else{ Alerts.showAlert("لقد أدخلت قيما غير صحيحة",3); }   
                        
               } catch (NumberFormatException es) { Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);}
   
                } 
//         else { Alerts.showAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة",3);}

                

            }
    
    
    
    
    
    
    
    
    
    
     /*********************************************************Delete Employee************************************************/
    
    private void Delete_Employee() {
        if ( E_tables.getSelectionModel().getSelectedItem() !=null  ){  
            
            
           Employee G=E_tables.getSelectionModel().getSelectedItem();
        if (Alerts.ConfirmAlert("هل تريد مسح"+":", G.getEmployeeId())) {
                Boolean result = DataHelper.deleteEmployee(G);
                
                if (result) {
                    Alerts.showAlert("تم المسح !!",1);
                    E_tables.getItems().removeAll(E_tables.getSelectionModel().getSelectedItem());
                    Additional.clearTextfieldContent(E_Tname,E_Tcode,E_Tphone,E_Tsalary,E_Taddress);
                }
                 else {  Alerts.showAlert("لم تتم العملية بشكل صحيح ",3);}
            
    } else {    Alerts.showAlert("برجاءاختيار عنصر لمسحه",3); }
        
    }    
    }
    

    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////Helper Methods///////////////////////////////////////////
    
    
    
    
    
    
    
    
     
     /*****************************************TO get items of table in  textFields*************************************/
    
    @FXML
    private void selectFromTable(MouseEvent event) {
         Employee emp=E_tables.getSelectionModel().getSelectedItem();
         
        E_Tname.setText(emp.getEmployeeName());
        E_Tcode.setText(emp.getEmployeeId());
        E_Tphone.setText(emp.getEmployeePhone());
        E_Tsalary.setText(emp.getEmployeeSalaryHours()+"");
        E_Taddress.setText(emp.getEmployeeAddress());
        oldBar=E_Tcode.getText();
    }
    


   
   


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



/*************************************************************** Methods of keyWORDS************************************/



@FXML
    private void Key_pressed(KeyEvent event) {
            try{
        if(event.getCode().equals(KeyCode.CONTROL)){  // Save when Pressing Control
          this.AddEmployee(); }
        else if (event.getCode().equals(KeyCode.DELETE)){ //Delet When Pressing Delete
          this.Delete_Employee();
        }
    }catch(Exception e){}
       
    }

    
       
  
}