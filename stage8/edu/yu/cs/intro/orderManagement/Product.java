package edu.yu.cs.intro.orderManagement;
public class Product implements Item { //A "physical" item that is "stocked" in the warehouse.
    private String savedName;
    private double savedPrice;
    private int getProductID;
    public Product(String name, double price, int productID){
        this.savedName = name;
        this.savedPrice = price;
        this.getProductID = productID;
    }
    @Override
    public int getItemNumber() { //Fix: get the Item number not the product number
        return this.getProductID;
    }
    @Override
    public String getDescription() {
        return this.savedName;
    }
    @Override
    public double getPrice() {
        return this.savedPrice;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) { //see if it's the same object
            return true;
        } 
        if (o == null) { //see if it's null
            return false;
        }
        if (getClass()!=o.getClass()) { //see if they are from the same class
            return false;
        }        
        Product otherProduct = (Product) o; //cast other object to my class                   
        if (this.hashCode() == otherProduct.hashCode()) { //test equality of relevant instance variables 
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() { //It is only their itemNumber (a.k.a. serviceID or productID) that uniquely identifies them
        return this.getProductID;        
    }
}