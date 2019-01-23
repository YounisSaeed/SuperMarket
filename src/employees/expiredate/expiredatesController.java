/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employees.expiredate;

import employees.rackshortages.*;
import Classes.Common_Properties;
import Classes.Goods;
import Serial_dinamic.NewSerial;
import database.*;
import employees.main.EmployeesController;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author NOUR
 */
public class expiredatesController extends NewSerial implements Initializable {
    EmployeesController x =  new EmployeesController();

    @FXML
    private AnchorPane loadPane;
    @FXML
    private TableView<Goods> rackshortagesTable;
    @FXML
    private TableColumn<Goods, String> t_quan;
    @FXML
    private TableColumn<Goods, String> t_cate;
    @FXML
    private TableColumn<Goods, String> t_bar;

    /**
     * Initializes the controller class.
     */
        
    DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
        initTableViewCols();
        DataHelper.loadExpiredData(rackshortagesTable,gettDate());
    }    
    private  void initTableViewCols(){
        t_bar.setCellValueFactory(new PropertyValueFactory<>("productBarCode"));
        t_cate.setCellValueFactory(new PropertyValueFactory<>("productName"));
        t_quan.setCellValueFactory(new PropertyValueFactory<>("productExpirationdate"));
    }
    @FXML
    private void loadRackMain(ActionEvent event) {
    //loadWindow("/employees/main/employees.fxml");
        x.loadwindow(loadPane,"/employees/main/employees.fxml");
    }
}
