package Classes;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Alerts extends Throwable{
    
    
    
    
    
    /**
     * @param content*
     * @param key*****************************************************************************/
     public static void showAlert(String content,int key){
      final  Alert alert =new Alert(Alert.AlertType.NONE); //Make an object of alert class of type information
        
      /**************** Determine the type of Alert*************************/
      /*  1 for information alert
          2 for warning alert
          3 for Erorr aler 
          any number : information alert
      */
      switch(key){    
        case 1:
            alert.setAlertType(Alert.AlertType.INFORMATION);  //Information alert
            alert.setTitle("رسالة تأكيد");
            break;
        case 2:
             alert.setAlertType(Alert.AlertType.WARNING);    // Warning alert 
             alert.setTitle("إنتبه");
              break;
        case 3:
            alert.setAlertType(Alert.AlertType.ERROR);      //Error Alert
            alert.setTitle("خطأ");
            break;
      
        default:
            alert.setAlertType(Alert.AlertType.INFORMATION);
            break;
        
        }
           
           alert.setContentText(content);  //Set content of alert
            //Start of object thread
            Thread thread = new Thread(() -> {
            try {
                
                Thread.sleep(2000); // Wait for 5 secs
                if (alert.isShowing()) {
                    Platform.runLater(() -> alert.close());
                }
            } catch (Exception exp) {
//                exp.printStackTrace();    //it prints exeotion 
                System.out.println("There are a problem at showAlert ");
            }
        });
        //end of object thread
        
        thread.setDaemon(true);
        thread.start();
        Optional<ButtonType> result = alert.showAndWait();
    }
     
    /**
     * @param content*
     * @param Choise*
     * @return *************************************************************************************************************/
     public static boolean ConfirmAlert(String content,String Choise){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("إنتبه");
        //alert.setResult(ButtonType.CANCEL);
        alert.setContentText(content+Choise);
        Optional<ButtonType> answer = alert.showAndWait();
        return answer.get() == ButtonType.OK;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    
//    public static void showInfoAlert(String content){
//      final  Alert alert =new Alert(Alert.AlertType.NONE); //Make an object of alert class of type information
//      int key=4;  
//      switch(key){
//            
//        case 1:
//        alert.setAlertType(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("رسالة تأكيد");
//        break;
//        case 2:
//           alert.setAlertType(Alert.AlertType.ERROR);
//            alert.setTitle("خطأ");
//            break;
//        case 3:
//            alert.setAlertType(Alert.AlertType.WARNING);
//        alert.setTitle("إنتبه");
//        break;
//        case 4:
//            alert.setAlertType(Alert.AlertType.INFORMATION);
//        alert.setTitle("رسالة تأكيد");
//        break;
//        default:
//            alert.setAlertType(Alert.AlertType.INFORMATION);
//            System.out.println("Error at Alert method");
//            break;
//        
//        }
//            
//         alert.setContentText(content); 
//             
//        //Start of object thread
//        Thread thread = new Thread(() -> {
//            try {
//                
//                Thread.sleep(2000); // Wait for 5 secs
//                if (alert.isShowing()) {
//                    Platform.runLater(() -> alert.close());
//                }
//            } catch (Exception exp) {
////                exp.printStackTrace();    //it prints exeotion 
//                System.out.println("There are a problem at showinfoAlert");
//            }
//        });
//        //end of object thread
//        
//        thread.setDaemon(true);
//        thread.start();
//        Optional<ButtonType> result = alert.showAndWait();
//    }
    
     
//    public static void showErrorAlert(String content){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//           
//            alert.setTitle("خطأ");
//            alert.setContentText(content);
//            Thread thread = new Thread(() -> {
//            try {
//                // Wait for 5 secs
//                Thread.sleep(2000);
//                if (alert.isShowing()) {
//                    Platform.runLater(() -> alert.close());
//                }
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//        Optional<ButtonType> result = alert.showAndWait();
//    }
   }
    
//    public static void showWorningAlert(String content){
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("رسالة تأكيد");
//        alert.setContentText(content);
//        Thread thread = new Thread(() -> {
//            try {
//                // Wait for 5 secs
//                Thread.sleep(2000);
//                if (alert.isShowing()) {
//                    Platform.runLater(() -> alert.close());
//                }
//            } catch (Exception exp) {
//                exp.printStackTrace();
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//        Optional<ButtonType> result = alert.showAndWait();
//        
//    }
// }
/*
    private void deleteAllRows(){
        Sales S=new Sales();
        if(AllCheckbox.isSelected()){
            billTabel.setFocusTraversable(true);
            if(Alerts.ConfirmAlert("هل تريد مسح جميع عناصر الجدول ؟","")){
                boolean result=DataHelper.deleteAllRowsInSalesTV(S);
                if(result){
                    billTabel.getItems().clear();
                    Alerts.showInfoAlert("تم مسح جميع العناصر");
                }
                else
                    Alerts.showErrorAlert("لم يتم المسح");
            }
        }
        else{
            Alerts.showErrorAlert("اضغط على مربع "+" \"الكل\" "+"لتتم عملية مسح جميع العناصر");
        }
        
    }
*/

/*
public static boolean deleteAllRowsInSalesTV(int serial) {
        String qu = "DELETE FROM sales WHERE sale_id ="+serial+""; //
        if(DatabaseHandler.getInstance().execAction(qu)){
            return true;
        }
        return false;
    }

*/