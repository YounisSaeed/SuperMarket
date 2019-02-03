
package Classes;

import java.io.IOException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Shypoup
 */
public class Additional {
    
    
    //OPEN CALCULATOR METHOD USED FOR OPERN CALCULATOR BUTTON
    public static void openCalculator(){
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException ex) {
            Alerts.showAlert("حدث مشكلة اثناء فتح الآلة الحاسبة , يرجى المحاولة لاحقا",3);
        }
    }
    
    
    // CLEAR TEXTFIELDCONTENT USED FOR DELETE TEXTFELD AND TEXT AREAS CONTENT
    public static void clearTextfieldContent(TextInputControl... f){   //TextInputControl parent of textfeild and textarea class 
        for(TextInputControl t:f){   
                t.clear();
                }    
    }
    
    // clearComboBoxContent METHOD  USED FOR DELETE COMBOBOX CONTENT
      public static void clearComboBoxContent(ComboBox... b){
          
        for(ComboBox c:b){
                c.setValue("");
                }    
    }
    
    // clearLabelConten METHOD  USED FOR DELETE LABEL CONTENT
        public static void clearLabelContent(Label... l){
          
        for(Label a:l){
                a.setText("");
                }    
    }
        
    // clearTableContent METHOD  USED FOR DELETE TABELE CONTENT 
         public static void clearTableContent(TableView... t){
          
        for(TableView a:t){
                a.getItems().clear();
                }    
    }
    
}
