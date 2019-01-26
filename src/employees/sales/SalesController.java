
package employees.sales;

import Serial_dinamic.*;
import Classes.Sales;
import Classes.Alerts;
import Classes.Price;
import static Serial_dinamic.NewSerial.getSalesSerial;
import static Serial_dinamic.NewSerial.increment_Sales;
import database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.glass.events.MouseEvent;
import employees.main.EmployeesController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class SalesController extends NewSerial implements Initializable {
    EmployeesController x = new EmployeesController();
    @FXML
    private Label date;
    @FXML
    private JFXButton saveBill;
    @FXML
    private JFXButton loadMain;
    @FXML
    private TextField billNumber;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private TextField Quntity;
    @FXML
    private TextField paid;
    @FXML
    private TextField rest;
    @FXML
    private JFXComboBox<String> quntityComboBox;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private TableColumn<Sales, String> c_cost;
    @FXML
    private TableColumn<Sales, String> c_quantity;
    @FXML
    private TableColumn<Sales, String> c_price;
    @FXML
    private TableColumn<Sales, String> c_item;
    @FXML
    private TableColumn<Sales, String> c_quantityKind;
    @FXML
    private JFXTextField T_Search;
    @FXML
    private Label productBarcode;
    @FXML
    private TableColumn<Sales, String> c_bar;
    @FXML
    private TextField totalPrice;
    @FXML
    private TableView<Sales> SalesTabel;
//    @FXML
//    public Button closeButton;
//    
//
//
//@FXML
//public void handleCloseButtonAction(ActionEvent event) {
//    
//    if(event.equals(MouseEvent.EXIT)){
//    if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){
//    Stage stage = (Stage) closeButton.getScene().getWindow();
//    stage.close();
//    }
//}
//}
    //          <Button fx:id="closeButton" cancelButton="true" layoutX="350.0" layoutY="767.0" mnemonicParsing="false" onAction="#handleCloseButtonAction" prefWidth="100.0" text="Close" />
    
    
    /**
     * Initializes the controller class.
     */
    
    
    
    /***************************************************************************************************************/
    /******************************************INITIALIZATION*******************************************************/
    /***************************************************************************************************************/

    
    
    DatabaseHandler databaseHandler;
    private static double TOTAL=0; // TOTAL is global var represents TotalPrice and it back to ZERO with new bill generated
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
        ObservableList<String> list= FXCollections.observableArrayList("قطعة","علبة","كرتونة");
        quntityComboBox.setItems(list);
        quntityComboBox.setValue("قطعة");
        date.setText(gettDate());
        setSalesSerial(DataHelper.getLastSerialTodaySales(gettDate()));
        billNumber.setText(getSalesSerial()+"");
        initTableViewCols();
        ser(); // Set Dinamic Serial Number and Date
        DataHelper.checkDataBar(T_Search); // get barcode of all products 
        
        SelectString();
    }
    private void SelectString(){
        T_Search.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (T_Search.isFocused() && !T_Search.getText().isEmpty()) {
                        T_Search.selectAll();
                    }
                }
                });
            }
        });
        
    }

//    private void DRR(){
//        String qu="DROP TABLE emp_left";
//        String qu2="DROP TABLE emp_att";
//        boolean r=databaseHandler.execAction(qu);
//        boolean r2=databaseHandler.execAction(qu2);
//        if(r && r2)
//            System.out.println("    dedddddddddddddd");
//    }

    private  void initTableViewCols(){
        c_item.setCellValueFactory(new PropertyValueFactory<>("name"));
        c_price.setCellValueFactory(new PropertyValueFactory<>("UintPrice"));
        c_quantity.setCellValueFactory(new PropertyValueFactory<>("CurrentQuantity"));
        c_quantityKind.setCellValueFactory(new PropertyValueFactory<>("quantityKind"));
        c_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        c_bar.setCellValueFactory(new PropertyValueFactory<>("barcodfiled"));
    }
    private void ser(){ // set dinamic serial to bills .. every 24 hours it set serial to 1 
        Serial_S z;
        Time_Out t=new Time_Out();
        int s=t.getHoursUntilTarget();
        z=new Serial_S(s);
    }
    
   
    
    /******************* Search with button or pressing enter ******************/
    Price pri;
    @FXML
    private void S_Field(KeyEvent event) {
        searrch();
        productPrice.setText(pri.getItemPrice()+"");
        
    }

    @FXML
    private void searchButton(ActionEvent event) {
        clear();
        searrch();
        
    }
    
    private void searrch(){
        pri=new Price();
        DataHelper.fillSalesWithInfoOfProduct(T_Search.getText(),productBarcode,productName,productPrice,pri,quntityComboBox);
        System.out.println(pri.getItemPrice());  
    }
    
    
    
    
    
    
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/
    
    
    
    
    
    
    
    /************************* ADD OR DELETE BILL TO DATABASE *****************/
    
    
    @FXML
    private void AddNewBill(ActionEvent event) { // AddNewBill(); do it by mouse click on button "جديد"
        this.AddNewBill();
    }
    @FXML
    private void A_N_B(KeyEvent event) {    // AddNewBill(); do it by pressing in SHIFT with Z  keys 
        try{                                // deleteAllRows(); impelemented by pressing in SHIFT with DELETE keys
        if(event.getCode().equals(KeyCode.CONTROL)) 
             this.AddNewBill();
        else if(event.getCode().equals(KeyCode.SHIFT.DELETE))
            this.deleteAllRows();
        }catch(Exception e){}
    }
    
    
    
    
    /**************************************************************************/
    
    
    
    
    /************************* ADD ITEMS TO BILL TABLE VIEW *******************/
    
    @FXML
    private void AddQuantity(ActionEvent event) { // AddQuantity(); do it by mouse click on button "إدخال الكمية"
        this.AddQuantity();
    }
    @FXML
    private void Q_A_K(KeyEvent event) {    // AddQuantity(); do it by pressing into enter key while focus on Quantity textfield
        try{
        if(event.getCode().equals(KeyCode.ENTER)) {
             this.AddQuantity();
        }
        }catch(Exception e){}
    }
    
    
    /**************************************************************************/
    
    
    /********************* DELETE ROW FROM TABLE VIEW *************************/
    
    @FXML
    private void DeleteRow(ActionEvent event) { // DeleteRow(); do it by mouse click on button "مسح عنصر"
            this.DeleteRow(); 
    }
    
    @FXML
    private void D_I_T(KeyEvent event) { 
        try{
        if(event.getCode().equals(KeyCode.DELETE)) { // DeleteRow(); do it by pressing in Delete key while focus on row in table view
             // do something
             this.DeleteRow();
        }}catch(Exception e){}
    }
    
    /**************************************************************************/
    
    
    
    /*********************** ADD PAID MONEY ***********************************/
    
    @FXML
    private void AddPaid(ActionEvent event) { // AddPaid(); do it by mouse click on button "ادخال المدفوع"
        this.AddPaid();
    }
    @FXML
    private void E_P(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) { // AddPaid(); do it by pressing into ENTER key while focus on field of paid money
             this.AddPaid();
        }
    }
    
    
    /**************************************************************************/
    
    /***************************** CANCEL BILL ********************************/
    
    @FXML
    private void cancelBill(ActionEvent event) {
        if (Alerts.ConfirmAlert("هل تريد مسح جميع عناصر الفاتورة ؟!!","")) {
        deleteAllRows();
        clear();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**************************************************************************/
    
    
    /**************************************************************************/
                /****************************************************/
                            /**********************/
    
    
    
    
    
    /*****************************************************************************************************************/
    /*****************************************************************************************************************/
    /*****************************************************************************************************************/
    /*****************************************************************************************************************/
    /***************************************IMPLEMENTATION OF METHODS*************************************************/
    /*****************************************************************************************************************/
    
    
    /********************************************* AddNewBill _________*/
    private void AddNewBill(){
        if(!totalPrice.getText().equals("")&&!this.paid.getText().equals("") && !this.rest.getText().equals("")){
            try{
                billNumber.setText(getSalesSerial()+"");
                Sales S=new Sales();
                S.setSerial(Integer.parseInt(billNumber.getText()));
                Date JDBC_Date = Date.valueOf(this.date.getText());
                S.setDate(JDBC_Date);
                S.setTime(gettTime());
                S.setTotalPrice(Double.parseDouble(totalPrice.getText()));
                S.setPaid(Double.parseDouble(this.paid.getText()));
                S.setReminderMoney(Double.parseDouble(this.rest.getText()));

                boolean result = DataHelper.insertNewBill(S);


                if(result){
                    increment_Sales(); //
                    billNumber.setText(getSalesSerial()+"");
                    System.out.println(getSalesSerial());
                    TOTAL=0;
                    paid.setText(gettTime()+"");
                    clear();
                    Alerts.showAlert("تم اضافة الفاتورة رقم  بنجاح !!",1);
                }
                else
                    Alerts.showAlert("لم تتم العملية بشكل صحيح .. يرجى التواصل مع الدعم الفنى",3);
            }catch(Exception e){}
        }
        
        
    }
    
    /********************************************* AddQuantity _________*/
    private void AddQuantity() {
        
        Sales S=new Sales(); 
        if(!productBarcode.getText().equals("") ){
             if(  !Quntity.getText().equals("")){
            try{
                if(Double.parseDouble(Quntity.getText())>0){
                S.setCurrentQuantity(Integer.parseInt(Quntity.getText()));
                S.setSerial(Integer.parseInt(billNumber.getText()));
                Date JDBC_Date = Date.valueOf(this.date.getText());// JDBC_Date: this var take value of date in "Date"data type to pass it to Date column in database
                S.setDate(JDBC_Date);
                S.setBarcodfiled(productBarcode.getText());
                S.setName(productName.getText());
                S.setQuantityKind(quntityComboBox.getValue());
                if(quntityComboBox.getValue().equals("قطعة")){
                S.setUintPrice(pri.getItemPrice());
                S.setCost(S.CalcCostOfSoldItem(pri.getItemPrice(),Double.parseDouble(Quntity.getText())));
                }
                else if(quntityComboBox.getValue().equals("علبة")){
                S.setUintPrice(pri.getPacketPrice());
                S.setCost(S.CalcCostOfSoldItem(pri.getPacketPrice(),Double.parseDouble(Quntity.getText())));
                }
                else if(quntityComboBox.getValue().equals("كرتونة")){
                S.setUintPrice(pri.getBoxPrice());
                S.setCost(S.CalcCostOfSoldItem(pri.getBoxPrice(),Double.parseDouble(Quntity.getText())));
                }
                long k=DataHelper.getLastOrderNumber(); 
                S.setNumber(k); // give number for every added item in bill .. 
                boolean result = DataHelper.insertNewSale(S);

                System.out.println(k);
                int qty=Integer.parseInt(Quntity.getText());
                if(DataHelper.CheckQuan(productBarcode.getText()))
                    Alerts.showAlert("لقد وصلت كمية هذا المنتج الى الحد الادنى !!",1);
                boolean s= DataHelper.InterAction_B_Sales__Products_addQuan(productBarcode,qty,quntityComboBox.getValue());
                if(s){
                    if(result){
                        SalesTabel.getItems().add(S);
                        TOTAL+=S.getCost();
                        totalPrice.setText(TOTAL+"");
                        clearSome();
                     //   Alerts.showInfoAlert("تمت الاضافة !!");
                    }
                    else
                        Alerts.showAlert("لم تتم العملية بشكل صحيح ",3);
                }
                }else{Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);}
            }catch(NumberFormatException es){
                Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);
            }
            
       
    }else Alerts.showAlert("أدخل الكمية",3);
     }else Alerts.showAlert("لايوجد منتج للأضافة",3);
    
    }
    
    
    
    
    
    /********************************************* AddPaid _________*/
    private void AddPaid(){
        Sales S=new Sales();
        try{
            if(paid.getText().equals(""))
                {Alerts.showAlert("يرجى ادخال المبلغ المدفوع",3);}
            else{
                double reminder=S.CalcReminderMoney(Double.parseDouble(paid.getText()),
                             Double.parseDouble(totalPrice.getText()));
                rest.setText(reminder+"");
            }
        }catch(NumberFormatException ex){
            Alerts.showAlert("لقد ادخلت قيمةغير صحيحة",3);
        }
    }
    
    
    
    
    /********************************************* DeleteRow _________*/
    private void DeleteRow(){
        if(SalesTabel.getItems().isEmpty()){
            
            Alerts.showAlert("لا يوجد بيانات فى الجدول !!",3);
        }
        else if (SalesTabel.getSelectionModel().getSelectedItem() == null ){
            Alerts.showAlert("حدد عنصر أولا",3);
        }
        else{
            double c=SalesTabel.getSelectionModel().getSelectedItem().getCost();
            Sales S =SalesTabel.getSelectionModel().getSelectedItem();
            if (Alerts.ConfirmAlert("هل تريد مسح  ", S.getName())) {
                Boolean result = DataHelper.deleteSale(S);
                if (result) {
                    try{
                    double p=Double.parseDouble(rest.getText());
                    rest.setText((c+p)+"");
                    }catch(Exception ex){}
                    
                    if(S.getQuantityKind().equals("قطعة"))
                    {
                        boolean rs=DataHelper.InterAction_B_Sales__Products_DeleteQuan(S.getBarcodfiled(), S.getCurrentQuantity(),S.getQuantityKind());
                        if(rs)
                            Alerts.showAlert("تم المسح !!",1);
                        else
                            Alerts.showAlert("لم يتم المسح ",3);
                                   
                    }
                    
                    SalesTabel.getItems().removeAll(SalesTabel.getSelectionModel().getSelectedItem()); // delete item from ui table
                    TOTAL-=c;
                    totalPrice.setText(TOTAL+"");
                    
                } else {
                    Alerts.showAlert("لم تتم العملية بشكل صحيح .",3);
                }
            }
        }
    }
    
    
    /********************************************* deleteAllRows _________*/
    private void deleteAllRows(){  
        if(SalesTabel.getItems().isEmpty() && T_Search.getText().equals("")){
            Alerts.showAlert("لا يوجد بيانات لتتم عملية المسح !!",3);
        }
        else{
                if(Alerts.ConfirmAlert("هل تريد مسح جميع عناصر الجدول ؟","")){
                    SalesTabel.getItems().forEach((t) -> {
                           boolean de=DataHelper.InterAction_B_Sales__Products_DeleteQuan(t.getBarcodfiled(), t.getCurrentQuantity(),t.getQuantityKind());
                    });
                    boolean result=DataHelper.deleteAllRowsInSalesTV(getSalesSerial());
                    if(result){
                        SalesTabel.getItems().clear();
                        Alerts.showAlert("تم مسح جميع العناصر",3);
                    }
                    else
                        Alerts.showAlert("لم تتم العملية بشكل صحيح",3);
                }
           TOTAL=0;
        }
    }
    
    /************************************CLEAR DATA FROM FIELDS _________*/
    private void clear(){
        T_Search.clear();
        productName.setText("");
        productPrice.setText("");
        productBarcode.setText("");
        Quntity.clear();
        totalPrice.clear();
        paid.clear();
        rest.clear();
        SalesTabel.getItems().clear();
    }
    
    private void clearSome(){
        T_Search.clear();
        productName.setText("");
        productPrice.setText("");
        productBarcode.setText(""); 
        Quntity.clear();
    }
    
    /**********************_____________END OF IMPELMTNTAION______________************************/
    
    
    /*********************************      LOAD PAGES     ***************************************/
    @FXML
    private void loadMainOfSales(ActionEvent event) {
        //loadWindow("/employees/main/employees.fxml");
        if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){
        x.loadwindow(loadPane, "/employees/main/employees.fxml");
    }
    }

    @FXML
    private void calcButton(ActionEvent event) {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException ex) {
           Alerts.showAlert("حدث مشكلة أثناء فتح الالة الحاسبة , يرجى اعادة المحاولة",3) ;
        }
    }

    /***************************_____________THE END______________********************************/    
}

    





















    /***************** DON'T DELETE THIS -->
     * 
     * 
     */
        /*private void deleteAllRows(){  
        if(SalesTabel.getItems().isEmpty()){
            Alerts.showErrorAlert("لا يوجد بيانات فى الجدول !!");
        }
        else{
            if(AllCheckbox.isSelected()){
                if(Alerts.ConfirmAlert("هل تريد مسح جميع عناصر الجدول ؟","")){
                    SalesTabel.getItems().forEach((t) -> {
                           boolean de=DataHelper.InterAction_B_Sales__Products_DeleteQuan(t.getBarcodfiled(), t.getCurrentQuantity(),t.getQuantityKind());
                    });
                    boolean result=DataHelper.deleteAllRowsInSalesTV(getSalesSerial());
                    if(result){
                        SalesTabel.getItems().clear();
                        Alerts.showInfoAlert("تم مسح جميع العناصر");
                    }
                    else
                        Alerts.showErrorAlert("لم تتم العملية بشكل صحيح .. يرجى التواصل مع الدعم الفنى");
                }
            }
            else{
                Alerts.showErrorAlert("اضغط على مربع "+" \"الكل\" "+"لتتم عملية مسح جميع العناصر");
            }
            AllCheckbox.setSelected(false);
        }
    }*/
























       /* try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage= new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            Image icon = new Image("/icons/my_account.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(parent));
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(EmployeesController.class.getName()).log(Level.SEVERE, null, ex);
        }*/