
package Manager.Financial.Reports;

import Classes.Alerts;
import Manager.Main.HomeController;
import static Manager.Products.Reports.Products_ReportsController.normal;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
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
           if (F_Tdate1.getValue().compareTo(F_Tdate2.getValue())<0){   //To make sure that end date is after start date
            String da1=F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String da2=F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             
            String qOut1="SELECT DISTINCT sale_date FROM sales WHERE sale_date >= '"+da1+"' AND sale_date <= '"+da2+"'";
            
            
            
            
            /**********Create Document******************/
             Document document =new Document(PageSize.A3);
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
             preface.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             PdfPTable table = new PdfPTable(8);
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
             c1 = new PdfPCell(new Phrase("مرتجعات بقيمة",normal));
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
                double Recalls=0;
                double profit=0;
                d=rs_SalesDate.getString("sale_date");
                String qIN1="SELECT current_qty,cost FROM sales WHERE sale_date = '"+d+"'";
                String qIN2="SELECT e_cost FROM expenses WHERE exp_date = '"+d+"'";
                String qIN3="SELECT cost FROM buying WHERE buy_date = '"+d+"'";
                String qIN4="SELECT cost,source FROM recalls WHERE rec_date = '"+d+"'";
                ResultSet rs_Sales=DatabaseHandler.getInstance().execQuery(qIN1)
                        ,rs_Exp=DatabaseHandler.getInstance().execQuery(qIN2)
                        ,rs_Buy=DatabaseHandler.getInstance().execQuery(qIN3)
                        ,rs_rec=DatabaseHandler.getInstance().execQuery(qIN4);
                
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
                while(rs_rec.next()){
                    if(rs_rec.getString("source").equals("عميل")){
                        Recalls+=rs_rec.getDouble("cost");}
                }
                
                total_Exp=Shop_Expenses+buy_Exp+Recalls;
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
                c1 = new PdfPCell(new Phrase(Recalls+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 5
                c1 = new PdfPCell(new Phrase(Shop_Expenses+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 6
                c1 = new PdfPCell(new Phrase(buy_Exp+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 7
                c1 = new PdfPCell(new Phrase(total_Exp+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 8
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
           else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
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
              if (F_Tdate1.getValue().compareTo(F_Tdate2.getValue())<0){   //To make sure that end date is after start date
             String da1=F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             
             String qOut1="SELECT DISTINCT sale_date FROM sales WHERE sale_date >= '"+da1+"' AND sale_date <= '"+da2+"'"; 
             
             
             /**********Create Document******************/
             Document document =new Document(PageSize.A3);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             
             PdfWriter.getInstance(document, new FileOutputStream("فواتير المبيعات"+ft.format(date)+".pdf"));
             
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
             preface.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
             addEmptyLine(preface, 2);
             document.add(preface);   // Add paragraph of name preface to document
             
             
             
             /************************Start of content*********/
             
             
             
             /***********retrive data from database and but them in cells***************/
             try {
                 
                 //int u=1;
                 //double y=rs.getDouble("sale_id");
                 String dat="", tim="";
                 int id=0;
                 double TotalPrice=0;
                 double Paid=0;
                 double Remind=0;
                 ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
                 while(rs.next()){
                    dat=rs.getString("sale_date");
                    String qOut2="SELECT * FROM bills WHERE sale_date = '"+dat+"'";
                    ResultSet rs_S=DatabaseHandler.getInstance().execQuery(qOut2);
                    
                    
                    
                    PdfPTable t2 = new PdfPTable(1);
                    PdfPCell cell2 = new PdfPCell();
                    Paragraph p2=new Paragraph("فواتير بتاريخ  "+dat, normal);
                    p2.setAlignment(PdfPCell.ALIGN_LEFT);
                    cell2.addElement(p2);
                    cell2.setBorder(Rectangle.NO_BORDER);
                    cell2.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
                    t2.addCell(cell2);
                    document.add(t2);
                    
                    Paragraph preface4 = new Paragraph();
                    addEmptyLine(preface4, 2);
                    document.add(preface4);
                    while(rs_S.next()){
                        id=rs_S.getInt("sale_id");
                        TotalPrice=rs_S.getDouble("total_price");
                        Paid=rs_S.getDouble("paid_money");
                        Remind=rs_S.getDouble("reminder_money");
                        tim=rs_S.getString("t_time");
                        /********************************/
                                        //Paragraph preface5 = new Paragraph("فاتورة رقم : "+id + "         التوقيت:    "+tim,normal);   

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
             
                        
                        
                        /********************************/
                        String qIN1="SELECT * FROM sales WHERE sale_id= "+id+" AND sale_date = '"+dat+"'";
                        ResultSet rs_Sales=DatabaseHandler.getInstance().execQuery(qIN1);
                        while(rs_Sales.next()){
                            String x1=rs_Sales.getString("s_bar");
                            String x2=rs_Sales.getString("product_name");
                            double x3=rs_Sales.getDouble("unit_price");
                            int x4=rs_Sales.getInt("current_qty");
                            String x5=rs_Sales.getString("qty_kind");
                            double x6=rs_Sales.getDouble("cost");
                            

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
                        Paragraph p5=new Paragraph("الاجمالى :  "+TotalPrice+"    المدفوع:  "+Paid+"   الباقى:   "+Remind,normal);
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
                    
                    Paragraph preface3 = new Paragraph();
                    
                    addEmptyLine(preface3, 2); //add line space
                    preface3.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    addEmptyLine(preface3, 2);
                    document.add(preface3);   // Add paragraph of name preface to document
                    
                }
                
                /////////////////ِTo show that pdf is printed///////////////
                Alerts.showInfoAlert("تمت طباعة التقرير");

             } catch(Exception e){
                 System.out.println(e);
             }
             // close document
             document.close();
             System.out.println("Document Closed");
             
          
              }
        else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
            
         } catch (DocumentException ex) {
             Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (FileNotFoundException ex) {
            Logger.getLogger(Financial_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    
    
    
    
    /**********************************************************************Expenses*****************************************************************/
    
    
    //String startDate ,String endDate,String name
    private void expences() throws FileNotFoundException, DocumentException{
        
            Font normal=FontFactory.getFont("C:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, 16, Font.BOLD);
             if (F_Tdate1.getValue().compareTo(F_Tdate2.getValue())<0){   //To make sure that end date is after start date
            String da1=F_Tdate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String da2=F_Tdate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String qOut1="SELECT cost FROM sales WHERE sale_date >= '"+da1+"' AND sale_date <= '"+da2+"'"; 
            String qOut2="SELECT cost FROM buying WHERE buy_date >= '"+da1+"' AND buy_date <= '"+da2+"'"; 
            String qOut3="SELECT e_cost FROM expenses WHERE exp_date >= '"+da1+"' AND exp_date <= '"+da2+"'"; 
            String qOut4="SELECT cost,source FROM recalls WHERE rec_date >= '"+da1+"' AND rec_date <= '"+da2+"'"; 
            ResultSet rs1=DatabaseHandler.getInstance().execQuery(qOut1);
            ResultSet rs2=DatabaseHandler.getInstance().execQuery(qOut2);
            ResultSet rs3=DatabaseHandler.getInstance().execQuery(qOut3);
            ResultSet rs4=DatabaseHandler.getInstance().execQuery(qOut4);
            double TotalSales=0,TotalBuying=0,Expenses=0,Profit,Recalls=0;
        
         /**********Create Document******************/
        Document document =new Document(PageSize.A4); 
        System.out.println("Document Created");
        /***********Method to calculate Date******/
             Date date=new Date();
            SimpleDateFormat ft = 
            new SimpleDateFormat (" yyyy.MM.dd ");
            
        /***************The Name of Pdf************/
         try {
         
                 PdfWriter.getInstance(document, new FileOutputStream("ارباح شامل"+ft.format(date)+".pdf"));
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
        Paragraph p=new Paragraph("ارباح شامل/ مصروفات ", normal);
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
        addEmptyLine(preface, 4);
        document.add(preface);   // Add paragraph of name preface to document
        
        
        
        /************************Start of content*********/
 
        
        PdfPTable t5 = new PdfPTable(1);
        PdfPCell cell5 = new PdfPCell();
        Paragraph p5=new Paragraph("الفترة من  :  "+da1+"    الى:  "+da2,normal);
        p5.setAlignment(PdfPCell.ALIGN_LEFT);
        cell5.addElement(p5);
        cell5.setBorder(Rectangle.NO_BORDER);
        cell5.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
        t5.addCell(cell5);
        addEmptyLine(p5, 2);
        document.add(t5);

        
        Paragraph preface2 = new Paragraph();
        preface2.setAlignment(Element.ALIGN_RIGHT);
        //preface2.add("--------------------------------------------------              ");
        addEmptyLine(preface2, 3);
        document.add(preface2);
        
        /***********retrive data from database and but them in cells***************/
        try {
            System.out.println("Try");
            while(rs1.next()){
                    TotalSales+=rs1.getDouble("cost");
                    System.out.print(1);
            }
             while(rs2.next()){
                    TotalBuying+=rs2.getDouble("cost");
                    System.out.print(2);
            }
           
             while(rs3.next()){
                    Expenses+=rs1.getDouble("e_cost");
                    System.out.print(3);
            }
            while(rs4.next()){
                    if(rs4.getString("source").equals("عميل")){
                        Recalls+=rs4.getDouble("cost");
                    System.out.print(4);
                    }
           
            }
            
            Profit=TotalSales-(TotalBuying+Expenses+Recalls);
        /**************************************************************************/    
            System.out.println(TotalSales+" "+TotalBuying+" "+Expenses+" "+Profit+" "+normal);
            PdfPTable t3 = new PdfPTable(1);
            PdfPCell cell3 = new PdfPCell();
            Paragraph p3=new Paragraph("اجمالى قيمة المبيعات:    "+TotalSales +"\n\n"+
                    "اجمالى قيمة المصروفات:     "+(TotalBuying+Expenses)+"\n\n"+
                    "صافى الربح:     "+Profit,normal);
            p3.setAlignment(PdfPCell.ALIGN_LEFT);
            cell3.addElement(p3);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell3.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
            t3.addCell(cell3);
            document.add(t3);
            
            /////////////////ِTo show that pdf is printed///////////////
            Alerts.showInfoAlert("تمت طباعة التقرير");
         
            

         
         
        } catch(Exception e){
            System.out.println(e);
        }
            
        // close document
        document.close();
        System.out.println("Document Closed");
    
              } else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
             }

    
    
    
    
    
    
    
    
    
    
    
    
    /**************************  addEmptyLine method to make an Empty line in document*************/
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    
    
    
    

    }
