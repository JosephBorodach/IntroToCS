package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.Map; 
import java.util.HashMap;
import java.util.HashSet;
public class Warehouse { //Is managed by OrderManagementSystem and Stocks products, fulfills product orders, manages stock of products.
    private Set<Product> products;
    private Set<Integer> itemCatalog; 
    private Map<Integer, Integer> defaultStockLevel; 
    private Map<Integer, Integer> stockLevel;
    private Set<Integer> doNotRestock;
    protected Warehouse(){ //Step #1: create a warehouse 
        this.doNotRestock = new HashSet<Integer>(); //Step #2: initialize all the instance variables
        this.products = new HashSet<Product>();        
        this.itemCatalog = new HashSet<Integer>();
        this.defaultStockLevel = new HashMap<>();
        this.stockLevel = new HashMap<>();
    }
    protected Set<Product> getAllProductsInCatalog(){ //return all unique Products stocked in the warehouse --> QU: What does it mean by "unique"?
        Set<Product>getAllProductsInCatalog = new HashSet<>(); //you don't want someone who calls this method to be able to directly manipulate your collection of products
            if (this.products != null) {
                getAllProductsInCatalog.addAll(this.products);
            }
        return getAllProductsInCatalog;
    }
    protected void addNewProductToWarehouse(Product product, int desiredStockLevel){ //Add a product to the warehouse, at the given stock level
        if (product == null) {
            throw new IllegalArgumentException ("");            
        }
        if (this.doNotRestock.contains(product) || this.products.contains(product)) { //throws IllegalArgumentException if the product is (==true) in the "do not restock" set OR if the product is (==true) already in the warehouse 
            throw new IllegalArgumentException ("");
        } else {
            this.products.add(product); 
//Should it be stocked when it's added? 
            this.stockLevel.put(product.getItemNumber(), desiredStockLevel); //the number to stock initially
            this.defaultStockLevel.put(product.getItemNumber(), desiredStockLevel); //Step #3: the # to restock to when subsequently restocked  
            this.itemCatalog.add(product.getItemNumber());
        }
    }    
    protected void restock(int productNumber, int minimum){ //defaultStockLevel does not change.
        if(!isRestockable(productNumber)) { //Step #1: throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
            throw new IllegalArgumentException ("");
        } else if (getStockLevel(productNumber) >= minimum) { //Step #2: If the actual stock is already >= the minimum, do nothing.         
            return;
        } else if (defaultStockLevel.get(productNumber) > minimum) { //Step #3: If the defaultStockLevel is greater than the minimum, raise it to the defaultStockLevel
            this.stockLevel.put(productNumber, defaultStockLevel.get(productNumber));
            return;
        } else { //Step #4: Otherwise, raise it to the minimum level.  
            this.stockLevel.put(productNumber, minimum); 
        }
    }
    protected int setDefaultStockLevel(int productNumber, int quantity){
        if (!isRestockable(productNumber)) { //Step #1: throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
            throw new IllegalArgumentException ("");
        } else { //Step #2: Set the new default stock level for the given product
            int oldDefaultStockLevel = this.defaultStockLevel.get(productNumber);
            this.defaultStockLevel.put(productNumber, quantity); //the productNumber is the key, the quantity is the value
            return oldDefaultStockLevel; //Step #3: return the old default stock level
        }
    }
    protected int getStockLevel(int productNumber){ 
        if (this.stockLevel.get(productNumber) == 0 || this.stockLevel.get(productNumber) == null) { //Step #1: zero if it is not stocked - check for both 0 or null
            return 0; //it would be null if the product is not on the currentStockLevel at all
        } else { //Step #2: return how many of the given product we have in stock
            return this.stockLevel.get(productNumber);
        }
    }
    protected boolean isInCatalog(int itemNumber){
        if (this.itemCatalog.contains(itemNumber)) { //Step #1: if the given item number is in the warehouse's catalog, return true:
            return true;
        } else { //Step #2: if not, return false
            return false; 
        }
    }
    protected boolean isRestockable(int itemNumber){
        if (!isInCatalog(itemNumber) || this.doNotRestock.contains(itemNumber)) { //return false if it's NOT (==false) in catalog OR IS (==true) in the "do not restock" set.
            return false;
        } else { //Otherwise true.
            return true; 
        }
    }
    protected int doNotRestock(int productNumber){
        if (this.doNotRestock.contains(productNumber)) {
            throw new IllegalArgumentException ("");
        } else {
            this.doNotRestock.add(productNumber); //Step #1: add the given product to the "do not restock" set
            return getStockLevel(productNumber); //Step #2: return the current actual stock level of the product
        }
    }
    protected boolean canFulfill(int productNumber, int quantity){ //Can the warehouse fulfill an order for the given amount of the given product?
        if (!isInCatalog(productNumber) || getStockLevel(productNumber) < quantity){ //Step #1: Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog.
            return false;
        } else { //Step #2: Otherwise true: The product is both in the catalog AND there is sufficient stock of the requested product.
            return true;
        }
    }
    protected void fulfill(int productNumber, int quantity){ 
        if (!canFulfill(productNumber, quantity)) { //Step #1: throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false
            throw new IllegalArgumentException ("");
        } else { //Step #2: Fulfill an order for the given amount of the given product
            this.stockLevel.replace(productNumber, (getStockLevel(productNumber) - quantity)); //i.e. lower the stock levels of the product by the given amount
        }
    }
}