/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager.Employee.Reports;

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
import com.jfoenix.controls.JFXComboBox;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author lolo
 */
public class Employee_ReportsController implements Initializable {
  
    HomeController x = new HomeController();
    
    @FXML
    private AnchorPane E_Reports;
    @FXML
    private Label E_lable_name;
    @FXML
    private Label E_label_date1;
    @FXML
    private Label E_label_date2;
    @FXML
    private JFXDatePicker E_field_date1;
    @FXML
    private JFXDatePicker E_field_date2;
     @FXML
    private JFXComboBox<String> E_cname;

    /**
     * Initializes the controller class.
     */
    DatabaseHandler databaseHandler;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       databaseHandler = DatabaseHandler.getInstance();
       
       fillComboBox(E_cname);
       
       
       
       
       
       
    }
     

    @FXML
    private void Salary_Report(ActionEvent event) throws FileNotFoundException, DocumentException {
       try {
         if (!E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !E_cname.getValue().equals("")){
            System.out.println("غلطططططط555 "); // yoooooooooour code
            
            this.employeeSalary(E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), E_cname.getValue());
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    @FXML
    private void Purchases_Report(ActionEvent event) throws FileNotFoundException, DocumentException {
        try {
         if (!E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !E_cname.getValue().equals("")){
            System.out.println("غلطططططط8888 "); // yoooooooooour code
            this.employeeExpense(E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), E_cname.getValue());
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
        
         
        
    }

    @FXML
    private void Attendance_Report(ActionEvent event) throws FileNotFoundException, DocumentException {
        try {
         if (!E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") && !E_cname.getValue().equals("")){
            System.out.println("غلطططططط "); // yoooooooooour code
              this.attendance();
         }
         }
        catch(NullPointerException e){
                 Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
                 }
    }

    @FXML
    private void Manager_Home(ActionEvent event) {
        x.loadwindow(E_Reports, "/Manager/Main/Home.fxml");
    }

    @FXML
    private void Back(ActionEvent event) {
        x.loadwindow(E_Reports, "/Manager/Reports/Reports.fxml");
    }
    


public static void fillComboBox(ComboBox C1){
        ObservableList<String> list = FXCollections.observableArrayList();
        String qu = "SELECT emp_name FROM employee1";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String Ename=rs.getString("emp_name");
                list.add(Ename);
            }
        } catch (SQLException ex) {
            Alerts.showInfoAlert("لا يوجد موردين");
        }
        C1.setItems(list);
    }
 











    














 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*******************************************  Report   Implementation ******************************************************************/
    



/******************************************* Attendance******************************************************************/




    @FXML
    private void attendance () {
         try {
     //    if (!E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("") &&!E_field_date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals("")  ){
              if (E_field_date1.getValue().compareTo(E_field_date2.getValue())<0){   //To make sure that end date is after start date          
             
              String da1=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String emp=E_cname.getValue();
             //String qOut1="SELECT * FROM employee2 RIGHT OUTER JOIN employee1 on  employee1.emp_id = employee2.emp_id   WHERE (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') AND (emp_name='"+sup+"') "; 
             String qOut1="SELECT emp_date FROM emp_att WHERE emp_name='"+emp+"' AND (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') "; 
             
             /**********Create Document******************/
             Document document =new Document(PageSize.A3);
             System.out.println("Document Created");
             /***********Method to calculate Date******/
             Date date=new Date();
             SimpleDateFormat ft =
                     new SimpleDateFormat (" yyyy.MM.dd ");
             
             /***************The Name of Pdf************/
             
             PdfWriter.getInstance(document, new FileOutputStream("حضور موظف"+ft.format(date)+".pdf"));
             
             System.out.println("Writrer insrance Created");
             document.open();  // Open the document to append in it .
             System.out.println("Document Opened");
             /************Title of Document*************/
             
             Paragraph preface = new Paragraph();
             addEmptyLine(preface, 1);
             
             PdfPTable t = new PdfPTable(1);
             PdfPCell cell = new PdfPCell();
             Paragraph p=new Paragraph("حضور موظف", normal);
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
             
             
        
             Paragraph preface2 = new Paragraph();
             addEmptyLine(preface2, 2);
             document.add(preface2);
             
             
             PdfPTable table = new PdfPTable(4);
             table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
             
             
             
             /***Header of table*****/
             
             PdfPCell c1 = new PdfPCell(new Phrase("تاريخ اليوم",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("ميعاد الحضور",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("ميعاد الانصراف",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             c1.setRunDirection(RUN_DIRECTION_RTL);
             table.addCell(c1);
             c1 = new PdfPCell(new Phrase("عدد الساعات",normal));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             
             
             table.setHeaderRows(1);
             /***********************/
            
             
             
                         
            ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
            
            String dat="";
             while(rs.next()){
                dat=rs.getString("emp_date");
                String start="";
                String end="";
                int hours=0;
                
                String qu1="SELECT emp_start_time FROM emp_att WHERE emp_date='"+dat+"' AND emp_name='"+emp+"'";
                ResultSet rs1=DatabaseHandler.getInstance().execQuery(qu1);
                if(rs1.next()) start=rs1.getString("emp_start_time");
                
                String qu2="SELECT emp_start_time FROM emp_left WHERE emp_date='"+dat+"' AND emp_name='"+emp+"'";
                ResultSet rs2=DatabaseHandler.getInstance().execQuery(qu2);
                if(rs2.next()) end=rs2.getString("emp_start_time");
                
//                String qu3="SELECT emp_hours FROM emp_hours WHERE emp_date='"+dat+"' AND emp_name='"+emp+"'";
//                ResultSet rs3=DatabaseHandler.getInstance().execQuery(qu3);
//                if(rs3.next()) hours=rs3.getInt("emp_hours");
//                
                int hour=difff(start, end);
                
                //cell 1
                c1 = new PdfPCell(new Phrase(dat,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 1
                c1 = new PdfPCell(new Phrase(start,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(end,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                //cell 5
                c1 = new PdfPCell(new Phrase(hour+"",normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                

               
            }
            document.add(table); 
            ////////////////ِTo show that pdf is printed///////////////
            Alerts.showInfoAlert("تمت طباعة التقرير");
            document.close();
         
         
         
//        catch(NullPointerException e){
//                // Alerts.showErrorAlert("برجاءالتأكد من  ملىء جميع الحقول المطلوبة");
              }        
else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
         

                 } catch (DocumentException ex) { 
            Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    
    











    
//    private void attendance(String startDate ,String endDate) throws FileNotFoundException, DocumentException{
//        String qu="SELECT * FROM employee1  RIGHT OUTER JOIN employee2 on  employee1.emp_id = employee1.emp_id BETWEEN '"+startDate+"'AND '"+endDate+"'";  
//        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
//                
//        
//         /**********Create Document******************/
//        Document document =new Document(PageSize.A4); 
//        System.out.println("Document Created");
//        /***********Method to calculate Date******/
//             Date date=new Date();
//            SimpleDateFormat ft = 
//            new SimpleDateFormat (" yyyy.MM.dd ");
//            
//        /***************The Name of Pdf************/
//         try {
//         
//             PdfWriter.getInstance(document, new FileOutputStream("مصاريف الموظف "+ft.format(date)+".pdf"));
//         } catch (DocumentException ex) {
//             Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
//         }
//        System.out.println("Writrer insrance Created");
//        document.open();  // Open the document to append in it .
//        System.out.println("Document Opened");
//        /************Title of Document*************/
//      
//        Paragraph preface = new Paragraph();
//        addEmptyLine(preface, 1);
//        
//        PdfPTable t = new PdfPTable(1);
//         PdfPCell cell = new PdfPCell();
//        Paragraph p=new Paragraph("مشتريات الموظف", normal);
//        p.setAlignment(PdfPCell.ALIGN_CENTER);
//        cell.addElement(p);
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
//        t.addCell(cell);
//        document.add(t);
//        
//       /************Date  of Document*************/
//        preface.add(new Paragraph(
//                "" + ft.format(date),normal));
//                
//        addEmptyLine(preface, 1); //add line space
//        preface.add("----------------------------------------------------------------------------------------------------------------------------------");
//        addEmptyLine(preface, 2);
//        document.add(preface);   // Add paragraph of name preface to document
//        
//        
//        
//        /************************Start of content*********/
// 
//        /*****Creat table has 4 column*******/
//        PdfPTable table = new PdfPTable(4);
//        table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
//        
//          /***Header of table*****/
//        PdfPCell c1 = new PdfPCell(new Phrase("التاريخ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        c1.setRunDirection(RUN_DIRECTION_RTL);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("توقيت الحضور ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("توقيت الانصراف ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//         c1 = new PdfPCell(new Phrase("عدد ساعات العمل ",normal));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        
//        table.setHeaderRows(1);
//        
//        /***********retrive data from database and but them in cells***************/
//        try {
//            while(rs.next()){
//          
//                String x1=rs.getString("emp_name"); //emp_finish_time
//                 String x2=rs.getString("emp_start_time");
//                 String x3=rs.getString("emp_finish_time");
//                int x4=rs.getInt("emp_hours ");
//                
//                System.out.println(x1+"  "+x2);
//                //cell 1
//                c1 = new PdfPCell(new Phrase(x1,normal));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setRunDirection(RUN_DIRECTION_RTL);
//                table.addCell(c1);
//                //cell 2
//                c1 = new PdfPCell(new Phrase(x2,normal));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setRunDirection(RUN_DIRECTION_RTL);
//                table.addCell(c1);
//                //cell 3
//                c1 = new PdfPCell(new Phrase(x3,normal));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setRunDirection(RUN_DIRECTION_RTL);
//                table.addCell(c1);
//        
//                  //cell 4
//                c1 = new PdfPCell(new Phrase(String.valueOf(x4),normal));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(c1);
//                
//            }
//            // Add table to document
//            document.add(table);
// 
//            /////////////////ِTo show that pdf is printed///////////////
//            Alert AT=new Alert(Alert.AlertType.INFORMATION);
//            AT.setHeaderText(null);
//            AT.setContentText("تمت طباعة التقرير");
//            AT.showAndWait();
//            
//                   } catch(Exception e){
//            System.out.println(e);
//        }
//        // close document
//        document.close();
//        System.out.println("Document Closed");
//
//
//    }
//    
    
    /**************************************************************ُُExpenses********************************************************/
    
    
    
    
    private void employeeExpense(String startDate ,String endDate,String name) throws FileNotFoundException, DocumentException{
         if (E_field_date1.getValue().compareTo(E_field_date2.getValue())<0){   //To make sure that end date is after start date
             String da1=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String emp=E_cname.getValue();
             //String qOut1="SELECT * FROM employee2 RIGHT OUTER JOIN employee1 on  employee1.emp_id = employee2.emp_id   WHERE (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') AND (emp_name='"+sup+"') "; 
             String qOut1="SELECT * FROM personal_expences WHERE emp_name='"+emp+"' AND (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') "; 
             //String qOut1="SELECT * FROM personal_expences WHERE (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') AND (emp_name='"+emp+"') "; 
             //String qOut1="SELECT * FROM personal_expences "; 
             
             ResultSet rs=DatabaseHandler.getInstance().execQuery(qOut1);
                
        
         /**********Create Document******************/
        Document document =new Document(PageSize.A4); 
        System.out.println("Document Created");
        /***********Method to calculate Date******/
             Date date=new Date();
            SimpleDateFormat ft = 
            new SimpleDateFormat (" yyyy.MM.dd ");
            
        /***************The Name of Pdf************/
         try {
         
             PdfWriter.getInstance(document, new FileOutputStream("مصاريف الموظف "+ft.format(date)+".pdf"));
         } catch (DocumentException ex) {
             Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        System.out.println("Writrer insrance Created");
        document.open();  // Open the document to append in it .
        System.out.println("Document Opened");
        /************Title of Document*************/
      
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        
        PdfPTable t = new PdfPTable(1);
         PdfPCell cell = new PdfPCell();
        Paragraph p=new Paragraph("مصاريف الموظف", normal);
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
        
        
        PdfPTable t2 = new PdfPTable(1);
             PdfPCell cell2 = new PdfPCell();
             Paragraph p2=new Paragraph("الموظف :    "+emp, normal);
             p2.setAlignment(PdfPCell.ALIGN_LEFT);
             cell2.addElement(p2);
             cell2.setBorder(Rectangle.NO_BORDER);
             cell2.setRunDirection(RUN_DIRECTION_RTL); //To Make arabic works well
             t2.addCell(cell2);
             document.add(t2);
             
             Paragraph preface2 = new Paragraph();
             addEmptyLine(preface2, 2);
             document.add(preface2);
             
             
        
        
        /************************Start of content*********/
 
        /*****Creat table has 4 column*******/
        PdfPTable table = new PdfPTable(3);
        table.setRunDirection(RUN_DIRECTION_RTL);//To Make arabic works well
        
          /***Header of table*****/
        PdfPCell c1 = new PdfPCell(new Phrase("التاريخ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRunDirection(RUN_DIRECTION_RTL);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("القيمة",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("السبب ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
        
        /***********retrive data from database and but them in cells***************/
        try {
            while(rs.next()){
          
                String x1=rs.getString("emp_date"); //emp_finish_time
                double x2 =rs.getDouble("emp_price_product");
                 String x3=rs.getString("emp_product");
                
                
                System.out.println(x1+"  "+x2);
                //cell 1
                c1 = new PdfPCell(new Phrase(x1,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(x2),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                //cell 3
                c1 = new PdfPCell(new Phrase(x3,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
        
                
                
            }
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

}else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
    
    
    }
    
    
    
     /**************************************************************ُُSalary********************************************************/
    
    
    
    
    private void employeeSalary(String startDate ,String endDate,String name) throws FileNotFoundException, DocumentException{
        if (E_field_date1.getValue().compareTo(E_field_date2.getValue())<0){   //To make sure that end date is after start date
     
        //String qu="SELECT * FROM employee1 RIGJT OUTER JOIN employee2 on  employee1.emp_id = employee1.emp_id where emp_name = "+ name+" BETWEEN '"+startDate+"'AND '"+endDate+"'";  
         String da1=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String da2=E_field_date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String emp=E_cname.getValue();
             //String qOut1="SELECT * FROM employee2 RIGHT OUTER JOIN employee1 on  employee1.emp_id = employee2.emp_id   WHERE (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') AND (emp_name='"+sup+"') "; 
             String gqu="SELECT emp_salary_hours FROM employee1 WHERE emp_name='"+emp+"'";
             String qOut1="SELECT emp_hours FROM emp_hours WHERE emp_name='"+emp+"' AND (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') "; 
             String qOut2="SELECT emp_price_product FROM personal_expences WHERE emp_name='"+emp+"' AND (emp_date >= '"+da1+"' AND emp_date <= '"+da2+"') "; 
             
             ResultSet grs=DatabaseHandler.getInstance().execQuery(gqu);
             ResultSet rs1=DatabaseHandler.getInstance().execQuery(qOut1);
             ResultSet rs2=DatabaseHandler.getInstance().execQuery(qOut2);
                
        
         /**********Create Document******************/
        Document document =new Document(PageSize.A4); 
        System.out.println("Document Created");
        /***********Method to calculate Date******/
             Date date=new Date();
            SimpleDateFormat ft = 
            new SimpleDateFormat (" yyyy.MM.dd ");
            
        /***************The Name of Pdf************/
         try {
         
             PdfWriter.getInstance(document, new FileOutputStream("أجر الموظف "+emp+ft.format(date)+".pdf"));
         } catch (DocumentException ex) {
             Logger.getLogger(Employee_ReportsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        System.out.println("Writrer insrance Created");
        document.open();  // Open the document to append in it .
        System.out.println("Document Opened");
        /************Title of Document*************/
      
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        
        PdfPTable t = new PdfPTable(1);
         PdfPCell cell = new PdfPCell();
        Paragraph p=new Paragraph("أجر الموظف :"+emp, normal);
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
        
        
        /************************Start of content*********/
 
        /***********retrive data from database and but them in cells***************/
        try {
            double salaryPerHour=0,salary=0,expenses=0,netSalary=0;
            int workHours=0;
            if(grs.next())
                salaryPerHour=grs.getDouble("emp_salary_hours"); // الاجر للساعة
 
            while(rs1.next()){
                workHours+=rs1.getInt("emp_hours"); // عدد ساعات العمل
            }
            salary=salaryPerHour*workHours; // الاجر
            
            while(rs2.next()){
                expenses+=rs2.getDouble("emp_price_product"); // المصاريف الشخصية
            }
            
            netSalary=salary-expenses;
            
            
            PdfPTable t3 = new PdfPTable(1);
            PdfPCell cell3 = new PdfPCell();
            Paragraph p3=new Paragraph("  اجر الساعة:    "+ salaryPerHour+"\n\n"+"عدد ساعات العمل:    "+workHours +"\n\n"+
                    "المصاريف الشخصية:     "+expenses+"\n\n"+
                    "صافى المرتب:     "+netSalary,normal);
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

}  else {
              Alerts.showErrorAlert("تاريخ النهاية يسبق تاريخ البداية");
          }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**************************  addEmptyLine method to make an Empty line in document*************/
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

   private int difff(String st,String fh){
        double salary;
        String start = st;
        String finish = fh;
         long differenceCalc=0;

        try{SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
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
        
        System.out.println(h+"  "+s);
        //this.diff();
        return  h;
    }
    
    
    
    
    

    }

/*

        PdfPCell c1 = new PdfPCell(new Phrase("التاريخ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRunDirection(RUN_DIRECTION_RTL);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("عدد ساعات العمل",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("الأجر ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("المصاريف ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("صافي المرتب ",normal));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);


                //cell 1
                c1 = new PdfPCell(new Phrase(x4,normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(hours),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                 //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(salary),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(expenses),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
                 //cell 2
                c1 = new PdfPCell(new Phrase(String.valueOf(netSalary),normal));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setRunDirection(RUN_DIRECTION_RTL);
                table.addCell(c1);
        
                
                
            
            // Add table to document
            document.add(table);

*/