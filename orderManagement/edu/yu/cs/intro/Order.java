package orderManagement.edu.yu.cs.intro;

import orderManagement.edu.yu.cs.intro.*;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Item, Integer> order; 
    private boolean orderStatus = false;
    public Order(){ 
        this.order = new HashMap<>(); 
    }
    public Item[] getItems(){ 
//What should I do here if it is null? 
        return this.order.keySet().toArray(new Item[this.order.keySet().size()]);
    }
    public int getQuantity(Item b){ 
        if (b == null) {
            throw new IllegalArgumentException ("");
        }        
        if (!this.order.containsKey(b)) { //Zero if the item is not in the order
            return 0;
        } else {
            return this.order.get(b);            
        }
    }
    public void addToOrder(Item item, int quantity){ 
        if (item == null || quantity == 0) { 
            throw new IllegalArgumentException ("");             
        }
        if (this.order.containsKey(item)) { 
            this.order.put(item, (getQuantity(item) + quantity)); 
        } else { 
            this.order.put(item, quantity); 
        }
    }
    public double getProductsTotalPrice(){ 
        double totalPrice = 0;  
        if (this.order.keySet() != null) {
            for (Item product : this.order.keySet()) { 
                if (product instanceof Product) { 
                    totalPrice += (getQuantity(product) * product.getPrice());
                }
            }
        } return totalPrice; 
    }
    public double getServicesTotalPrice(){ 
        double totalPrice = 0;  
        if (this.order.keySet() != null) {
            for (Item service : this.order.keySet()) {
                if (service instanceof Service) {
                    totalPrice += (getQuantity(service) * service.getPrice()); 
                }
            }
        } return totalPrice; 
    }
    public boolean isCompleted() { 
        if (this.orderStatus == true) {
            return true;
        } else {
            return false;
        }
    }
    public void setCompleted(boolean completed) { 
        this.orderStatus = completed;
    }
}