
package Manager.Suppliers;

import Classes.Additional;
import Classes.Alerts;
import Classes.Suppliers;
import Classes.Validations;
import Manager.Main.HomeController;
import database.DataHelper;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;




public class Manager_SuppliersController implements Initializable {
      HomeController x = new HomeController();
    @FXML
    private AnchorPane Manager_Suppliers;
    @FXML
    private VBox VBox;
    @FXML
    private Label Suppliers;
    @FXML
    private Label S_name;
    @FXML
    private Label S_Phone;
    @FXML
    private Label S_Saller;
    @FXML
    private TableView<Suppliers> S_Table;
    @FXML
    private TextField S_TSearch;
    @FXML
    private TextField S_Tname;
    @FXML
    private TextField S_TPhone;
    @FXML
    private TextField S_TSaller;
    @FXML
    private TableColumn<Suppliers, String> t_supplier;
    @FXML
    private TableColumn<Suppliers, String> t_phone;
    //private TableColumn<Suppliers, String> t_category;
    @FXML
    private TableColumn<Suppliers, String> t_name;
    DatabaseHandler databaseHandler;
    private  String oldCompName="";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
        t_name.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
      //  t_category.setCellValueFactory(new PropertyValueFactory<>("supplierCategory"));
        t_phone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
        t_supplier.setCellValueFactory(new PropertyValueFactory<>("salespersonName"));
        DataHelper.loadSuppliersData(S_Table);
        DataHelper.checkDataSupNames(S_TSearch);
         }    
    
    
    /**************************************Buttons to move to another Page*************************************/
    @FXML
    private void Suppliers_Reports(ActionEvent event) {          
         if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){// Suuplier Reports Page
         x.loadwindow(Manager_Suppliers,"/Manager/Suppliers/Reports/Sppliers_Reports.fxml");
    }}

    @FXML
    private void Manager_Home(ActionEvent event) {    
        // Manager Main Page
         if (Alerts.ConfirmAlert("هل تريد الخروج من الصفحة وألغاء أي تغييرات ؟","")){
        x.loadwindow(Manager_Suppliers,"/Manager/Main/Home.fxml");
    }}

    
    
    
    
    
    /*****************************************************button of add and edit and delete**************************/
    @FXML
    private void Add_Supplier(ActionEvent event) {
        this.AddSupplier();
    }

    @FXML
    private void Edit_Supplier(ActionEvent event) {
        this.Edit_Supplier();
    }

    @FXML
    private void Delete_Supplier(ActionEvent event) {
         this.Delete_Supplier();
    }

    @FXML
    private void Suppliers_Search(ActionEvent event) {
          this.Suppliers_Search();
    }
    
    
       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    
    /**************************************************************Method of Add Supplier******************************************/
    
    
    private void AddSupplier()
    {
        try {
        if(Validations.textInputNotEmpty(S_Tname,S_TPhone,S_TSaller))
        {
           
                    Suppliers s =new Suppliers();
                    s.setSupplierName(S_Tname.getText());
                    s.setSupplierPhone(S_TPhone.getText());
                    s.setSalespersonName(S_TSaller.getText());
                    boolean result = DataHelper.insertNewSupplier(s);

                    
                    if(result){
                        S_Table.getItems().add(s);
                        Alerts.showAlert("تم الاضافة",1);
                        Additional.clearTextfieldContent(S_Tname,S_TPhone,S_TSaller);
                    }
                    else 
                        Alerts.showAlert("خطأ فى الاضافة",3);
                   
        
        }
          
         } catch (NullPointerException e){                   
                Alerts.showAlert("برجاء التأكد من ملىء جميع الحقول المطلوبة ",3);
                 }
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    /**************************************************************ُُEdit Supplier information****************************************************/
    
    
    
     private void Edit_Supplier() {
         
        if ( Validations.textInputNotEmpty(S_Tname,S_TPhone,S_TSaller)){  
            try{
                String name=S_Tname.getText();
                String phone=S_TPhone.getText();
                String salesperson=S_TSaller.getText();
                Suppliers S=new Suppliers(name, phone, salesperson);
                boolean R=DataHelper.updateSupplier(S, oldCompName);
                if(R)
                {
                    Alerts.showAlert("تم التعديل",1);
                    DataHelper.loadSuppliersData(S_Table);
                     Additional.clearTextfieldContent(S_Tname,S_TPhone,S_TSaller);
                }
            }
            catch (NumberFormatException es)
            {
                Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);
            }
        }
    }
    
    
     
     
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     
     
     
     /**************************************************************Delete Supplier ****************************************************************/
     
     
    private void Delete_Supplier() {
        
       // try {
         if ( S_Table.getSelectionModel().getSelectedItem()!=null ){
            
               
           Suppliers S=S_Table.getSelectionModel().getSelectedItem();
        
        if (Alerts.ConfirmAlert("هل تريد مسح"+":", S.getSupplierName())) {
                Boolean result = DataHelper.deleteSupplier(S);
                if (result) {
                    Alerts.showAlert("تم المسح !!",1);
                   S_Table.getItems().removeAll(S_Table.getSelectionModel().getSelectedItem());
                     Additional.clearTextfieldContent(S_Tname,S_TPhone,S_TSaller);
                }
                 else 
                    Alerts.showAlert("لم تتم العملية بشكل صحيح ",3);
            }
    }else {
             Alerts.showAlert("برجاءاختيار عنصر لمسحه",3);
                }
    
             
             
             
             
           /* try{
                
                 
                 
            }catch (NumberFormatException es)
            {
                Alerts.showErrorAlert("لقد ادخلت قيمة غير صحيحة !!");
            }
         }else {
             Alerts.showErrorAlert("برجاء ملىء جميع الحقول المطلوبة");
                }
        }catch(NullPointerException e){
              Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
         }*/
    }
    
    
    private void Suppliers_Search() {
        
         if ( !S_TSearch.getText().equals("")){
            
             try{
                //yooooour coooode
            }catch (NumberFormatException es)
            {
                Alerts.showAlert("لقد ادخلت قيمة غير صحيحة !!",3);
            }
         }
          else 
        {  
            Alerts.showAlert("برجاء ادخال اسم المورد او التصنيف  ",3);
            
        }
    }
   

    private void chechbox(DragEvent event) {
        ChoiceBox cd = new ChoiceBox();
    cd.setItems(FXCollections.observableArrayList(
    "new", "Electronic ", 
    new Separator(), "liquied", "meats")
); 
    }

   
    
    ObservableList<Suppliers> list= FXCollections.observableArrayList();    
    FilteredList filter=new FilteredList(list,e->true);
    @FXML
    private void key_Search(KeyEvent event) {
        DataHelper.autoserSup(list);
        S_TSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(    (Predicate<? super Suppliers>)    (Suppliers go)->{
                if(newValue.isEmpty() || newValue == null)
                    return true;
                if(newValue.contains(go.getSupplierName()))
                    return true;
                
                return false;
            });
        });
        
        SortedList st=new SortedList(filter);
        st.comparatorProperty().bind(S_Table.comparatorProperty());
        S_Table.setItems(st);
    }

    @FXML
    private void selectFromTable(MouseEvent event) {
        
        Suppliers sup=S_Table.getSelectionModel().getSelectedItem();
        S_Tname.setText(sup.getSupplierName());
        S_TPhone.setText(sup.getSupplierPhone());
        S_TSaller.setText(sup.getSalespersonName());
        oldCompName=S_Tname.getText();
    }
    
    
    
    /*
        private TableView<Suppliers> S_Table;
    @FXML
    private TextField S_TSearch;
    @FXML
    private TextField S_Tname;
    @FXML
    private TextField S_TPhone;
    @FXML
    private TextField S_TSaller;
    @FXML
    */
    
    
    
    
    
     @FXML
    private void Key_Pressed(KeyEvent event) {
         try{
        if(event.getCode().equals(KeyCode.CONTROL)){
          this.AddSupplier(); 
        
        }
        else if (event.getCode().equals(KeyCode.DELETE)){
             this.Delete_Supplier();
        }
    }catch(Exception e){}
       
    }
    /***************************************************TO clear what in TextFields*************************************************/
    
//    private void clear()
//    {
//        S_Tname.setText("");
//        S_TPhone.setText("");
//        S_TSaller.setText("");
//       
//        
//    }
}

