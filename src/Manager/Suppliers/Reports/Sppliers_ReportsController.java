/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager.Suppliers.Reports;

import Classes.Alerts;
import Manager.Main.HomeController;
import static Manager.Products.Reports.Products_ReportsController.normal;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.RUN_DIRECTION_RTL;
import com.jfoenix.controls.JFXDatePicker;
import database.DataHelper;
import database.DatabaseHandler;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Walid
 */
public class Sppliers_ReportsController implements Initializable {
 
    HomeController x = new HomeController();
    
    @FXML
    private AnchorPane Suppliers_Reports;
    @FXML
    private Label S_name;
    @FXML
    private ComboBox<String> S_Cname;
    @FXML
    private Label S_Date1;
    @FXML
    private Label S_Date2;
    @FXML
    private JFXDatePicker S_TData1;
    @FXML
    private JFXDatePicker S_TDate2;
    DatabaseHandler databaseHandler;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler=DatabaseHandler.getInstance();
        S_Cname.setItems(DataHelper.checkDataSupp());
        
        // TODO
    }    


    @FXML
    private void Supplier_Returns(ActionEvent event) {
         try {
         if (!S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !S_Cname.getValue().equals("") ){
            
             
             String da1=S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             
             String qOut1="SELECT DISTINCT buy_date FROM buying WHERE buy_date >= '"+da1+"' AND buy_date <= '"+da2+"'"; 
             
             /**********Create Document******************/
             Document document =new Document(PageSize.A3);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             
             PdfWriter.getInstance(document, new FileOutputStream("الفواتير"+ft.format(date)+".pdf"));
             
             System.out.println("Writrer insrance Created");
             document.open();  // Open the document to append in it .
             System.out.println("Document Opened");
             /************Title of Document*************/
             
             Paragraph preface = new Paragraph();
             addEmptyLine(preface, 1);
             
             PdfPTable t = new PdfPTable(1);
             PdfPCell cell = new PdfPCell();
             Paragraph p=new Paragraph("فواتير الموردين", normal);
             p.setAlignment(PdfPCell.ALIGN_CENTER);
             cell.addElement(p);
             cell.setBorder(Rectangle.NO_BORDER);
             cell.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
             t.addCell(cell);
             document.add(t);
             
             /************Date  of Document*************/
             preface.add(new Paragraph(
                     "" + ft.format(date),normal));
             
             addEmptyLine(preface, 1); //add line space
             preface.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             
            ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
            String dat="", tim="";
            int id=0;
            double TotalPrice=0;
            while(rs.next()){
               dat=rs.getString("sale_date");
               String qOut2="SELECT * FROM buy_bills WHERE bill_date = '"+dat+"'";
               ResultSet rs_S=DatabaseHandler.getInstance().execQuery(qOut2);

            }
             
             
             
             
             
             
             
             
             
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 } catch (DocumentException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @FXML
    private void Supplier_Financial(ActionEvent event) {
        try {
         if (!S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !S_Cname.getValue().equals("") ){
            
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void Suppliers_Invoices(ActionEvent event) {
        try {
         if (!S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !S_Cname.getValue().equals("") ){
            
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void Manager_Home(ActionEvent event) {
        x.loadwindow(Suppliers_Reports,"/Manager/Main/Home.fxml");
    }

    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(Suppliers_Reports, "/Manager/Reports/Reports.fxml");

    }
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
