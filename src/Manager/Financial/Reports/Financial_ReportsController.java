
package Manager.Financial.Reports;

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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lolo
 */
public class Financial_ReportsController implements Initializable {
    HomeController x = new HomeController();
    
    @FXML
    private AnchorPane Financial_Reports;
    @FXML
    private Label financial;
    @FXML
    private Label F_date1;
    @FXML
    private Label F_date2;
    @FXML
    private JFXDatePicker F_Tdate1;
    @FXML
    private JFXDatePicker F_Tdate2;

    /**
     * Initializes the controller class.
     */
    DatabaseHandler databaseHandler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler=DatabaseHandler.getInstance();
    }    

    @FXML
    private void Expenses_Reports(ActionEvent event) throws FileNotFoundException, DocumentException {
         try {
         if (!F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") ){
            System.out.println("غلطططططط "); // yoooooooooour code
            expences();
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    @FXML
    private void invoices_Reports(ActionEvent event) throws FileNotFoundException, DocumentException {
         try {
         if (!F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") ){
            System.out.println("غلطططططط "); 
            //invoices(F_Tdate1.getValue().toString(),F_Tdate2.getValue().toString());
             invoices();
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    @FXML
    private void Daily_Report(ActionEvent event) {
         try {
         if (!F_Tdate1.getValue().equals("") &&!F_Tdate2.getValue().equals("") ){
            String da1=F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String da2=F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             
            String qOut1="SELECT DISTINCT sale_date FROM sales WHERE sale_date >= '"+da1+"' AND sale_date <= '"+da2+"'";
            
            
            
            
            /**********Create Document******************/
             Document document =new Document(PageSize.A4);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             PdfWriter.getInstance(document, new FileOutputStream("تقرير يومى"+ft.format(date)+".pdf"));
             document.open();
             /******************************************/
             Paragraph preface = new Paragraph();
             addEmptyLine(preface, 1);
             
             PdfPTable t = new PdfPTable(1);
             PdfPCell cell = new PdfPCell();
             Paragraph p=new Paragraph("تقرير يومى", normal);
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
             preface.add("----------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             PdfPTable table = new PdfPTable(7);
             table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
             
             
             
             /***Header of table*****/
             
             PdfPCell c1 = new PdfPCell(new Phrase("تاريخ اليوم",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("عدد مبيعات اليوم",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("اجمالى مبيعات اليوم",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("مصاريف المحل",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("مصاريف الشراء",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("اجمالى المصاريف",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             
             c1 = new PdfPCell(new Phrase("صافى الربح",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             
             table.setHeaderRows(1);
             /***********************/
            
            String d="";
            ResultSet rs_SalesDate=DatabaseHandler.getInstance().execQuery(qOut1);
            while(rs_SalesDate.next()){
                int contQuan=0;
                double TotalQuan=0;
                double Shop_Expenses=0;
                double buy_Exp=0;
                double total_Exp=0;
                double profit=0;
                d=rs_SalesDate.getString("sale_date");
                String qIN1="SELECT current_qty,cost FROM sales WHERE sale_date = '"+d+"'";
                String qIN2="SELECT e_cost FROM expenses WHERE exp_date = '"+d+"'";
                String qIN3="SELECT cost FROM buying WHERE buy_date = '"+d+"'";
                ResultSet rs_Sales=DatabaseHandler.getInstance().execQuery(qIN1)
                        ,rs_Exp=DatabaseHandler.getInstance().execQuery(qIN2)
                        ,rs_Buy=DatabaseHandler.getInstance().execQuery(qIN3);
                
                while(rs_Sales.next()){
                   contQuan+=rs_Sales.getInt("current_qty");
                   TotalQuan+=rs_Sales.getDouble("cost");
                }
                while(rs_Exp.next()){
                    Shop_Expenses+=rs_Exp.getDouble("e_cost");
                }
                while(rs_Buy.next()){
                    buy_Exp+=rs_Buy.getDouble("cost");
                }
                total_Exp=Shop_Expenses+buy_Exp;
                profit=TotalQuan-total_Exp;
                //cell 1
                c1 = new PdfPCell(new Phrase(d,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(contQuan+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 3
                c1 = new PdfPCell(new Phrase(TotalQuan+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 4
                c1 = new PdfPCell(new Phrase(Shop_Expenses+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 5
                c1 = new PdfPCell(new Phrase(buy_Exp+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 6
                c1 = new PdfPCell(new Phrase(total_Exp+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 7
                c1 = new PdfPCell(new Phrase(profit+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }
            
            document.add(table);
            // document.add(table2);

            ////////////////ِTo show that pdf is printed///////////////
            Alerts.showInfoAlert("تمت طباعة التقرير");
            document.close();
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 } catch (SQLException ex) {
            Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Manager_Home(ActionEvent event) {
        x.loadwindow(Financial_Reports, "/Manager/Main/Home.fxml");
    }

    @FXML
    private void Back(ActionEvent event) {
         x.loadwindow(Financial_Reports, "/Manager/Reports/Reports.fxml");
    }
    

/*******************************************************Reports Methods****************************************************/





    //private void invoices(String startDate ,String endDate) throws FileNotFoundException, DocumentException{
    private void invoices(){    

         try {    
             String da1=F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             
             String qOut1="SELECT DISTINCT sale_date FROM sales WHERE sale_date >= '"+da1+"' AND sale_date <= '"+da2+"'"; 
             
             /**********Create Document******************/
             Document document =new Document(PageSize.A4);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             try {
                 
                 PdfWriter.getInstance(document, new FileOutputStream("الفواتير"+ft.format(date)+".pdf"));
             } catch (DocumentException ex) {
                 Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
             }
             System.out.println("Writrer insrance Created");
             document.open();  // Open the document to append in it .
             System.out.println("Document Opened");
             /************Title of Document*************/
             
             Paragraph preface = new Paragraph();
             addEmptyLine(preface, 1);
             
             PdfPTable t = new PdfPTable(1);
             PdfPCell cell = new PdfPCell();
             Paragraph p=new Paragraph("فواتير اليوم", normal);
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
             preface.add("----------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             
             
             /************************Start of content*********/
             
             /*****Creat table has 4 column*******/
             PdfPTable table = new PdfPTable(6);
             table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
             preface.add("----------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             PdfPTable table2 = new PdfPTable(1);
             table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
             
             /***Header of table*****/
             PdfPCell c1 = new PdfPCell(new Phrase("الاسم",normal));
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
             c1 = new PdfPCell(new Phrase("تسلسل ",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             PdfPCell d = new PdfPCell(new Phrase("---------------------------------------------",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table2.addCell(d);
             table.setHeaderRows(1);
             
             /***********retrive data from database and but them in cells***************/
             try {
                 //int u=1;
                 //double y=rs.getDouble("sale_id");
                 String dat="";
                 ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
                 while(rs.next()){
//               double y=rs.getDouble("sale_id"); 
                    //                if (y==x){
                    
                    dat=rs.getString("sale_date");
                    String qIN1="SELECT * FROM sales WHERE sale_date = '"+dat+"'";
                    ResultSet rs_Sales=DatabaseHandler.getInstance().execQuery(qIN1);
                while(rs_Sales.next()){
                    String x1=rs_Sales.getString("product_name");
                    double x2=rs_Sales.getDouble("unit_price");
                    int x3=rs_Sales.getInt("current_qty");
                    String x4=rs_Sales.getString("qty_kind");
                    double x5=rs_Sales.getDouble("cost");
                    int x6=rs_Sales.getInt("sale_id");
                
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
                    c1 = new PdfPCell(new Phrase(x4,normal));
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
                    // Add table to document
                    //               c1 = new PdfPCell(new Phrase(o,normal));
                    //                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //                table2.addCell(c1);
                    //y++;
                 }}
                 preface.add("hhhhhhhhhhhhhhhlglgllglglglglg");
                 document.add(table);
                 // document.add(table2);
            
                /////////////////ِTo show that pdf is printed///////////////
                Alerts.showInfoAlert("تمت طباعة التقرير");

             } catch(Exception e){
                 System.out.println(e);
             }
             // close document
             document.close();
             System.out.println("Document Closed");
             
             
         } catch (DocumentException ex) {
             Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
         }


    }
    
    
    
    
    
    /**********************************************************************Expenses*****************************************************************/
    
    
    //String startDate ,String endDate,String name
    private void expences() throws FileNotFoundException, DocumentException{
        String qu="SELECT * FROM expenses Right OUTER JOIN buy_bills on  expenses.tab=buy_bills.tab ";  
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
                
        
         /**********Create Document******************/
        Document document =new Document(PageSize.A4); 
        System.out.println("Document Created");
        /***********Method to calculate Date******/
             Date date=new Date();
            SimpleDateFormat ft = 
            new SimpleDateFormat (" yyyy.MM.dd ");
            
        /***************The Name of Pdf************/
         try {
         
             PdfWriter.getInstance(document, new FileOutputStream("أجر الموظف"+ft.format(date)+".pdf"));
         } catch (DocumentException ex) {
             Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        System.out.println("Writrer insrance Created");
        document.open();  // Open the document to append in it .
        System.out.println("Document Opened");
        /************Title of Document*************/
      
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        
        PdfPTable t = new PdfPTable(1);
         PdfPCell cell = new PdfPCell();
        Paragraph p=new Paragraph("أجر الموظف", normal);
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
        preface.add("----------------------------------------------------------------------------------------------------------------------------------");
        addEmptyLine(preface, 2);
        document.add(preface);   // Add paragraph of name preface to document
        
        
        
        /************************Start of content*********/
 
        /*****Creat table has 4 column*******/
        PdfPTable table = new PdfPTable(5);
        table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
        
          /***Header of table*****/
        PdfPCell c1 = new PdfPCell(new Phrase("مصاريف المحل",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRunDirection(RUN_DIRECTION_RTL);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("مصاريف الشراء",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("الأجر ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("المصاريف ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("صافي المرتب ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        
        table.setHeaderRows(1);
        
        /***********retrive data from database and but them in cells***************/
        try {
            
            double z=0,y=0;
            while(rs.next()){
          
                double x1 =rs.getDouble("e_cost");
                double x2 =rs.getDouble("total_price");
                 z=z+x1;
                 y=y+x2;
                 System.out.println(x1+"  "+x2);
                }
            
                 
                
                
               
                //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(z),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                 //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(y),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                
                
                
            
            // Add table to document
            document.add(table);
 
            /////////////////ِTo show that pdf is printed///////////////
            Alert AT=new Alert(Alert.AlertType.INFORMATION);
            AT.setHeaderText(null);
            AT.setContentText("تمت طباعة التقرير");
            AT.showAndWait();
            
                   } catch(Exception e){
            System.out.println(e);
        }
        // close document
        document.close();
        System.out.println("Document Closed");


    }

    
    
    
    
    
    
    
    
    
    
    
    
    /**************************  addEmptyLine method to make an Empty line in document*************/
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    
    
    
    

    }
