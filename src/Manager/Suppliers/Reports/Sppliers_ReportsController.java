
package Manager.Suppliers.Reports;

import Classes.Alerts;
import Manager.Main.HomeController;
import static Manager.Products.Reports.Products_ReportsController.normal;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
                       
          if (S_TData1.getValue().compareTo(S_TDate2.getValue())<0){   //To make sure that end date is after start date
             String da1=S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String sup=S_Cname.getValue();
             String qOut1="SELECT * FROM recalls WHERE (rec_date >= '"+da1+"' AND rec_date <= '"+da2+"') AND (source='"+sup+"')"; 
             
             /**********Create Document******************/
             Document document =new Document(PageSize.A3);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             
             PdfWriter.getInstance(document, new FileOutputStream("المرتجعات"+ft.format(date)+".pdf"));
             
             System.out.println("Writrer insrance Created");
             document.open();  // Open the document to append in it .
             System.out.println("Document Opened");
             /************Title of Document*************/
             
             Paragraph preface = new Paragraph();
             addEmptyLine(preface, 1);
             
             PdfPTable t = new PdfPTable(1);
             PdfPCell cell = new PdfPCell();
             Paragraph p=new Paragraph("المرتجعات", normal);
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
             preface.add("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             
             
             PdfPTable t2 = new PdfPTable(1);
             PdfPCell cell2 = new PdfPCell();
             Paragraph p2=new Paragraph("المصدر:    "+sup, normal);
             p2.setAlignment(PdfPCell.ALIGN_LEFT);
             cell2.addElement(p2);
             cell2.setBorder(Rectangle.NO_BORDER);
             cell2.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
             t2.addCell(cell2);
             document.add(t2);
             
             Paragraph preface2 = new Paragraph();
             addEmptyLine(preface2, 2);
             document.add(preface2);
             
             
             PdfPTable table = new PdfPTable(7);
             table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
             
             
             
             /***Header of table*****/
             
             PdfPCell c1 = new PdfPCell(new Phrase("تاريخ اليوم",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("باركود",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("اسم المنتج",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("السعر",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("الكمية",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("نوع الكمية",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("التكلفة",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             
             
             
             table.setHeaderRows(1);
             /***********************/
            
             
             
             
             
             
            ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
            
            
             while(rs.next()){
                String dat=rs.getString("rec_date");
                String bar=rs.getString("r_bar");
                String name=rs.getString("product_name");
                double price=rs.getDouble("unit_price");
                int quan=rs.getInt("current_qty");
                String kind=rs.getString("qty_kind");
                double cost=rs.getDouble("cost");
                String sor=rs.getString("source");
                
                //cell 1
                c1 = new PdfPCell(new Phrase(dat,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(bar,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 3
                c1 = new PdfPCell(new Phrase(name,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 4
                c1 = new PdfPCell(new Phrase(price+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 5
                c1 = new PdfPCell(new Phrase(quan+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 6
                c1 = new PdfPCell(new Phrase(kind+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 7
                c1 = new PdfPCell(new Phrase(cost+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            document.add(table); 
            ////////////////ِTo show that pdf is printed///////////////
            Alerts.showAlert("تمت طباعة التقرير",1);
            document.close();
         }else {
              Alerts.showAlert("تاريخ النهاية يسبق تاريخ البداية",3);
          }
         
         }
         }catch(NullPointerException e){
                 Alerts.showAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة",3);
                 } catch (DocumentException ex) { 
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    
    
    
    
    @FXML
    private void Suppliers_Invoices(ActionEvent event) {
        try {
         if (!S_TData1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!S_TDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !S_Cname.getValue().equals("") ){
            
             if (S_TData1.getValue().compareTo(S_TDate2.getValue())<0){   //To make sure that end date is after start date
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
             
             PdfWriter.getInstance(document, new FileOutputStream("فواتير الشراء"+ft.format(date)+".pdf"));
             
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
            String sup=S_Cname.getValue();
            String dat="", tim="";
            int id=0;
            double TotalPrice=0;
            while(rs.next()){
               dat=rs.getString("buy_date");
               String qOut2="SELECT * FROM buy_bills WHERE bill_date = '"+dat+"' AND supplier_name= '"+sup+"'" ;
               ResultSet rs_B=DatabaseHandler.getInstance().execQuery(qOut2);
               
               
               
               while(rs_B.next()){
                    
                    tim=rs_B.getString("t_time"); 
                    id=rs_B.getInt("bill_id");
                    TotalPrice=rs_B.getDouble("total_price");
                    PdfPTable t3 = new PdfPTable(1);
                    PdfPCell cell3 = new PdfPCell();
                    Paragraph p3=new Paragraph("فاتورة رقم : "+id + "         التوقيت:    "+tim,normal);
                    p3.setAlignment(PdfPCell.ALIGN_LEFT);
                    cell3.addElement(p3);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    cell3.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
                    t3.addCell(cell3);
                    document.add(t3);
                    Paragraph preface6 = new Paragraph();
                    addEmptyLine(preface6, 1);
                    document.add(preface6);
                        
                    
                    /*****Creat table has 4 column*******/
                    PdfPTable table = new PdfPTable(6);
                    table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
                               /***Header of table*****/
                    PdfPCell c1;
                    c1 = new PdfPCell(new Phrase("باركود ",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1= new PdfPCell(new Phrase("الاسم",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setRunDirection(RUN_DIRECTION_RTL);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase("السعر",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase("الكمية",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase("نوع الكمية",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase("التكلفة",normal));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    table.setHeaderRows(1);
             
                    String qIN1="SELECT * FROM buying WHERE buy_id= "+id+" AND buy_date = '"+dat+"'";
                    ResultSet rs_Buy=DatabaseHandler.getInstance().execQuery(qIN1);
                    while(rs_Buy.next()){
                        String x1=rs_Buy.getString("b_bar");
                        String x2=rs_Buy.getString("product_name");
                        double x3=rs_Buy.getDouble("unit_price");
                        int x4=rs_Buy.getInt("current_qty");
                        String x5=rs_Buy.getString("qty_kind");
                        double x6=rs_Buy.getDouble("cost");


                        //cell 1
                        c1 = new PdfPCell(new Phrase(x1,normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setRunDirection(RUN_DIRECTION_RTL);
                        table.addCell(c1);
                        //cell 2
                        c1 = new PdfPCell(new Phrase(String.valueOf(x2),normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                        //cell 3
                        c1 = new PdfPCell(new Phrase(String.valueOf(x3),normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                        //cell 4
                        c1 = new PdfPCell(new Phrase(x4+"",normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setRunDirection(RUN_DIRECTION_RTL);
                        table.addCell(c1);
                        //cell 5
                        c1 = new PdfPCell(new Phrase(String.valueOf(x5),normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                        //cell 5
                        c1 = new PdfPCell(new Phrase(String.valueOf(x6),normal));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                    }
                    document.add(table);

                    PdfPTable t5 = new PdfPTable(1);
                    PdfPCell cell5 = new PdfPCell();
                    Paragraph p5=new Paragraph("الاجمالى :  "+TotalPrice,normal);
                    p5.setAlignment(PdfPCell.ALIGN_LEFT);
                    cell5.addElement(p5);
                    cell5.setBorder(Rectangle.NO_BORDER);
                    cell5.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
                    t5.addCell(cell5);
                    addEmptyLine(p5, 2);
                    document.add(t5);

                    Paragraph preface2 = new Paragraph();
                    preface2.setAlignment(Element.ALIGN_RIGHT);
                    preface2.add("--------------------------------------------------                     ");
                    addEmptyLine(preface2, 1);
                    document.add(preface2);
               }
               
             
                 Alerts.showAlert("تمت طباعة التقرير",1);   
            }
             
             
             
             
             
             
             
             
               // close document
             document.close();
             System.out.println("Document Closed");
            
         }
             else {
              Alerts.showAlert("تاريخ النهاية يسبق تاريخ البداية",3);
          }
         }else
             Alerts.showAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة",3);
             
        }
        catch(NullPointerException e){
                 Alerts.showAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة",3);
                 } catch (DocumentException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sppliers_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
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
