
package Manager.Product.Quantity;

import Classes.Alerts;
import Classes.Validations;
import Manager.Main.HomeController;
import com.jfoenix.controls.JFXTextField;
import database.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class Manager_Product_QuantityController implements Initializable {
    HomeController x = new HomeController (); // used for load main page
    
    @FXML
    private AnchorPane Product_Quantity;
    @FXML
    private JFXTextField P_TSearch;
    @FXML
    private JFXTextField P_CQuantity;
    @FXML
    private JFXTextField P_BQuantity;
    @FXML
    private JFXTextField P_UQuantity;
    private RadioButton R_packet;
    private RadioButton R_item;
    private RadioButton R_box;
    @FXML
    private Label LName;
    @FXML
    private Label L_InP; // in this label I take the value of ItemInPackets of specific product From Database
    @FXML
    private Label L_PnB; // like L_InP but take PacketsInBox 
    
    
    DatabaseHandler databaseHandler;
    @FXML
    private Label P_Quantity;
    
    private static int itm=0,pkt=0,bx=0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler=DatabaseHandler.getInstance();
        
//        P_UQuantity.setEditable(false);
//        P_BQuantity.setEditable(false);
//        P_CQuantity.setEditable(false);
        DataHelper.getBarcodesInEditQuanPage(P_TSearch);
        P_TSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (P_TSearch.isFocused() && !P_TSearch.getText().isEmpty()) {
                        P_TSearch.selectAll();
                    }
                }
                });
            }
        });

            
    }    

    @FXML
    private void P_Search(ActionEvent event) {
         
        if ( Validations.textInputNotEmpty(P_TSearch)){
           
            this.P_Search();quann();
            System.out.println(itm+"            "+pkt+"         "+bx);
        }
       else 
            Alerts.showAlert("برجاء ادخال الباكورد ..",3);
       
    }
    @FXML
    private void AutoCompSearch(KeyEvent event) {
        P_Search();
        quann();
        System.out.println(itm+"            "+pkt+"         "+bx);
    }
    private void quann(){
        itm=Integer.parseInt(P_UQuantity.getText());
        pkt=Integer.parseInt(P_BQuantity.getText());
        bx=Integer.parseInt(P_CQuantity.getText());
    }
    @FXML
    private void Edit_Product(ActionEvent event) {
        this.editItemProduct();
    }

    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(Product_Quantity, "/Manager/Products/Manager_Products.fxml");
    }

    @FXML
    private void Manager_Home(ActionEvent event) {
        x.loadwindow(Product_Quantity, "/Manager/Main/Home.fxml");
    }
    
    
    
    //////////////////////////
    
    
    private void P_Search()
    {
        DataHelper.ProductQuantity(P_TSearch.getText(),P_UQuantity,P_BQuantity,P_CQuantity,LName);
    }
    
    
    private void editItemProduct(){
        if (!P_UQuantity.getText().equals(""))
        {
            try{
            int it=Integer.parseInt(P_UQuantity.getText());
            

            DataHelper.getQuanDetails(P_TSearch.getText(), L_InP, L_PnB);
            int inp=Integer.parseInt(L_InP.getText());
            int pnb=Integer.parseInt(L_PnB.getText());
            
            if(itm <it ){
                it-=itm;
                DataHelper.QuickEditQuantity(it,P_TSearch.getText(),true);  Alerts.showAlert("تم التعديل",1);}
            else if(itm >it){
                itm-=it;
                DataHelper.QuickEditQuantity(itm,P_TSearch.getText(),false);  Alerts.showAlert("تم التعديل",1);
            }else     Alerts.showAlert("لا يوجد تغير فى الكمية",2);
            P_Search();
            quann();    
            }catch(NumberFormatException e){Alerts.showAlert("لقد ادخلت قيمة خاطئة",3);}
        }
        else {
       Alerts.showAlert("برجاء ادخال عدد الكراتين",3);
       }
    }
    
    
    private void editPacketProduct(){
        if (!P_BQuantity.getText().equals(""))
        {
            try{
            int pa=Integer.parseInt(P_BQuantity.getText());

            DataHelper.getQuanDetails(P_TSearch.getText(), L_InP, L_PnB);
            int inp=Integer.parseInt(L_InP.getText());
            int pnb=Integer.parseInt(L_PnB.getText());
            
            if(pkt < pa){
                pa-=pkt;
                DataHelper.QuickEditQuantity(pa*inp,P_TSearch.getText(),true); Alerts.showAlert("تم التعديل",1);}
            else if(pkt > pa){
                pkt-=pa;
                DataHelper.QuickEditQuantity(pkt*inp,P_TSearch.getText(),false); Alerts.showAlert("تم التعديل",1);
            }else     Alerts.showAlert("لا يوجد تغير فى الكمية",1);
            P_Search();
            quann();    
            }catch(NumberFormatException e){Alerts.showAlert("لقد ادخلت قيمة خاطئة",3);}
        }
        else {
       Alerts.showAlert("برجاء ادخال عدد الباكيت",3);
       }
    }
    
    
    private void editBoxesProduct(){
        if (!P_CQuantity.getText().equals(""))
        {
            try{
           
            int bo=Integer.parseInt(P_CQuantity.getText());

            DataHelper.getQuanDetails(P_TSearch.getText(), L_InP, L_PnB);
            int inp=Integer.parseInt(L_InP.getText());
            int pnb=Integer.parseInt(L_PnB.getText());
            
            if(bx < bo){
                    bo-=bx;
                    DataHelper.QuickEditQuantity(bo*pnb*inp,P_TSearch.getText(),true); Alerts.showAlert("تم التعديل",1);
              }
              else if(bx > bo){
                    bx-=bo;
                    DataHelper.QuickEditQuantity(bx*pnb*inp,P_TSearch.getText(),false); Alerts.showAlert("تم التعديل",1);
              }else     Alerts.showAlert("لا يوجد تغير فى الكمية",1);
            P_Search();
            quann();    
            }catch(NumberFormatException e){Alerts.showAlert("لقد ادخلت قيمة خاطئة",3);}
        }
        else {
       Alerts.showAlert("برجاء ادخال عدد الوحدات",3);
       }
    }
    
    
    private void Edit_Product(){
       if (!P_CQuantity.getText().equals("") && !P_BQuantity.getText().equals("") && !P_UQuantity.getText().equals(""))
       {
            try{
                if(R_item.isSelected() || R_packet.isSelected() || R_box.isSelected() ){
                  int it=Integer.parseInt(P_UQuantity.getText());
                  int pa=Integer.parseInt(P_BQuantity.getText());
                  int bo=Integer.parseInt(P_CQuantity.getText());
                  
                  DataHelper.getQuanDetails(P_TSearch.getText(), L_InP, L_PnB);
                  int inp=Integer.parseInt(L_InP.getText());
                  int pnb=Integer.parseInt(L_PnB.getText());
                  if(R_item.isSelected()){
                        if(itm <it ){
                            it-=itm;
                            DataHelper.QuickEditQuantity(it,P_TSearch.getText(),true);  Alerts.showAlert("تم التعديل",1);}
                        else if(itm >it){
                            itm-=it;
                            DataHelper.QuickEditQuantity(itm,P_TSearch.getText(),false);  Alerts.showAlert("تم التعديل",1);
                        }else     Alerts.showAlert("لا يوجد تغير فى الكمية",1);
                  }
                  else if(R_packet.isSelected()){
                        if(pkt < pa){
                            pa-=pkt;
                            DataHelper.QuickEditQuantity(pa*inp,P_TSearch.getText(),true); Alerts.showAlert("تم التعديل",1);}
                        else if(pkt > pa){
                            pkt-=pa;
                            DataHelper.QuickEditQuantity(pkt*inp,P_TSearch.getText(),false); Alerts.showAlert("تم التعديل",1);
                        }else     Alerts.showAlert("لا يوجد تغير فى الكمية",1);
                  }
                  else if(R_box.isSelected() && Integer.parseInt(P_CQuantity.getText()) != 0 ){
                      if(bx < bo){
                            bo-=bx;
                            DataHelper.QuickEditQuantity(bo*pnb*inp,P_TSearch.getText(),true); Alerts.showAlert("تم التعديل",1);
                      }
                      else if(bx > bo){
                            bx-=bo;
                            DataHelper.QuickEditQuantity(bx*pnb*inp,P_TSearch.getText(),false); Alerts.showAlert("تم التعديل",1);
                      }else     Alerts.showAlert("لا يوجد تغير فى الكمية",1);
                  
                  }
                  P_Search();
                  quann();
              }
              else
                  Alerts.showAlert("لم يتم تحديد خلية محددة",3);
            }catch(NumberFormatException e){Alerts.showAlert("لقد ادخلت قيمة خاطئة",3);}
        }
       else {
       Alerts.showAlert("برجاء التأكد من ملىء الحقول المطلوبة ..",3);
       }
    }



    @FXML
    private void Edit_Product2(ActionEvent event) {
        this.editPacketProduct();
    }

    @FXML
    private void Edit_Product3(ActionEvent event) {
        this.editBoxesProduct();
    }

    @FXML
    private void editItemkey(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) 
             this.editItemProduct();
    }

    @FXML
    private void editpaketkey(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) 
             this.editPacketProduct();
    }

    @FXML
    private void editboxkey(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) 
             this.editBoxesProduct();
    }


}
/*try{
        if(event.getCode().equals(KeyCode.ENTER)) {
             this.Edit_Product();
        }
        
        
        /*
        else if(event.getCode().equals(KeyCode.ADD)){
            if(P_UQuantity.isFocused()){
                R_item.setSelected(true);
                
            }
            else if(P_BQuantity.isFocused()){
                R_packet.setSelected(true);
            }
            else if(P_CQuantity.isFocused()){
                R_box.setSelected(true);
                
            }
            selectRadio();
        }
        */ 