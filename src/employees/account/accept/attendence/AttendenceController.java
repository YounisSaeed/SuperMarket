
package employees.account.accept.attendence;

import com.jfoenix.controls.JFXButton;
import employees.main.EmployeesController;
import java.net.URL;
import com.jfoenix.controls.JFXTextField;
import employees.account.AccountController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        
    }    

    @FXML
    private void loadBack(ActionEvent event) {
        //loadWindow("/employees/account/accepted/accountaccept.fxml");
        x.loadwindow(loadPane,"/employees/account/accepted/acoountaccept.fxml");
    }
    private void attt()
    {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
            Date cal = new Date();
        start_work.setText(dateFormat.format(cal.getTime()));
    }
    
    private void left()
    {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
            Date finish = new Date();
        finish_work.setText(dateFormat.format(finish.getTime()));
    }
    
    @FXML
    private void att_btn(ActionEvent event) {
        this.attt();
    }
    
    @FXML
    private void leave_dtn(ActionEvent event) {
        this.left();
    }
    @FXML
    private void differenceAction(ActionEvent event) {
        double salary;
        String start = start_work.getText();
        String finish = finish_work.getText();
         long differenceCalc=0;

        try{SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
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
        
        System.out.println(salary);
        differenceText.setText(finalTime);
        //this.diff();
    }
}
