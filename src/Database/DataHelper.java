package database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Classes.*;
import com.jfoenix.controls.JFXComboBox;
import employees.sales.SalesController;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

public class DataHelper extends Database.Mdification{

    
    
    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************_______PRODUCTS_______**************************************************/
    /////leda add
    
    public static boolean insertNewProduct(Goods go) {
        data = new HashMap<>();
        data.put("pro_bar",go.getProductBarCode());
        data.put("pro_name", go.getProductName());
        data.put("pro_category", go.getProductCategory());
        data.put("pro_qty_item", go.getItemsInPacket());
        data.put("pro_qty_packet", go.getPacketsInBox());
        data.put("pro_box", go.getBox());
        data.put("pro_All_qty", go.getAllQuantity());
        data.put("pro_price_item", go.getItemPrice());
        data.put("pro_price_packet", go.getPacketPrice());
        data.put("pro_price_box", go.getBoxPrice());
        data.put("pro_minimum", go.getProductMinQuantity());
        if(InsertInfo("product", data))
            return true;
        else
            return false;
//            
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO product(pro_bar,pro_name,pro_category,"
//                    + "pro_qty_item,pro_qty_packet,pro_box,pro_All_qty,pro_price_item,pro_price_packet,"
//                    + "pro_price_box,pro_minimum) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
//            statement.setString(1, go.getProductBarCode());
//            statement.setString(2, go.getProductName());
//            statement.setString(3, go.getProductCategory());
//            statement.setInt(4, go.getItemsInPacket());
//            statement.setInt(5, go.getPacketsInBox());
//            statement.setString(6, go.getBox());
//            statement.setLong(7, go.getAllQuantity());
//            statement.setDouble(8, go.getItemPrice());
//            statement.setDouble(9, go.getPacketPrice());
//            statement.setDouble(10, go.getBoxPrice());
//            statement.setInt(11, go.getProductMinQuantity());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {}
//        return false;
    }
    
    public static boolean updateProductInfo(Goods go,String bar) {
        data = new HashMap<>();
        data.put("pro_bar", go.getProductBarCode());
        data.put("pro_name", go.getProductName());
        data.put("pro_category", go.getProductCategory());
        data.put("pro_qty_item", go.getItemsInPacket());
        data.put("pro_qty_packet", go.getPacketsInBox());
        data.put("pro_box", go.getBox());
        data.put("pro_All_qty", go.getAllQuantity());
        data.put("pro_price_item", go.getItemPrice());
        data.put("pro_price_packet", go.getPacketPrice());
        data.put("pro_price_box", go.getBoxPrice());
        data.put("pro_minimum", go.getProductMinQuantity());
        
        return UpdateInfo("product", data, "pro_bar", bar);
//        try {
//            
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "UPDATE product SET  pro_bar=?,pro_name=?,pro_category=?,"
//                            + "pro_qty_item=?,pro_qty_packet=?,pro_box=?,pro_All_qty=?,pro_price_item=?,"
//                            + "pro_price_packet=?,pro_price_box=?,pro_minimum=?  WHERE pro_bar= '"+bar+"' ");
//            
//            statement.setString(1, go.getProductBarCode());
//            statement.setString(2, go.getProductName());
//            statement.setString(3, go.getProductCategory());
//            statement.setInt(4, go.getItemsInPacket());
//            statement.setInt(5, go.getPacketsInBox());
//            statement.setString(6, go.getBox());
//            statement.setLong(7, go.getAllQuantity());
//            statement.setDouble(8, go.getItemPrice());
//            statement.setDouble(9, go.getPacketPrice());
//            statement.setDouble(10, go.getBoxPrice());
//            statement.setInt(11, go.getProductMinQuantity());
//            return statement.executeUpdate() > 0;
//        }
//        catch (SQLException ex) {}
//        return false;
    }

    public static boolean QuickEditQuantity(int quan,String bar,boolean supSum) {
        try {
            int alQ=0;
            String qu1 = "SELECT pro_All_qty FROM product WHERE pro_bar = '"+bar+"' ";
            ResultSet rs =DatabaseHandler.getInstance().execQuery(qu1);
            if(rs.next()) alQ=rs.getInt("pro_All_qty");
            if(supSum == true)
                alQ+=quan;
            else if(supSum == false)
                alQ=alQ-quan;
                
            String qu="UPDATE product SET  pro_All_qty="+alQ+" WHERE pro_bar = '"+bar+"' ";
            if(DatabaseHandler.getInstance().execAction(qu))
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean deleteProduct(Goods go) {
        return DeleteInfo("product", "pro_bar", go.getProductBarCode());
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM product WHERE pro_bar = ?");
//            
//            statement.setString(1, go.getProductBarCode());
//            return statement.executeUpdate() > 0;
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    
    public static void loadProductsData(TableView TV,TextField TF) {
        ObservableList<Goods> list = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM product";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("pro_name");
                String bar=rs.getString("pro_bar");
                String cate=rs.getString("pro_category");
                String sup=rs.getString("pro_supplier_name");
                int it=rs.getInt("pro_qty_item");
                int pa=rs.getInt("pro_qty_packet");
                String box=rs.getString("pro_box");
                double Pi=rs.getDouble("pro_price_item");
                double Pp=rs.getDouble("pro_price_packet");
                double Pb=rs.getDouble("pro_price_box");
                int mP=rs.getInt("pro_minimum");
                int aq=rs.getInt("pro_All_qty");
                list.add(new Goods(name, bar, cate, sup, it, pa,box, Pi, Pp, Pb, mP));
                list2.add(bar);
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
        TextFields.bindAutoCompletion(TF, list2);
    }
    public static ObservableList<Goods> autos(ObservableList<Goods> list){
        list.clear();
        String qu = "SELECT * FROM product";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("pro_name");
                String bar=rs.getString("pro_bar");
                String cate=rs.getString("pro_category");
                String sup=rs.getString("pro_supplier_name");
                int it=rs.getInt("pro_qty_item");
                int pa=rs.getInt("pro_qty_packet");
                String box=rs.getString("pro_box");
                double Pi=rs.getDouble("pro_price_item");
                double Pp=rs.getDouble("pro_price_packet");
                double Pb=rs.getDouble("pro_price_box");
                int mP=rs.getInt("pro_minimum");
                int aq=rs.getInt("pro_All_qty");
                list.add(new Goods(name, bar, cate, sup, it, pa,box, Pi, Pp, Pb, mP));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        return list;
    }
    
    public static void ProductQuantity(String bar,TextField TI,TextField TP,TextField TB,Label name){
        String qu = "SELECT pro_All_qty,pro_qty_item,pro_qty_packet,pro_name,pro_box FROM product WHERE pro_bar= '"+bar+"'";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        
        try {
            if(rs.next()){
                int  
                        packets=(rs.getInt("pro_All_qty")%(rs.getInt("pro_qty_item")*rs.getInt("pro_qty_packet")))/rs.getInt("pro_qty_item"),
                        boxes= (rs.getInt("pro_All_qty")/(rs.getInt("pro_qty_item")*rs.getInt("pro_qty_packet"))) ,
                        items=(rs.getInt("pro_All_qty")%(rs.getInt("pro_qty_item")*rs.getInt("pro_qty_packet")))%rs.getInt("pro_qty_item");
                TI.setText(items+"");
                //TP.setText( (rs.getInt("pro_All_qty")/rs.getInt("pro_qty_item")) + "");
                TP.setText( packets + "");
                if(rs.getString("pro_box").equals("يوجد")){
                    TB.setDisable(false);
                    TB.setText(boxes + "");}
                else if(!rs.getString("pro_box").equals("يوجد")){
                    TB.setText("0");
                    TB.setDisable(true);}
                name.setText(rs.getString("pro_name"));
            }
        } catch (SQLException ex) {
            exit();
        }
        
        
    }
    public static void getQuanDetails(String bar,Label linp,Label lpnb){
        String qu = "SELECT pro_qty_item,pro_qty_packet FROM product WHERE pro_bar= '"+bar+"'";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            if(rs.next()){
                linp.setText(rs.getInt("pro_qty_item")+"");
                lpnb.setText(rs.getInt("pro_qty_packet")+"");
            }
        } catch (SQLException ex) {
            exit();
        }
    }
    public static void getBarcodesInEditQuanPage(TextField TF){
        ObservableList<String> list = FXCollections.observableArrayList();
        String qu = "SELECT pro_bar FROM product";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            while(rs.next()){
                list.add(rs.getString("pro_bar"));
            }
            
        } catch (SQLException ex) {
            exit();
        }
        TextFields.bindAutoCompletion(TF, list);
    }
    
    
    
    
    
    
    public static void loadExpiredData(TableView TV,String dat) {
        ObservableList<Goods> list = FXCollections.observableArrayList();
        String qu = "SELECT pro_bar,pro_name,expire_date FROM product WHERE expire_date='"+dat+"'";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String x1 =rs.getString("pro_bar");
                String x2 =rs.getString("pro_name");
                String x3=rs.getString("expire_date");
                list.add(new Goods(x1, x2, x3));
                System.out.println(x1+" "+x2+"  "+x3);
                
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
    }
    
    /********************************************END OF PRODUCTS*****************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    
    
    
    
    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*****************************************_______EMPLOYEE_______*************************************************/
    
    public static boolean insertNewemployee(Employee emp) {

        //New
        data =new HashMap<>();
        data.put("emp_id",emp.getEmployeeId());
        data.put("emp_name",emp.getEmployeeName());
        data.put("emp_phone",emp.getEmployeePhone());
        data.put("emp_salary_hours",emp.getEmployeeSalaryHours());
        data.put("emp_address",emp.getEmployeeAddress());
        if(InsertInfo("employee1", data))
            return true;
        else
            return false;
        
        
        //Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO employee1(emp_id,emp_name,emp_phone,emp_salary_hours,emp_address) VALUES(?,?,?,?,?)");
//            statement.setString(1, emp.getEmployeeId());
//            statement.setString(2, emp.getEmployeeName());
//            statement.setString(3, emp.getEmployeePhone());
//            statement.setDouble(4, emp.getEmployeeSalaryHours());
//            statement.setString(5, emp.getEmployeeAddress());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//         System.out.print("Employee doesn't be inserted");
//         Alerts.showAlert("خطأ فى التسجيل .. ربما سجل هذا الموظف من قبل",3);
//        }
//        return false;
    }
    
    public static boolean updateEmployee(Employee E,String oldID )
    {
        data =new HashMap<>();
        data.put("emp_id",E.getEmployeeId() );
        data.put("emp_name",E.getEmployeeName() );
        data.put("emp_phone",E.getEmployeePhone() );
        data.put("emp_address",E.getEmployeeAddress() );
        data.put("emp_salary_hours",E.getEmployeeSalaryHours() );
        return UpdateInfo("Employee1", data, "emp_id", oldID);
//        try {
//            
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "UPDATE Employee1 SET  emp_id=?,emp_name=?,emp_phone=?,emp_address=?,emp_salary_hours= ? WHERE emp_id = '" +oldID+"'");
//            statement.setString(1, E.getEmployeeId());
//            statement.setString(2, E.getEmployeeName());
//            statement.setString(3, E.getEmployeePhone());
//            statement.setString(4, E.getEmployeeAddress());
//            statement.setDouble(5, E.getEmployeeSalaryHours());
//          
//            return statement.executeUpdate() > 0;
//        }
//        catch (SQLException ex) {
//            System.out.print("data does't update");
//        }
//        return false;
    }
    
        public static boolean deleteEmployee(Employee emp) {
        return DeleteInfo("employee1", "emp_id", emp.getEmployeeId());
//            try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM employee1 WHERE emp_id = ?");
//            
//            statement.setString(1, emp.getEmployeeId());
//            int res = statement.executeUpdate();
//            if (res == 1) {
//                return true;
//            }
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    
    public static boolean insertAttendence(Date d, String name,String id,Time start) {
        data = new HashMap<>();
        data.put("emp_date", d);
        data.put("emp_name", name);
        data.put("emp_id", id);
        data.put("emp_start_time", start);
        if(InsertInfo("emp_att", data))
            return true;
        else
            return false;
        //Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO emp_att(emp_date,emp_name,emp_id,emp_start_time) VALUES(?,?,?,?)");
//            statement.setDate(1,d);
//            statement.setString(2,name);
//            statement.setString(3,id);
//            statement.setTime(4,start);
//            
//            if(statement.executeUpdate() > 0){
//                System.out.println("att inserted 000000");
//                return true;}
//        } catch (SQLException ex) {
//            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
    }
    
    public static void checkattendance(String id,TextField fx,String d) {
        try {
            String qu="SELECT emp_start_time FROM emp_att WHERE emp_id ='"+id+"' AND emp_date='"+d+"'";
            ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
            if(rs.next())
                fx.setText(rs.getTime("emp_start_time")+"");
            else fx.setText("تسجيل حضور");
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean insertleft(Date d, String name,String id,Time end) {
        data=new HashMap<>();
        data.put("", d);
        data.put("", name);
        data.put("", id);
        data.put("", end);
        if(InsertInfo("emp_left", data))
            return true;
        else
            return false;
        //Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO emp_left(emp_date,emp_name,emp_id,emp_start_time) VALUES(?,?,?,?)");
//            statement.setDate(1,d);
//            statement.setString(2,name);
//            statement.setString(3,id);
//            statement.setTime(4,end);
//            if(statement.executeUpdate() > 0){
//                System.out.println("Left inserted 000000");
//                return true;}
//        } catch (SQLException ex) {
//            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
    }
    public static boolean insertWorkHours(Date d, String name,String id,int h) {
        data=new HashMap<>();
        data=new HashMap<>();
        data.put("", d);
        data.put("", name);
        data.put("", id);
        data.put("", h);
        if(InsertInfo("emp_hours", data))
            return true;
        else
            return false;
        //Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO emp_hours(emp_date,emp_name,emp_id,emp_hours) VALUES(?,?,?,?)");
//            statement.setDate(1,d);
//            statement.setString(2,name);
//            statement.setString(3,id);
//            statement.setInt(4,h);
//            if(statement.executeUpdate() > 0){
//                System.out.println("num of h = "+h);
//                return true;}
//        } catch (SQLException ex) {
//            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
    }
    public static void loadEmployeesData(TableView TV) {
        ObservableList<Employee> list = FXCollections.observableArrayList();
       // ObservableList<String> list2 = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM employee1";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("emp_name");
                String phone=rs.getString("emp_phone");
                String id=rs.getString("emp_id");
                String address=rs.getString("emp_address");
                double salary=rs.getDouble("emp_salary_hours");
                list.add(new Employee(id,name,phone,address,salary));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد موظفين",1);
        }
        TV.setItems(list);
        //TextFields.bindAutoCompletion(TF, list2);

    }
   ///////////////////////////////////////////////////////
public static boolean isEmployeeisEXits(String id) {
        try {
            String checkstmt = "SELECT emp_id FROM employee1 WHERE emp_id = '"+id+"' ";
            
            ResultSet rs=DatabaseHandler.getInstance().execQuery(checkstmt);
            
            if (rs.next())
                return true;
            else{
                System.out.println("hjkl;lkjhgfddfghjkl");
                return false;
            }} catch (SQLException ex) {
            
        }
        return false;
    }

  public static boolean insertNewPersonalExpences(Employee E)
    {
        data =new HashMap<>();
        data.put("emp_name", E.getEmployeeName());
        data.put("emp_id", E.getEmployeeId());
        data.put("emp_price_product", E.getEmployeeExpensesCost());
        data.put("emp_product", E.getEmployeeExpensesReason());
        data.put("emp_date", E.getDate());
        
        if(InsertInfo("personal_expences", data))
            return true;
        else
            return false;
        // Old
//        try{
//        PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO personal_expences (emp_name,emp_id,emp_price_product,emp_product,emp_date) VALUES(?,?,?,?,?)");
//        
//        statement.setString(1, E.getEmployeeName());
//        statement.setString(2, E.getEmployeeId());
//        statement.setDouble(3, E.getEmployeeExpensesCost());
//        statement.setString(4, E.getEmployeeExpensesReason());
//        statement.setDate(5, E.getDate());
//       
//        return statement.executeUpdate() > 0;
//        } catch (SQLException ex)
//        {
//        }
//        return false;
    }

    public static void loadpersonalExpensesData(TableView TV,String date,String id) {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM personal_expences WHERE emp_date = '"+date+"' AND emp_id='"+id+"'" ;
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                
                double x1 =rs.getDouble("emp_price_product");
                String x2 =rs.getString("emp_product");
                System.out.println("gggggggggggggggggggggggggggggggf");
                list.add(new Employee(x1,x2));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد مصاريفس",1);
        }
        
                System.out.println("fdffdfsdsd");
        TV.setItems(list);
    }

        

    
//    public static boolean isEmployeeRegistered(String employeeId) {
//        String query = "SELECT emp_id FROM employee2 WHERE emp_id='" + employeeId + "'";  //get username
//        try {
//      PreparedStatement preparedStatement = DatabaseHandler.getInstance().getConnection().prepareStatement(query);
//      preparedStatement.setString(1, employeeId);
//      ResultSet rs = preparedStatement.executeQuery() ;
//      if(rs.next()) {
//       return true ;
//      }else {
//       return false ;
//      }
//     } catch (SQLException e) {
//      e.printStackTrace();
//      return true ;
//     }
//       }
    /********************************************END OF EMPLOYEE*****************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
   ///////////////////////////////////////////////////////

        
        
        
    ///////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    /////////////////End employee///////////////////////////////
    ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    
    
    
    
    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*****************************************_______SUPPLIER_______*************************************************/
    //leda adds
    
    public static void checkDataSupNames(TextField TX){
        ObservableList<String> list = FXCollections.observableArrayList();
        list= FXCollections.observableArrayList();
        String qu="SELECT sup_company_name FROM suppliers1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            while(rs.next()){
                String suppliers=rs.getString("sup_company_name");
                list.add(suppliers);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(TX, list);
    }
    
    
    public static void fillSupplierWithInfoOfSupp(String Sname,TextField TSN,TextField Pho,TextField Sup,ComboBox C){ // 
        String qu="SELECT sup_name,sup_company_name,sup_category,sup_phone FROM suppliers1 WHERE sup_company_name='"+Sname+"'"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            if(rs.next()){
                TSN.setText(rs.getString("sup_company_name"));
                Pho.setText(rs.getString("sup_phone"));
                Sup.setText(rs.getString("sup_name"));
                C.setValue(rs.getString("sup_category"));
                
            System.out.println("gfgggXXXXXXXXXXXXgggggffgggggggggggg");
            }       
            System.out.println("gfggggggggggggggggggffgggggggggggg");
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Suppliers> autoserSup(ObservableList<Suppliers> list){
        list.clear();
        String qu = "SELECT * FROM suppliers1";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("sup_company_name");
                String pho=rs.getString("sup_phone");
                String sup=rs.getString("sup_name");
                list.add(new Suppliers(name, pho,sup));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("",1);
        }
        return list;
    }
    
    public static boolean insertNewSupplier(Suppliers s)
    {
        //New
        data =new HashMap<>();
        data.put("sup_name",s.getSalespersonName());
        data.put("sup_company_name",s.getSupplierName());
        data.put("sup_phone",s.getSupplierPhone());
        if(InsertInfo("suppliers1", data))
            return true;
        else
            return false;
        //Old   
//        
//        try{
//        PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO suppliers1 (sup_name,sup_company_name,sup_phone) VALUES(?,?,?)");
//        statement.setString(1, s.getSalespersonName());
//        statement.setString(2, s.getSupplierName());
//        statement.setString(3, s.getSupplierPhone());
//        
//        return statement.executeUpdate() > 0;
//        
//        } catch (SQLException ex)
//        {
//            System.out.print("Suppler doesn't insertsed");
//        }
//        return false;
    }
    
    public static boolean updateSupplier(Suppliers s ,String oldCompName)
    {
        data =new HashMap<>();
        data.put("sup_company_name", s.getSupplierName());
        data.put("sup_phone", s.getSupplierPhone());
        data.put("sup_name", s.getSalespersonName());
        return UpdateInfo("suppliers1", data, "sup_company_name", oldCompName);
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "UPDATE suppliers1 SET  sup_company_name=?,sup_phone=?,sup_name=? WHERE sup_company_name='"+oldCompName+"'");
//            
//            statement.setString(1, s.getSupplierName());
//            statement.setString(2, s.getSupplierPhone());
//            statement.setString(3, s.getSalespersonName());
//           
//            return statement.executeUpdate() > 0;
//        }
//        catch (SQLException ex) {
//        }
//        return false;
    }
    
    public static boolean deleteSupplier(Suppliers sup) {
        return DeleteInfo("suppliers1", "sup_company_name", sup.getSupplierName());
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM suppliers1 WHERE sup_company_name =?");
//            statement.setString(1, sup.getSupplierName());
//            int res = statement.executeUpdate();
//            if (res == 1) {
//                return true;
//            }
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    
    public static void loadSuppliersData(TableView TV) {
        ObservableList<Suppliers> list = FXCollections.observableArrayList();
       // ObservableList<String> list2 = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM suppliers1";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("sup_company_name");
                String phone=rs.getString("sup_phone");
                String sales_name=rs.getString("sup_name");
                list.add(new Suppliers(name,phone,sales_name));
             //   list2.add(phone);
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد موردين",1);
        }
        TV.setItems(list);
       // TextFields.bindAutoCompletion(TF, list2);
    }
    public static ObservableList<Suppliers> o(ObservableList<Suppliers> list){
        list.clear();
        String qu = "SELECT * FROM suppliers1";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String name=rs.getString("supplierName");
                String phone=rs.getString("supplierPhone");
                String sales_name=rs.getString("salespersonName");
               list.add(new Suppliers(name,phone,sales_name));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد موردين",1);
        }
        return list;
    }
    
//    public static void fillComboBox(ComboBox C1){
//        ObservableList<String> list = FXCollections.observableArrayList();
//        String qu = "SELECT emp_name FROM employee1";
//        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
//        try {
//            while (rs.next()) {
//                String Ename=rs.getString("emp_name");
//                list.add(Ename);
//            }
//        } catch (SQLException ex) {
//            Alerts.showInfoAlert("لا يوجد موردين");
//        }
//        C1.setItems(list);
//    }
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*****************************************END OF SUPPLIER********************************************************/
    
    
    
    
    
    
    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*****************************************_______SALES_______****************************************************/

    public static boolean insertNewSale(Sales sal){
        data =new HashMap<>();
        data.put("number", sal.getNumber());
        data.put("sale_id",sal.getSerial());
        data.put("sale_date",sal.getDate() );
        data.put("product_name",sal.getName());
        data.put("qty_kind",sal.getQuantityKind());
        data.put("unit_price",sal.getUintPrice());
        data.put("current_qty",sal.getCurrentQuantity());
        data.put("cost",sal.getCost());
        data.put("s_bar",sal.getBarcodfiled());
        if(InsertInfo("sales", data))
            return true;
        else
            return false;
        // Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO sales (number,sale_id,sale_date,product_name,qty_kind,unit_price,current_qty,cost,s_bar) VALUES(?,?,?,?,?,?,?,?,?)");
//            statement.setLong(1, sal.getNumber());
//            statement.setInt(2,sal.getSerial());
//            statement.setDate(3,sal.getDate() );
//            statement.setString(4,sal.getName());
//            statement.setString(5,sal.getQuantityKind());
//            statement.setDouble(6,sal.getUintPrice());
//            statement.setInt(7,sal.getCurrentQuantity());
//            statement.setDouble(8,sal.getCost());
//            statement.setString(9,sal.getBarcodfiled());
//            
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            
//        }
//        return false;
    }
    public static boolean insertNewBill(Sales sal){
        data =new HashMap<>();
        data.put("sale_id",sal.getSerial());
        data.put("sale_date",sal.getDate() );
        data.put("t_time",sal.getTime());
        data.put("total_price",sal.getTotalPrice());
        data.put("paid_money",sal.getPaid());
        data.put("reminder_money",sal.getReminderMoney());
        if(InsertInfo("bills", data))
            return true;
        else
            return false;
        
//        Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO bills (sale_id,sale_date,t_time,total_price,paid_money,reminder_money) VALUES(?,?,?,?,?,?)");
//            
//            statement.setInt(1,sal.getSerial());
//            statement.setDate(2,sal.getDate() );
//            statement.setTime(3, sal.getTime());
//            statement.setDouble(4,sal.getTotalPrice());
//            statement.setDouble(5,sal.getPaid());
//            statement.setDouble(6,sal.getReminderMoney());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            System.out.println( "@@@@@@@  " +sal.getTotalPrice()+" @@@@@@@");
//        }
//        return false;
    }
    /////////////////////////////////////
    ////////////////delete/////////////////////
    //////////////////////////////////////////
    public static boolean deleteSale(Sales sal) {
        return DeleteInfo("sales", "number", sal.getNumber());
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM sales WHERE number = ?");
//            statement.setLong(1, sal.getNumber());
//            int res = statement.executeUpdate();
//            if (res == 1) {
//                return true;
//            }
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    
    
    
    /////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ///////////////////////////////////////////////
    public static boolean deleteAllRowsInSalesTV(int serial) {
        return DeleteInfo("sales", "sale_id", serial);
//        String qu = "DELETE FROM sales WHERE sale_id ="+serial+""; //
//        if(DatabaseHandler.getInstance().execAction(qu)){
//            return true;
//        }
//        return false;
    }
    
    /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    public static int getLastSerialTodaySales(String D_date){
        String qu="SELECT sale_id FROM sales where sale_date = '"+D_date+"'"+" ORDER BY sale_id DESC LIMIT 1 ";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        int srl=0;
        try {
            if(rs.next()){
                srl=rs.getInt("sale_id")+1;
                System.out.println("llllllllllll");}
            else
           {
                srl=1;
                System.out.println("لسااااا");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return srl;
    }
    ///////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    public static long getLastOrderNumber(){
        String qu="SELECT number FROM sales ORDER BY number DESC LIMIT 1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        long num = 0;
        try {
            if(rs.next()){
                num=rs.getLong("number")+1;
                System.out.println("llllllllllll");}
            else
           {
                num=1;
                System.out.println("لسااااا");
//              Alerts.showInfoAlert("اول فواتير اليوم ..");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return num;
    }
                            /**************************************/
    public static void checkDataBar(TextField TX){
        ObservableList<String> list = FXCollections.observableArrayList();
        list= FXCollections.observableArrayList();
        String qu="SELECT pro_bar FROM product"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            while(rs.next()){
                String barcode=rs.getString("pro_bar");
                list.add(barcode);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(TX, list);
    }
    
    public static void fillSalesWithInfoOfProduct(String bar,Label PBar,Label PName,Label PPrice,Price pra,JFXComboBox T){ // 
        String qu="SELECT pro_bar,pro_name,pro_price_item,pro_price_packet,pro_price_box,pro_box FROM product WHERE pro_bar='"+bar+"'"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            if(rs.next()){
                PBar.setText(rs.getString("pro_bar"));
                PName.setText(rs.getString("pro_name"));
                PPrice.setText(rs.getDouble("pro_price_item")+"");
                pra.setItemPrice(rs.getDouble("pro_price_item"));
                pra.setPacketPrice(rs.getDouble("pro_price_packet"));
                pra.setBoxPrice(rs.getDouble("pro_price_box"));
                if(rs.getString("pro_box").equals("يوجد"))
                    T.setItems(FXCollections.observableArrayList("قطعة","علبة","كرتونة"));
                else
                    T.setItems(FXCollections.observableArrayList("قطعة","علبة"));
            }       
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean CheckQuan(String bar){
        String qu="SELECT pro_All_qty,pro_minimum FROM product WHERE pro_bar='"+bar+"'"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        int allQua = 0,minQua=0;
        try {
            if(rs.next()){
                allQua=rs.getInt("pro_All_qty");
                minQua=rs.getInt("pro_minimum");
            }       
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allQua <= minQua;
    }
    
    public static boolean InterAction_B_Sales__Products_addQuan(Label PBar,int XQuantity,String kquan){
        String bar=PBar.getText();
        String qu="SELECT pro_All_qty,pro_qty_item,pro_qty_packet FROM product WHERE pro_bar='"+bar+"'";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        int allQuan = 0,iteminbox=0,packetinbox=0,subQuan=0;
        String qu2="";
        try {
            if(rs.next()){
                allQuan=rs.getInt("pro_All_qty");
                iteminbox=rs.getInt("pro_qty_item");
                packetinbox=rs.getInt("pro_qty_packet");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(kquan.equals("قطعة")){
            if((allQuan-XQuantity)>=0){
                subQuan=allQuan-XQuantity;
                qu2="UPDATE product SET pro_All_qty= "+subQuan+" WHERE pro_bar = '"+bar+"'";
            if(DatabaseHandler.getInstance().execAction(qu2))
            return true;}
            else {Alerts.showAlert("لا توجد كمية كافية",3); 
            return false;}
        }
        else if(kquan.equals("علبة")){
            if((allQuan- (XQuantity*iteminbox))>=0){
                subQuan=allQuan- (XQuantity*iteminbox);
            qu2="UPDATE product SET pro_All_qty= "+subQuan+" WHERE pro_bar = '"+bar+"'";
            if(DatabaseHandler.getInstance().execAction(qu2))
            return true;}
            else{ Alerts.showAlert("لا توجد كمية كافية",3); return false;}
        }
        else{
            if((allQuan- (XQuantity*iteminbox*packetinbox) ) >= 0){
                subQuan=allQuan- (XQuantity*iteminbox*packetinbox);
            qu2="UPDATE product SET pro_All_qty= "+subQuan+" WHERE pro_bar = '"+bar+"'";
            if(DatabaseHandler.getInstance().execAction(qu2))
            return true;}
            else
            {Alerts.showAlert("لا توجد كمية كافية",3); return false;}
        }

        return false;
    }
    
    public static boolean InterAction_B_Sales__Products_DeleteQuan(String bar,int XQuantity,String kquan){
        String qu="SELECT pro_All_qty,pro_qty_item,pro_qty_packet FROM product WHERE pro_bar='"+bar+"'";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        int allQuan = 0,iteminbox=0,packetinbox=0,AddQuan=0;
        try {
            if(rs.next()){
                allQuan=rs.getInt("pro_All_qty");
                iteminbox=rs.getInt("pro_qty_item");
                packetinbox=rs.getInt("pro_qty_packet");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(kquan.equals("قطعة")){
            AddQuan=allQuan+XQuantity;
        }
        else if(kquan.equals("علبة")){
            AddQuan=allQuan+ (XQuantity*iteminbox);
        }
        else{
            AddQuan=allQuan+ (XQuantity*iteminbox*packetinbox);
        }
        
        String qu2="UPDATE product SET pro_All_qty= "+AddQuan+" WHERE pro_bar = '"+bar+"'";
        if(DatabaseHandler.getInstance().execAction(qu2))
            return true;
        
        return false;
    }
    
    
    
                        /***************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*public static boolean cleanDBEfficiently(){
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
                    "DELETE FROM sales WHERE number>1 and number<100000000000 ");
=======
>>>>>>> 505b484313aca1b58227f05bd083b7b3401771f7
            int res = statement.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {//    
<<<<<<< HEAD
=======
        }
        return false;
    }
    
    public static boolean deleteAllRowsInSalesTV(int serial) {
        String qu = "DELETE FROM sales WHERE sale_id ="+serial+""; //
        if(DatabaseHandler.getInstance().execAction(qu)){
            return true;
>>>>>>> 505b484313aca1b58227f05bd083b7b3401771f7
        }
        return false;
    }
    /*******************************************END OF SALES*********************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    
    
    
    
    
    
    /****************************************************************************************************************/
    /*****************************************________BUYING________*************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    
    public static boolean insertBuyGoods(Buying buy){
        data=new HashMap<>();
        
        data.put("number", buy.getNumber());
        data.put("buy_id",buy.getSerial());
        data.put("buy_date",buy.getDate() );
        data.put("product_name",buy.getName());
        data.put("qty_kind",buy.getQuantityKind());
        data.put("unit_price",buy.getUintPrice());
        data.put("current_qty",buy.getCurrentQuantity());
        data.put("cost",buy.getCost());
        data.put("b_bar",buy.getBarcodfiled());
        if(InsertInfo("buying", data))
            return true;
        else
            return false;
        // Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO buying (number,buy_id,buy_date,product_name,qty_kind,unit_price,current_qty,cost,b_bar) VALUES(?,?,?,?,?,?,?,?,?)");
//            statement.setLong(1, buy.getNumber());
//            statement.setInt(2,buy.getSerial());
//            statement.setDate(3,buy.getDate() );
//            statement.setString(4,buy.getName());
//            statement.setString(5,buy.getQuantityKind());
//            statement.setDouble(6,buy.getUintPrice());
//            statement.setInt(7,buy.getCurrentQuantity());
//            statement.setDouble(8,buy.getCost());
//            statement.setString(9,buy.getBarcodfiled());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            
//        }
//        return false;
    }
    
    public static boolean insertBuyGoodsNewBill(Buying buy){
        data=new HashMap<>();
  
        data.put("bill_id", buy.getSerial());
        data.put("bill_date",buy.getDate());
        data.put("t_time",buy.getTime());
        data.put("supplier_name",buy.getSupplier());
        data.put("total_price", buy.getTotalPrice());
        if(InsertInfo("buy_bills", data))
            return true;
        else
            return false;
        
//Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO buy_bills (bill_id,bill_date,t_time,supplier_name,total_price) VALUES(?,?,?,?,?)");
//            statement.setInt(1, buy.getSerial());
//            statement.setDate(2,buy.getDate());
//            statement.setTime(3,buy.getTime());
//            statement.setString(4,buy.getSupplier());
//            statement.setDouble(5, buy.getTotalPrice());
//            
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            
//        }
//        return false;
    }
    
    
    public static int getLastSerialTodayBuying(String D_date){
        String qu="SELECT buy_id FROM buying where buy_date = '"+D_date+"'"+" ORDER BY buy_id DESC LIMIT 1 ";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        int srl=0;
        try {
            if(rs.next()){
                srl=rs.getInt("buy_id")+1;
                System.out.println("llllllllllll");}
            else
           {
                srl=1;
                System.out.println("لسااااا");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return srl;
    }    
    
    public static long getLastOrderNumberBuying(){
        String qu="SELECT number FROM buying ORDER BY number DESC LIMIT 1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        long num = 0;
        try {
            if(rs.next()){
                num=rs.getLong("number")+1;
                System.out.println("llllllllllll");}
            else
           {
                num=1;
                System.out.println("لسااااا");
//              Alerts.showInfoAlert("اول فواتير اليوم ..");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return num;
    }
    public static boolean deleteBuyRow(Buying buy) {
        return DeleteInfo("buying", "number", buy.getNumber());
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM buying WHERE number = ?");
//
//            statement.setLong(1, buy.getNumber());
//            int res = statement.executeUpdate();
//            if (res == 1) {
//                return true;
//            }
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    
    public static ObservableList<String> checkDataSupp(){
        ObservableList<String> list = FXCollections.observableArrayList();
        list= FXCollections.observableArrayList();
        String qu="SELECT sup_company_name FROM suppliers1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            while(rs.next()){
                String barcode=rs.getString("sup_company_name");
                list.add(barcode);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public static boolean deleteAllRowsInBuyTV(int serial) {
        String qu = "DELETE FROM buying WHERE buy_id ="+serial+""; //
        if(DatabaseHandler.getInstance().execAction(qu)){
            return true;
        }
        return false;
    }
    
    /*******************************************END OF BUYING********************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/






    

    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /*************************************______DAMAGES______********************************************************/
    /****************************************************************************************************************/    
    
    public static boolean insertDamages(Common_Properties dam){
        data=new HashMap<>();
        
        data.put("dam_date",dam.getDate());
        data.put("t_time",dam.getTime());
        data.put("d_bar", dam.getBarcodfiled());
        data.put("product_name",dam.getName());
        data.put("unit_price", dam.getUintPrice());
        data.put("qty_kind",dam.getQuantityKind());
        data.put("current_qty", dam.getCurrentQuantity());
        data.put("cost", dam.getCost());
        data.put("number", dam.getNumber());      
        if(InsertInfo("damages", data))
            return true;
        else
            return false;
        
        //Old
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO damages (dam_date,t_time,d_bar,product_name,unit_price,qty_kind,current_qty,cost,number) VALUES(?,?,?,?,?,?,?,?,?)");
//            statement.setDate(1,dam.getDate());
//            statement.setTime(2,dam.getTime());
//            statement.setString(3, dam.getBarcodfiled());
//            statement.setString(4,dam.getName());
//            statement.setDouble(5, dam.getUintPrice());
//            statement.setString(6,dam.getQuantityKind());
//            statement.setDouble(7, dam.getCurrentQuantity());
//            statement.setDouble(8, dam.getCost());
//            statement.setLong(9, dam.getNumber());
//            
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            
//        }
//        return false;
    }

    


//    public static boolean insertNewPersonalExpences(Employee E)
//    {
//        try{
//        PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO personal_expences (emp_product,emp_price_product) VALUES(?,?)");
//        statement.setDouble(1, E.getEmployeeExpensesCost());
//        statement.setString(2, E.getEmployeeExpensesReason());
//       
//        return statement.executeUpdate() > 0;
//        } catch (SQLException ex)
//        {
//        }
//        return false;
//    }
//
//    public static void loadpersonalExpensesData(TableView TV,String date) {
//        ObservableList<Employee> list = FXCollections.observableArrayList();
//        list.clear();
//        String qu = "SELECT * FROM personal_expences WHERE emp_date = '"+date+"'";
//        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
//        try {
//            while (rs.next()) {
//                
//                double x1 =rs.getDouble("emp_price_product");
//                String x2 =rs.getString("emp_product");
//                
//                list.add(new Employee(x1,x2));
//            }
//        } catch (SQLException ex) {
//            Alerts.showInfoAlert("لا يوجد مصاريفس");
//        }
//        TV.setItems(list);
//    }
//


    


    public static void loadDamageData(TableView TV,String dat) {
        ObservableList<Common_Properties> list = FXCollections.observableArrayList();
//        list.clear();
        String qu = "SELECT d_bar FROM damages";
        //String qu = "SELECT * FROM damages WHERE dam_date ='"+dat+"'";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            
            while (rs.next()) {
                String x1 =rs.getString("d_bar");
                String x2 =rs.getString("product_name");
                double x3=rs.getDouble("unit_price");
                int x4=rs.getInt("current_qty");
                String x5 =rs.getString("qty_kind");
                double x6 =rs.getDouble("cost");
                Date x7=rs.getDate("dam_date");
                Time x8=rs.getTime("t_time");
                long x9=rs.getLong("number");
                System.out.println("_______________+ "+x1+x2+x3+x4);
                list.add(new Common_Properties(x1,x2,x3,x4,x5,x6,x7,x8,x9));
                
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
    }

        public static long getLastOrderNumberDamage(){
        String qu="SELECT number FROM damages ORDER BY number DESC LIMIT 1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        long num = 0;
        try {
            if(rs.next()){
                num=rs.getLong("number")+1;
                System.out.println("llllllllllll");}
            else
           {
                num=1;
                System.out.println("لسااااا");
//              Alerts.showInfoAlert("اول فواتير اليوم ..");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return num;
    }
        
        public static boolean deletedamage(long sal) {
            return DeleteInfo("damages", "number", sal);
//            String qu="DELETE FROM damages WHERE number = "+sal+"";
//            return DatabaseHandler.getInstance().execAction(qu);
        }
    /*******************************************END OF DAMAGES*******************************************************/

    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    
    
    
    
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    /******************************************_______EXPENSES_______************************************************/
    public static boolean insertNewExpences(Expences E)
    {
        data=new HashMap<>();
       
        data.put("exp_date", E.getDate());
        data.put("e_reason", E.getReason());
        data.put("e_cost", E.getCost());
        if(InsertInfo("expenses", data))
            return true;
        else
            return false;
        // Old
//        try{
//        PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO expenses (exp_date,e_reason,e_cost) VALUES(?,?,?)");
//        statement.setDate(1, E.getDate());
//        statement.setString(2, E.getReason());
//        statement.setDouble(3, E.getCost());
//
//        return statement.executeUpdate() > 0;
//        } catch (SQLException ex)
//        {
//        }
//        return false;
    }

    public static void loadExpensesData(TableView TV,String dat) {
        ObservableList<Expences> list = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM expenses WHERE exp_date = '"+dat+"'";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                
                double x1 =rs.getDouble("e_cost");
                String x2 =rs.getString("e_reason");
                
                list.add(new Expences(x1,x2));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
    }

    /****************************************END OF EXPENSES*********************************************************/
    /****************************************************************************************************************/
    /****************************************************************************************************************/
    
    
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /**************************************______RECALLS______*****************************************************/
    public static boolean insertNewRecall(Recalls R)
    {
        data =new HashMap<>();
        data.put("rec_date", R.getDate());
        data.put("t_time", R.getTime());
        data.put("r_bar", R.getBarcodfiled());
        data.put("product_name", R.getName());
        data.put("unit_price", R.getUintPrice());
        data.put("current_qty", R.getCurrentQuantity());
        data.put("qty_kind", R.getQuantityKind());
        data.put("cost", R.getCost());
        data.put("source", R.getSource());
        data.put("number", R.getNumber());   
        if(InsertInfo("recalls", data))
            return true;
        else
            return false;
        //Old
//        
//        try{
//        PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
//                    "INSERT INTO recalls (rec_date,t_time,r_bar,product_name,unit_price,current_qty,qty_kind,cost,source,number) VALUES(?,?,?,?,?,?,?,?,?,?)");
//        statement.setDate(1, R.getDate());
//        statement.setTime(2, R.getTime());
//        statement.setString(3, R.getBarcodfiled());
//        statement.setString(4, R.getName());
//        statement.setDouble(5, R.getUintPrice());
//        statement.setDouble(6, R.getCurrentQuantity());
//        statement.setString(7, R.getQuantityKind());
//        statement.setDouble(8, R.getCost());
//        statement.setString(9, R.getSource());
//        statement.setLong(10, R.getNumber());
//        return statement.executeUpdate() > 0;
//        } catch (SQLException ex)
//        {}
//        return false;
    }
    
    
    public static void loadRecallsData(TableView TV) {
        ObservableList<Recalls> list = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM recalls";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String x1 =rs.getString("r_bar");
                String x2 =rs.getString("product_name");
                double x3=rs.getDouble("unit_price");
                int x4=rs.getInt("current_qty");
                String x5 =rs.getString("qty_kind");
                double x6 =rs.getDouble("cost");
                Date x7=rs.getDate("rec_date");
                Time x8=rs.getTime("t_time");
                String x9=rs.getString("source");
                long x10=rs.getLong("number");
                list.add(new Recalls(x1, x2, x3, x4, x5, x6, x7, x8, x9,x10));
            }
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
    }
    
    
    public static long getLastOrderNumberRecall(){
        String qu="SELECT number FROM recalls ORDER BY number DESC LIMIT 1"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        long num = 0;
        try {
            if(rs.next()){
                num=rs.getLong("number")+1;
                System.out.println("llllllllllll");}
            else
           {
                num=1;
                System.out.println("لسااااا");
//              Alerts.showInfoAlert("اول فواتير اليوم ..");
           }
        } catch (SQLException ex) {
            Alerts.showAlert("لايوجد بيانات",3);
        }
        return num;
    }
    
    public static boolean deleteRCallRow(Recalls Rcal) {
        return DeleteInfo("recalls", "number", Rcal.getNumber());
//        try {
//            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
//                    "DELETE FROM recalls WHERE number = ?");
//
//            statement.setLong(1, Rcal.getNumber());
//            int res = statement.executeUpdate();
//            if (res == 1) {
//                return true;
//            }
//        }
//        catch (SQLException ex) {//    
//        }
//        return false;
    }
    /****************************************END OF RECALLS********************************************************/
    /**************************************************************************************************************/
    
    
    
    
    
    
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /******************************************Rack shortages Controller**********DEFECTS**************************/
    public static void loadDEFECTSData(TableView TV) {
        ObservableList<Common_Properties> list = FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT pro_bar,pro_name,pro_All_qty FROM product WHERE pro_All_qty <= pro_minimum";
        ResultSet rs =DatabaseHandler.getInstance().execQuery(qu);
        try {
            while (rs.next()) {
                String x1 =rs.getString("pro_bar");
                String x2 =rs.getString("pro_name");
                int x3=rs.getInt("pro_All_qty");
                list.add(new Common_Properties(x1, x2, x3));
                System.out.println(x1+" "+x2+"  "+x3);
            }
            
        } catch (SQLException ex) {
            Alerts.showAlert("لا يوجد اصناف",1);
        }
        TV.setItems(list);
    }
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    /**************************************************************************************************************/
    
        ///////////////*********************************************************************\\\\\\\\\\\\\\\
        
    ///////////////*********************************************************************\\\\\\\\\\\\\\\

    /******************************* DATE PACKAGE   */
    public static void fillSalesWithInfoOfProduct(String bar,Label PName,Label D_Dat){ // 
        String qu="SELECT pro_name,expire_date FROM product WHERE pro_bar='"+bar+"'"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            if(rs.next()){
                PName.setText(rs.getString("pro_name"));
                D_Dat.setText(rs.getString("expire_date"));
                              
            }       
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static boolean updateExpireDate(String bar,String dat) {
        String qu="UPDATE product SET expire_date= '"+dat+"' WHERE pro_bar= '"+bar+"'";
        return DatabaseHandler.getInstance().execAction(qu);
    }
    
    /*************************************************/
    public static String getEmpName(String cod){
        String name="";
        String qu="SELECT emp_name FROM employee1 WHERE emp_id='"+cod+"'"; 
        ResultSet rs=DatabaseHandler.getInstance().execQuery(qu);
        try {
            if(rs.next()){
                name = rs.getString("emp_name");
            }       
        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    /***********************************************Reports********************************************************/
    
    
    
    

}
