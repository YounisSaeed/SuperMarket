package Classes;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Shypoup
 */
public class Validations {
    
    //textInputNotEmpty METHOD USED FOR CHECK TEXTFEILD OR TEXT AREA EMPTY OR NOT
     public static boolean textInputNotEmpty(TextInputControl... f){   //TextInputControl parent of textfeild and textarea class 
         int i=0;  
        for(TextInputControl t:f){
        if(!t.getText().isEmpty() )
          i++;                           // To check the number of textfields is not empty
        }
        if(i==f.length)                 // if number of not empty text feilds equal the number of passed argument 
            return true;               // so all passed textfields are empty then return true 
        else{
             Alerts.showAlert("برجاء ملىء جميييع الحقول المطلوبة",3);  // if not return error message
            
        }
        return false;   
            
    }
     
     
     
     
     //textInputNotEmpty METHOD USED FOR CHECK TEXTFEILD OR TEXT AREA  AND COMBOBOX EMPTY OR NOT
     // OVERRIDE OF U PASS COMBOBOX AND TEXTFIELD
     public static boolean textInputNotEmpty(ComboBox c,TextInputControl... f){   //TextInputControl parent of textfeild and textarea class 
                                                                                  // U SHOULD PASS COMBOBOX FIRST
         int i=0;  
        for(TextInputControl t:f){
        if(!t.getText().isEmpty() )
          i++;                           // To check the number of textfields is not empty
        }
        if(i==f.length && !c.getValue().equals(""))                 // if number of not empty text feilds equal the number of passed argument AND COBMOBOX EMPTY OR NOT
            return true;               // so all passed textfields are empty then return true 
        else{
             Alerts.showAlert("برجاء ملىء جميييع الحقول المطلوبة",3);  // if not return error message
            
        }
        return false;   
            
    }
     
     
     
     
     
      //textInputNotEmpty METHOD USED FOR CHECK TEXTFEILD OR TEXT AREA  AND LABEL EMPTY OR NOT
     // OVERRIDE OF U PASS LABEL AND TEXTFIELD
     public static boolean textInputNotEmpty(Label l,TextInputControl... f){   //TextInputControl parent of textfeild and textarea class 
                                                                                  // U SHOULD PASS LABEL FIRST
         int i=0;  
        for(TextInputControl t:f){
        if(!t.getText().isEmpty() )
          i++;                           // To check the number of textfields is not empty
        }
        if(i==f.length && !l.getText().equals(""))                 // if number of not empty text feilds equal the number of passed argument AND CLABEL EMPTY OR NOT
            return true;               // so all passed textfields are empty then return true 
        else{
             Alerts.showAlert("برجاء ملىء جميييع الحقول المطلوبة",3);  // if not return error message
            
        }
        return false;   
            
    }
     
     
      public static boolean isPositive(TextInputControl... f){   
                                                                                 
        for(TextInputControl t:f){
        if( Double.parseDouble(t.getText())<=0  ) {  
           Alerts.showAlert("لقدأدخلت قيمة خاطئة ",3);   
           return false; 
        }}
        return true;
    
      }



}
     

