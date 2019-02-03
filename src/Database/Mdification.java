/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Map;

/**
 *
 * @author walid
 */
public class Mdification { // this class contain Add and Delete Methods .. able to Modify again..
    
    public static Map<String, Object> data;
    public static boolean InsertInfo(String tableName,Map<String, Object> data){
        int i=0;
        String dat_cols="";
        Object[] dat_rows=new Object[data.size()];
        
        for(String key:data.keySet()){
            dat_rows[i]=data.get(key);
            if(i != data.size()-1)
                dat_cols+=key+",";
            else
                dat_cols+=key;
            i++;
        }
       
        String dat="";
        for(int j=0;j<dat_rows.length;j++){
            if(!(dat_rows[j] instanceof Integer) && !(dat_rows[j] instanceof Double) && !(dat_rows[j] instanceof Long)){
                dat_rows[j]="'"+dat_rows[j]+"'";
            }
            if(j != dat_rows.length-1)
                dat+=dat_rows[j]+",";
            else
                dat+=dat_rows[j];
        }
        String qu="INSERT INTO "+tableName+"("+dat_cols+")"+" VALUES("+dat+")";
        
        return database.DatabaseHandler.getInstance().execAction(qu);
        
    }
    
    public static boolean DeleteInfo(String tableName,String deleteWith,Object value){
        // by adding table name and column needed to delete with and value .. can delete any row from any table
        if(!(value instanceof Integer) && !(value instanceof Double) && !(value instanceof Long)){
            value="'"+value+"'";
        }
        String qu="DELETE FROM "+tableName+" WHERE "+deleteWith+"= "+value;
        return database.DatabaseHandler.getInstance().execAction(qu);
    }
    
    public static boolean UpdateInfo(String tableName,Map<String, Object> data,String updateWith,Object value){
        String setOfUpdates="";
        int i=0;
        for(String key:data.keySet()){
            Object newV=data.get(key);
            if(!(data.get(key) instanceof Integer) && !(data.get(key) instanceof Double) && !(data.get(key) instanceof Long))
                newV="'"+data.get(key)+"'";
            
            if(i!=data.size()-1)
                setOfUpdates+=key+" = "+ newV+",";
            else
                setOfUpdates+=key+" = "+ newV;
            i++;
        }
        
        if(!(value instanceof Integer) && !(value instanceof Double) && !(value instanceof Long))
            value="'"+value+"'";
        
        String qu="UPDATE "+tableName+" SET "+setOfUpdates+" WHERE "+updateWith+" = "+value;
        return database.DatabaseHandler.getInstance().execAction(qu);
    }
    
}
