
package employees.account.accept.attendence;

import com.jfoenix.controls.JFXButton;
import employees.main.EmployeesController;
import java.net.URL;
import com.jfoenix.controls.JFXTextField;
import database.DataHelper;
import employees.account.AccountController;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class AttendenceController extends AccountController implements Initializable {
    EmployeesController x = new EmployeesController();
    @FXML
    private AnchorPane loadPane;
    @FXML
    private JFXTextField start_work;
    @FXML
    private JFXTextField finish_work;
    private JFXTextField difference;
    @FXML
    private JFXTextField differenceText;
    @FXML
    private JFXButton difference_btn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        database.DataHelper.checkattendance(EmpCode, start_work, getDate());
    }    

    @FXML
    private void loadBack(ActionEvent event) {
        //loadWindow("/employees/account/accepted/accountaccept.fxml");
        x.loadwindow(loadPane,"/employees/account/accepted/acoountaccept.fxml");
    }
    private void attt()
    {
        start_work.setText(getTime()+"");
        java.sql.Date JDBC_Date = java.sql.Date.valueOf(getDate());
        DataHelper.insertAttendence(JDBC_Date, EmpName, EmpCode, getTime());
    }
    
    private void left()
    {
        finish_work.setText(getTime()+"");
        java.sql.Date JDBC_Date = java.sql.Date.valueOf(getDate());
        DataHelper.insertleft(JDBC_Date, EmpName, EmpCode, getTime());
    }
    
    @FXML
    private void att_btn(ActionEvent event) {
        this.attt();
    }
    
    @FXML
    private void leave_dtn(ActionEvent event) {
        this.left();
        java.sql.Date JDBC_Date = java.sql.Date.valueOf(getDate());
     //   DataHelper.insertWorkHours(JDBC_Date, EmpName, EmpCode, difff());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void differenceAction(ActionEvent event) {
        double salary;
        String start = start_work.getText();
        String finish = finish_work.getText();
         long differenceCalc=0;

        try{SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            Date date1 = format.parse(start);
            Date date2 = format.parse(finish);
            differenceCalc = date2.getTime() - date1.getTime(); 
        }catch(Exception e){
            System.out.println("test");
        }
        long timeDifference = differenceCalc/1000;
        int h = (int) (timeDifference / (3600));
        int m = (int) ((timeDifference - (h * 3600)) / 60);
        int s = (int) (timeDifference - (h * 3600) - m * 60);
        
         String finalTime = String.format("%02d:%02d:%02d", h,m,s);
        
        salary = h*m * 20;
        
        System.out.println(h);
        differenceText.setText(finalTime);
        //this.diff();
    }
    private int difff(String st,String fh){
        double salary;
        String start = start_work.getText();
        String finish = finish_work.getText();
         long differenceCalc=0;

        try{SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            Date date1 = format.parse(start);
            Date date2 = format.parse(finish);
            differenceCalc = date2.getTime() - date1.getTime(); 
        }catch(Exception e){
            System.out.println("test");
        }
        long timeDifference = differenceCalc/1000;
        int h = (int) (timeDifference / (3600));
        int m = (int) ((timeDifference - (h * 3600)) / 60);
        int s = (int) (timeDifference - (h * 3600) - m * 60);
        
         String finalTime = String.format("%02d:%02d:%02d", h,m,s);
        
        salary = h*m * 20;
        
        System.out.println(h+"  "+s);
        differenceText.setText(finalTime);
        //this.diff();
        return  h;
    }
    private String getDate(){
        java.util.Date today;
        SimpleDateFormat simpleDF;
        today = new java.util.Date();
        simpleDF = new SimpleDateFormat ("yyyy-MM-dd");
        return simpleDF.format(today);
    }
    
    private Time getTime(){
        Time rightNow = Time.valueOf(LocalTime.now());
        return rightNow;
    }
}
/*

<table-entry name="employee2" col-data="
            emp_exp_date date
            ,emp_name varchar(200)
            ,emp_id varchar(200) 
            ,emp_start_time timestamp not null default current_timestamp
            ,emp_finish_time timestamp not null default current_timestamp
            ,emp_salary double
            ,emp_hours integer
            ,emp_buy varchar(200)
            ,emp_net_salary double
            ,emp_date timestamp not null default current_timestamp
            
        "/>
*/