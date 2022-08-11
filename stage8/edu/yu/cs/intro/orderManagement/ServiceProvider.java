package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.HashSet;
public class ServiceProvider implements Comparable<ServiceProvider>{ //Is managed by OrderManagementSystem
    private String name;
    private int uniqueId;
    private String assignToCustomer;
    private Set<Service> services; 
    public ServiceProvider(String name, int id, Set services){
        this.name = name;
        this.uniqueId = id; 
        this.services = new HashSet<Service>(); 
        this.services.addAll(services);
        this.assignToCustomer = "free";
    }
    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.uniqueId;
    }
    protected void assignToCustomer(){ 
        if (this.assignToCustomer.equals("busy")) { 
            throw new IllegalStateException("");
        } else { 
            this.assignToCustomer = "busy";
        }
    }
    protected void endCustomerEngagement(){ 
        if (this.assignToCustomer.equals("free")) { 
            throw new IllegalStateException("");            
        } else {         
            this.assignToCustomer = "free";
        }
    }
    protected boolean addService(Service s){
        if (s == null) {
            throw new IllegalArgumentException ("");
        }        
        if (!this.services.contains(s)) {
            this.services.add(s); 
            return true; //true if it was added           
        } else {
            return false; //false if it was not added           
        }
    }
    protected boolean removeService(Service s){
        if (s == null) {
            throw new IllegalArgumentException ("");
        }        
        if (this.services.contains(s)) {
            this.services.remove(s);
            return true; //true if it was removed
        } else {
            return false; //false if it was not removed
        } 
    }
    public Set<Service> getServices(){ //return a COPY of the set of services
        Set<Service> getServices = new HashSet<>();
        if (this.services != null) {
            getServices.addAll(this.services);
        } return getServices; 
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
        ServiceProvider otherProvider = (ServiceProvider) o;
        if (this.hashCode() == otherProvider.hashCode()) { //test equality of relevant instance variables                 
            return true;
        } else {
            return false; 
        }
    }
    @Override
    public int hashCode() { //It is only their itemNumber (a.k.a. serviceID or productID) that uniquely identifies them
        return this.uniqueId;            
    }
    @Override
    public int compareTo(ServiceProvider other) {
        return this.hashCode() - other.hashCode();
    }
}