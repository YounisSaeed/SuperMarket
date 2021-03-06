
package employees.buying;

import Classes.Additional;
import Classes.Buying;
import Classes.Alerts;
import Classes.Price;
import Classes.Validations;
import Serial_dinamic.*;
import static Serial_dinamic.NewSerial.getSalesSerial;
import Serial_dinamic.Time_Out;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.DataHelper;
import employees.main.EmployeesController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class BuyingController  extends NewSerial implements Initializable {
    EmployeesController x = new EmployeesController();
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton loadMain;
    @FXML
    private Label date;
    @FXML
    private TextField billNumber;
    @FXML
    private Label productName;
    @FXML
    private Label productPrise;
    @FXML
    private TextField Quntity;
    @FXML
    private JFXComboBox<String> quntityComboBox;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private JFXComboBox<String> supplier;
    @FXML
    private TableView<Buying> B_table;
    @FXML
    private TableColumn<Buying, String> t_cost;
    @FXML
    private TableColumn<Buying, String> t_kquan;
    @FXML
    private TableColumn<Buying, String> t_quan;
    @FXML
    private TableColumn<Buying, String> t_cate;
    @FXML
    private TableColumn<Buying, String> t_bar;
    @FXML
    private JFXTextField B_searchField;
    @FXML
    private TextField totalPrice;
    @FXML
    private Label productBarcode;
    @FXML
    private TableColumn<Buying, String> t_price;
    
    
    /**
     * Initializes the controller class.
     */
    Price pri;
    private static double TOTAL=0; // TOTAL is global var represents TotalPrice and it back to ZERO with new bill generated
    @FXML
    private TextField BuuPrice;
    @FXML
    private Label alterLabel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list= FXCollections.observableArrayList("قطعة","علبة","كرتونة");
        quntityComboBox.setItems(list);
        quntityComboBox.setValue("كرتونة");
        supplier.setItems(DataHelper.checkDataSupp());
        date.setText(gettDate());
        setSalesSerial(DataHelper.getLastSerialTodayBuying(gettDate()));
        billNumber.setText(getSalesSerial()+"");
        initTableViewCols();
        DataHelper.checkDataBar(B_searchField); // get barcode of all products
        ser();
    }    
    private  void initTableViewCols(){
        t_bar.setCellValueFactory(new PropertyValueFactory<>("barcodfiled"));
        t_cate.setCellValueFactory(new PropertyValueFactory<>("name"));
        t_price.setCellValueFactory(new PropertyValueFactory<>("UintPrice"));
        t_quan.setCellValueFactory(new PropertyValueFactory<>("CurrentQuantity"));
        t_kquan.setCellValueFactory(new PropertyValueFactory<>("quantityKind"));
        t_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }
    private void ser(){ // set dinamic serial to bills .. every 24 hours it set serial to 1 
        Serial_B z;
        Time_Out t=new Time_Out();
        int s=t.getHoursUntilTarget();
        z=new Serial_B(s);
    }
    
    /******************* Search with button or pressing enter ******************/
    @FXML
    private void B_SearchButton(ActionEvent event) {
        searrch();
        productPrise.setText(pri.getItemPrice()+"");
    }
    private void searrch(){
        pri=new Price();
        DataHelper.fillSalesWithInfoOfProduct(B_searchField.getText(),productBarcode,productName,productPrise,pri,quntityComboBox);// 
        // fill Search Field with barcodes of all products in market
        // pri : initialize price of item,packet,box of specific product you search about
        System.out.println(pri.getItemPrice());  
    }
    

    @FXML
    private void B_SearchField(KeyEvent event) {
        searrch();  
    }
    /**************************************************************************/
    /**************************************************************************/
    /**************************************************************************/

    
    /************************* ADD OR DELETE BILL TO DATABASE *****************/
    @FXML
    private void addBuying(ActionEvent event) { // AddNewBill(); do it by mouse click on button "جديد" 
             this.addBuying();
    }
    
    @FXML
    private void A_N_B(KeyEvent event) {  // addBuying(); do it by pressing in SHIFT with Z  keys 
        try{
        if(event.getCode().equals(KeyCode.CONTROL))
             this.addBuying();
            
        }catch(Exception e){}
    }
    /**************************************************************************/

    
    /************************* ADD ITEMS TO BILL TABLE VIEW *******************/
    @FXML
    private void addQuntity(ActionEvent event) { // AddQuantity(); do it by mouse click on button "إدخال الكمية"
        this.addQuntity();
    }
    @FXML
    private void AddQuanPress(KeyEvent event) {
        try{
        if(event.getCode().equals(KeyCode.ENTER)) { // deleteRow(); do it by pressing in Delete key while focus on row in table view
             this.addQuntity();
             
        }}catch(Exception e){}
    }
    /**************************************************************************/
    
    /********************* DELETE ROW FROM TABLE VIEW *************************/
    @FXML
    private void DeleteRow(ActionEvent event) { // DeleteRow(); do it by mouse click on button "مسح عنصر"
        this.deleteRow();
    }
    
    @FXML
    private void DelRow(KeyEvent event) {
        try{
        if(event.getCode().equals(KeyCode.DELETE)) { // deleteRow(); do it by pressing in Delete key while focus on row in table view
             // do something
             this.deleteRow();
        }}catch(Exception e){}
    }
    /**************************************************************************/
    
    
    ////////////// ???????????????????????????????????????
    @FXML 
    private void cancelBuying(ActionEvent event) {
        if(Alerts.ConfirmAlert("هل تريد مسج جميع عناصر فاتورة الشراء؟",""))
        {
         deleteAllRows();
            Additional.clearTextfieldContent(B_searchField,Quntity,totalPrice,BuuPrice);
            Additional.clearLabelContent(productPrise, productBarcode,productName);
            Additional.clearComboBoxContent(supplier);
            Additional.clearTableContent(B_table);   
        }
    }
    ////////////// ???????????????????????????????????????
    
    
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /*****************************************IMPLEMENTATION OF METHODS********************************************/

    
    /********************************************* addBuying _________*/
    private void addBuying()
    {
        try{
        if(!totalPrice.getText().equals("") && !supplier.getValue().equals(""))
        {
            
            billNumber.setText(getSalesSerial()+"");
            Buying B=new Buying();
            B.setSerial(Integer.parseInt(billNumber.getText()));
            Date JDBC_Date = Date.valueOf(this.date.getText()); // JDBC_Date: this var take value of date in "Date"data type to pass it to Date column in database
            B.setDate(JDBC_Date);
            B.setTime(gettTime());
            B.setTotalPrice(Double.parseDouble(totalPrice.getText()));

            boolean result = DataHelper.insertBuyGoodsNewBill(B);
            
            increment_Sales(); //
            billNumber.setText(getSalesSerial()+"");
            System.out.println(getSalesSerial());
            TOTAL=0;
            Additional.clearTextfieldContent(B_searchField,Quntity,totalPrice,BuuPrice);
            Additional.clearLabelContent(productPrise, productBarcode,productName);
            Additional.clearComboBoxContent(supplier);
            Additional.clearTableContent(B_table);
            if(result){
                Alerts.showAlert("تم اضافة الفاتورة رقم  بنجاح !!",1);
            Additional.clearTextfieldContent(B_searchField,Quntity,totalPrice,BuuPrice);
            Additional.clearLabelContent(productPrise, productBarcode,productName);
            Additional.clearComboBoxContent(supplier);
            Additional.clearTableContent(B_table);
            }
            else{
                Alerts.showAlert("لم تتم العملية بشكل صحيح  ",3);
        }}
        else
            Alerts.showAlert("  يرجى ملئ جميع الحقول المطلوبة",3);
        }catch(Exception e){Alerts.showAlert("خطأ .. ربما لم يحدد اسم المورد",2);}
    }
     
    
    /********************************************* addQuntity _________*/
    private void addQuntity()
    {
      
        if(Validations.textInputNotEmpty(productBarcode,Quntity,Quntity))
        {
            
            Buying B=new Buying();
            try{
            B.setCurrentQuantity(Integer.parseInt(Quntity.getText()));
            B.setSerial(Integer.parseInt(billNumber.getText()));
            Date JDBC_Date = Date.valueOf(this.date.getText()); // JDBC_Date: this var take value of date in "Date"data type to pass it to Date column in database
            B.setDate(JDBC_Date);
            B.setBarcodfiled(productBarcode.getText());
            B.setName(productName.getText());
            B.setQuantityKind(quntityComboBox.getValue());
            
            B.setUintPrice(Double.parseDouble(BuuPrice.getText()));
            B.setCost(B.CalcCostOfSoldItem(Double.parseDouble(BuuPrice.getText()),Double.parseDouble(Quntity.getText())));
            
            long k=DataHelper.getLastOrderNumberBuying();
            B.setNumber(k);
            boolean result =DataHelper.insertBuyGoods(B);
          
            int qty=Integer.parseInt(Quntity.getText());
            boolean s= DataHelper.InterAction_B_Sales__Products_DeleteQuan(productBarcode.getText(),qty,quntityComboBox.getValue());
            if(s){
                if(result){
                    TOTAL+=B.getCost();
                    totalPrice.setText(TOTAL+"");
                    B_table.getItems().add(B);
                    Alerts.showAlert("تمت الاضافة !!",1);
                    Additional.clearTextfieldContent(Quntity,BuuPrice);
                    Additional.clearLabelContent(productPrise, productBarcode,productName);
                    
                }
                else
                Alerts.showAlert("لم تتم العملية بشكل صحيح .. ",1);
            }
            
            }catch(NumberFormatException es){
                Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);
            }
        }
        else
            Alerts.showAlert("برجاء ملئ جميع الحقول المطلوبة",3);
    }
    

    /********************************************* DeleteRow _________*/
    private void deleteRow(){
            
        if(B_table.getItems().isEmpty()){
            Alerts.showAlert("لا يوجد بيانات فى الجدول !!",3);
        }else if (B_table.getSelectionModel().getSelectedItem() == null ){
            Alerts.showAlert("حدد عنصر أولا",3);
        }
        else{
            double c=B_table.getSelectionModel().getSelectedItem().getCost();
            Buying S =B_table.getSelectionModel().getSelectedItem();
            if (Alerts.ConfirmAlert("هل تريد مسح  ", S.getName())) {
                Boolean result = DataHelper.deleteBuyRow(S);
                if (result) {
                        boolean rs=DataHelper.InterAction_B_Sales__Products_DeleteQuan(S.getBarcodfiled(), S.getCurrentQuantity(),S.getQuantityKind());
                        if(rs)
                            Alerts.showAlert("تم المسح !!",1);
                        else
                            Alerts.showAlert("لم يتم المسح ",3);

                    B_table.getItems().removeAll(B_table.getSelectionModel().getSelectedItem()); // delete item from ui table
                    TOTAL-=c;
                    totalPrice.setText(TOTAL+"");
                    
                } else {
                    Alerts.showAlert("لم تتم العملية بشكل صحيح ",3);
                }
            }
        }
    }
    /************************************Delete all rows ________________*/
    private void deleteAllRows(){  
        if(B_table.getItems().isEmpty()){
            Additional.clearTextfieldContent(B_searchField,Quntity,totalPrice,BuuPrice);
            Additional.clearLabelContent(productPrise, productBarcode,productName);
            Additional.clearComboBoxContent(supplier);
            Additional.clearTableContent(B_table);
        }
        else{
                    B_table.getItems().forEach((t) -> {
                           alterLabel.setText(t.getBarcodfiled());
                           boolean de=DataHelper.InterAction_B_Sales__Products_addQuan(alterLabel, t.getCurrentQuantity(),t.getQuantityKind());
                    });
                    boolean result=DataHelper.deleteAllRowsInBuyTV(getBuyingSerial());
                    if(result){
                        B_table.getItems().clear();
                        Alerts.showAlert("تم مسح جميع العناصر",1);
                    }
                    else
                        Alerts.showAlert("لم تتم العملية بشكل صحيح",3);
                
           TOTAL=0;
        }
    }
    /************************************CLEAR DATA FROM FIELDS _________*/
//    private void clear(){
//        B_searchField.clear();
//        B_table.getItems().clear();
//        productBarcode.setText("");
//        productName.setText("");
//        productPrise.setText("");
//        supplier.setValue("");
//        Quntity.clear();
//        totalPrice.clear();
//        BuuPrice.clear();
//       
//        
//        
//    }
//    private void clearSome(){
//       productPrise.setText("");
//       Quntity.clear();
//       BuuPrice.clear();
//       productBarcode.setText("");
//       productName.setText("");
//        
//
//    }
    

    
    
    /**********************_____________END OF IMPELMTNTAION______________************************/

    
    /*********************************      LOAD PAGES     ***************************************/
    @FXML
    private void loadMainOfBuying(ActionEvent event) {
        x.loadwindow(loadPane,"/employees/main/employees.fxml");
    }

    @FXML
    private void calcButton(ActionEvent event) {
         Additional.openCalculator();
    }

    
    /***************************_____________THE END______________********************************/    
}




 /******** @@@@ _____ Old add quantity mechanism _____ @@@@ ********/
/*
    private void addQuntity()
    {
        if(!Quntity.getText().equals("") && !productBarcode.getText().equals("") && !supplier.getValue().equals(""))
        {
            Buying B=new Buying();
            try{
            B.setCurrentQuantity(Integer.parseInt(Quntity.getText()));
            B.setSerial(Integer.parseInt(billNumber.getText()));
            Date JDBC_Date = Date.valueOf(this.date.getText()); // JDBC_Date: this var take value of date in "Date"data type to pass it to Date column in database
            B.setDate(JDBC_Date);
            B.setBarcodfiled(productBarcode.getText());
            B.setName(productName.getText());
            B.setQuantityKind(quntityComboBox.getValue());
            if(quntityComboBox.getValue().equals("قطعة")){
            B.setUintPrice(pri.getItemPrice());
            B.setCost(B.CalcCostOfSoldItem(pri.getItemPrice(),Double.parseDouble(Quntity.getText())));
            }
            else if(quntityComboBox.getValue().equals("علبة")){
            B.setUintPrice(pri.getPacketPrice());
            B.setCost(B.CalcCostOfSoldItem(pri.getPacketPrice(),Double.parseDouble(Quntity.getText())));
            }
            else if(quntityComboBox.getValue().equals("كرتونة")){
            B.setUintPrice(pri.getBoxPrice());
            B.setCost(B.CalcCostOfSoldItem(pri.getBoxPrice(),Double.parseDouble(Quntity.getText())));
            }
            
            long k=DataHelper.getLastOrderNumberBuying();
            B.setNumber(k);
            boolean result =DataHelper.insertBuyGoods(B);
          
            int qty=Integer.parseInt(Quntity.getText());
            boolean s= DataHelper.InterAction_B_Sales__Products_DeleteQuan(productBarcode.getText(),qty,quntityComboBox.getValue());
            if(s){
                if(result){
                    TOTAL+=B.getCost();
                    totalPrice.setText(TOTAL+"");
                    B_table.getItems().add(B);
                    Alerts.showInfoAlert("تمت الاضافة !!");
                }
                else
                Alerts.showErrorAlert("لم تتم العملية بشكل صحيح .. يرجى التواصل مع الدعم الفنى");
            }
            
            }catch(NumberFormatException es){
                Alerts.showErrorAlert("لقد ادخلت قيمة غير صحيحة !!");
            }
            
        }
        else
            Alerts.showErrorAlert("يرجى ادخال الكمية");
    }
    
*/