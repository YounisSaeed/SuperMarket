package Classes;

import java.sql.Time;


public class Suppliers{// Common_Methods is an Interface include Add , Update , Delete
    private long number;
    private Time time;
    
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
    public Suppliers()
    {}
    public Suppliers(String a,String b,String d)
    {
        this.supplierName=a;
        this.supplierPhone=b;
        this.salespersonName=d;
    }
    private String supplierName;
    private String supplierPhone;
    private String salespersonName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSalespersonName() {
        return salespersonName;
    }

    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

}
