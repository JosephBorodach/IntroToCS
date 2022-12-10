package orderManagement.edu.yu.cs.intro;

import orderManagement.edu.yu.cs.intro.*;

public class Service implements Item { //An implementation of item which represents a Service provided by the business.
    private double savedPricePerHour;
    private int savedNumberOfHours;
    private int savedSeviceID;
    private String savedDescription;
    public Service(double pricePerHour, int numberOfHours, int serviceID, String description){
        this.savedPricePerHour = pricePerHour;
        this.savedNumberOfHours = numberOfHours;
        this.savedSeviceID = serviceID;
        this.savedDescription = description;
    }
    public int getNumberOfHours(){ //return the number of hours this service takes
        return this.savedNumberOfHours;
    }
    @Override
    public int getItemNumber() {
        return this.savedSeviceID;
    }
    @Override
    public String getDescription() {
        return this.savedDescription;
    }
    @Override
    public double getPrice() { //The price returned by getPrice must be the per hour price multiplied by the number of hours the service takes
        return getNumberOfHours() * this.savedPricePerHour;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) { //see if it's the same object
            return true;
        } 
        if (o == null) { //see if it's null
            return false;
        } //
        if (getClass()!=o.getClass()) { //see if they are from the same class
            return false;
        }        
        Service otherService = (Service) o; //cast other object to my class   
        if (this.hashCode() == otherService.hashCode()) { //test equality of relevant instance variables                 
            return true;
        } else {
            return false; 
        }
    }
    @Override
    public int hashCode() { //It is only their itemNumber (a.k.a. serviceID or productID) that uniquely identifies them
        return this.savedSeviceID;
    }
}