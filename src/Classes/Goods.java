/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;


/**

 */
public class Goods{
    private String productName;
    private String productBarCode;
    private String productCategory;
    private String productSupplier;
    
    public Goods(){}

    public Goods(String productName, String productBarCode, String productCategory, String productSupplier, int itemsInPacket, int PacketsInBox, String box,double itemPrice, double packetPrice, double boxPrice, int productMinQuantity) {
        this.productName = productName;
        this.productBarCode = productBarCode;
        this.productCategory = productCategory;
        this.productSupplier = productSupplier;
        this.itemsInPacket = itemsInPacket;
        this.PacketsInBox = PacketsInBox;
        this.itemPrice = itemPrice;
        this.packetPrice = packetPrice;
        this.boxPrice = boxPrice;
        this.productMinQuantity = productMinQuantity;
        this.box=box;
        //this.productExpirationdate = productExpirationdate;
    }
    //private Price productPrice;
    //private Quantity productQuantity;
    private int itemsInPacket;

    public Goods(String productName, String productBarCode, String productExpirationdate) {
        this.productName = productName;
        this.productBarCode = productBarCode;
        this.productExpirationdate = productExpirationdate;
    }
    
    private int PacketsInBox;
    private String box;

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }
    

    
    private double itemPrice;
    private double packetPrice;
    private double boxPrice;
    private int productMinQuantity;
    private String productExpirationdate;

    public String getProductExpirationdate() {
        return productExpirationdate;
    }

    public void setProductExpirationdate(String productExpirationdate) {
        this.productExpirationdate = productExpirationdate;
    }
    private long allQuantity; //// //////////// //////////// ///////////////////

    public int getItemsInPacket() {
        return itemsInPacket;
    }

    public void setItemsInPacket(int itemsInPacket) {
        this.itemsInPacket = itemsInPacket;
    }

    public int getPacketsInBox() {
        return PacketsInBox;
    }

    public void setPacketsInBox(int PacketsInBox) {
        this.PacketsInBox = PacketsInBox;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getPacketPrice() {
        return packetPrice;
    }

    public void setPacketPrice(double packetPrice) {
        this.packetPrice = packetPrice;
    }

    public double getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(double boxPrice) {
        this.boxPrice = boxPrice;
    }
    

    public long getAllQuantity() {
        return allQuantity;
    }

    public void setAllQuantity(long allQuantity) {
        this.allQuantity = allQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBarCode() {
        return productBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public int getProductMinQuantity() {
        return productMinQuantity;
    }

    public void setProductMinQuantity(int productMinQuantity) {
        this.productMinQuantity = productMinQuantity;
    }

    

}
