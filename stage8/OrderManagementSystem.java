package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.Map; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
public class OrderManagementSystem { 
    private Warehouse warehouse; 
    private Map<ServiceProvider, Integer> providerToOrderCounter; 
    private Map<ServiceProvider, String> providersBusyStatus;
    private Map<Service, Set<ServiceProvider>> servicesToProviders;     
    private Map<Service, Integer> providersPerService; 
    private Set<Service> allServices;    
    private Set<Product> allProducts;
    private Set<Item> discontinuedItems;
    private int defaultProductStockLevel;
    public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders) {
        this.warehouse = new Warehouse(); //STEP #1: Creates a new Warehouse instance 
        OrderManagementSystem orderManagementSystem = new OrderManagementSystem(products, defaultProductStockLevel, serviceProviders, this.warehouse); //STEP #2: Calls the other constructor 
    }
    public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders, Warehouse warehouse) { //This method is called by the above OrderManagementSystem method
        this.warehouse = warehouse; //STEP #1: populate the warehouse with the products
        this.providerToOrderCounter = new HashMap<>();        
        this.providersBusyStatus = new HashMap<>();        
        this.servicesToProviders = new HashMap<>();
        this.providersPerService = new HashMap<>();
        this.allServices = new HashSet<>();  
        this.allProducts = new HashSet<>();  
        this.discontinuedItems = new HashSet<>(); 
        this.defaultProductStockLevel = defaultProductStockLevel;      
        for (Product product : products) {
            if (!this.discontinuedItems.contains(product) || !this.warehouse.isInCatalog(product.getItemNumber())){ //if it's already in the warehouse, do nothing - do not add it and do not throw any exception
                this.allProducts.add(product);
                this.warehouse.addNewProductToWarehouse(product, defaultProductStockLevel);
            }
        }
        for (ServiceProvider serviceProvider : serviceProviders) {
            this.providerToOrderCounter.put(serviceProvider, 0); 
            this.providersBusyStatus.put(serviceProvider, "free");
            for (Service service : serviceProvider.getServices()) { //STEP #2: retrieve set of services provided by the ServiceProviders, to save it as the set of services the business can provide
                if (!this.discontinuedItems.contains(service)) {
                    this.allServices.add(service);
                    if (this.servicesToProviders.containsKey(service)) {
                        Set<ServiceProvider> providers = new HashSet<>();
                        providers.addAll(this.servicesToProviders.get(service));
                        providers.add(serviceProvider);
                        this.servicesToProviders.put(service, providers); //STEP #3: create map of services to the List of service providers that provide them
                    } else {
                        Set<ServiceProvider> providers = new HashSet<>();
                        providers.add(serviceProvider);
                        this.servicesToProviders.put(service, providers); //STEP #3: create map of services to the List of service providers that provide them                        
                    }
                    if (this.providersPerService.containsKey(service)) {
                            int count = this.providersPerService.get(service);                        
                            this.providersPerService.put(service, count + 1); 
                    } else { 
                            this.providersPerService.put(service, 1);
                    }
                }            
            } 
        }
    }
    public void placeOrder(Order order) { 
        List items = Arrays.asList(order.getItems());
        Collection<Service> services = new HashSet<>(); 
        for (Object item : items) {
            if (item instanceof Service) {
                services.add((Service) item);
            }
        }
        if (validateServices(services, order) != 0) { //STEP #1: See if we have ServiceProviders for all Services in the order. If not, reject the order 
            throw new IllegalStateException (""); 
        }
        Collection<Product> products = new HashSet<>(); 
        for (Object item : items) {
            if (item instanceof Product) {
                products.add((Product) item);
            }
        }
        if (validateProducts(products, order) != 0) { //STEP #2b: See if we CAN fulfill ALL Items (Products) in the order (this implies that all must be check before putting the order through for any single product). We CAN fulfill a product order if either the warehouse currently has enough quantity in stock OR if the product is NOT on the "do not restock" list. 
                throw new IllegalArgumentException ("");
        }
        Set<Product> productTypes = new HashSet<>();
        productTypes.addAll(products);         
        for (Product product : productTypes) { //In the case that the current quantity of a product is < the quantity in the order AND the product is NOT on the "do not restock" list: the order management system should first instruct the warehouse to restock the item, and then tell the warehouse to fulfill this order.
            if ((!this.warehouse.canFulfill(product.getItemNumber(), order.getQuantity(product))) && (this.warehouse.isRestockable(product.getItemNumber()))) { 
                this.warehouse.restock(product.getItemNumber(), order.getQuantity(product)); 
            }
        }
        for (Product product : productTypes) {  //Step #4: Place the product orders with the warehouse      
            this.warehouse.fulfill(product.getItemNumber(), order.getQuantity(product)); 
        }
        Set<ServiceProvider> providerTracker = new HashSet<>(); 
        Set<Service> serviceTypes = new HashSet<>(); //Handle the service orders inside this class
        serviceTypes.addAll(services);                
        for (Service service : serviceTypes) { //There is a more efficient way to do this, but I will change it for the 2nd hand in
            int availableProviders = 0;
            Set<ServiceProvider> allProviders = new HashSet<>();
            allProviders.addAll(this.servicesToProviders.get(service));
            for (ServiceProvider provider : allProviders) { //providerToOrderCounter
                if ((availableProviders != order.getQuantity(service)) && (!providerTracker.contains(provider)) && (this.providersBusyStatus.get(provider) == "free") && (provider.getServices().contains(service))) {
                    providerTracker.add(provider);
                    ++availableProviders;
                }
            }
        }
        order.setCompleted(true); //STEP #3: Mark the order as completed
        for (ServiceProvider provider : providerTracker) { //STEP #4: Update the busy status of service providers involved...
            provider.assignToCustomer(); 
            this.providersBusyStatus.put(provider, "busy");
            this.providerToOrderCounter.put(provider, -1);
        }
        for (ServiceProvider provider : this.providerToOrderCounter.keySet()) { 
            int count = this.providerToOrderCounter.get(provider);
            this.providerToOrderCounter.put(provider, count + 1);
        }  
    }
    protected int validateServices(Collection<Service> services, Order order) { //General directions: return itemNumber of the first requested service encountered that we either do not have a provider for at all OR for which we do not have an available provider.
        for (ServiceProvider provider : this.providerToOrderCounter.keySet()) { 
            if ((this.providerToOrderCounter.get(provider) >= 3) && (this.providersBusyStatus.get(provider) == "busy")) { 
                this.providersBusyStatus.put(provider, "free");    
                this.providerToOrderCounter.put(provider, 0); 
                provider.endCustomerEngagement();              
            }
        } 
        Set<Service> serviceTypes = new HashSet<>(); 
        serviceTypes.addAll(services);         
        for (Service service : serviceTypes) { //STEP #1: Check that all the services are even carried by the System: return itemNumber of the first requested service encountered that we do not have a provider for at all
            if ((!this.allServices.contains(service)) || (this.discontinuedItems.contains(service))) { 
                return service.getItemNumber(); 
            }
        }
        Set<ServiceProvider> providerTracker = new HashSet<>();
        for (Service service : serviceTypes) { //STEP #2: Check how many times each service type occurs in the collection and then check to see if there are enough serviceProviders - If there aren't return the services itemNumber
            if (this.providersPerService.get(service) < order.getQuantity(service)) { 
                return service.getItemNumber();                
            }
            int availableProviders = 0; //STEP #3: Check how many available providers there are. //this.providerToOrderCounter.keySet
            Set<ServiceProvider> allProviders = new HashSet<>();
            allProviders.addAll(this.servicesToProviders.get(service));
            for (ServiceProvider provider : allProviders) { //this.servicesToProviders.get(service)
                if ((availableProviders != order.getQuantity(service)) && (!providerTracker.contains(provider)) && (this.providersBusyStatus.get(provider) == "free") && (provider.getServices().contains(service))) { 
                    providerTracker.add(provider);
                    ++availableProviders;              
                }
            } 
            if (availableProviders < order.getQuantity(service)) { //STEP #4: Check if there are enough available providers for the given service. 
                return service.getItemNumber(); 
            }
        } return 0; //STEP #5: Return 0 if all services are valid
    }
    protected int validateProducts(Collection<Product> products, Order order) { //validate that the requested quantity of products can be fulfilled
        for (Product product : products) {
            if ((!getProductCatalog().contains(product)) || (this.discontinuedItems.contains(product))) {
                return product.getItemNumber();
            }
            if (!this.warehouse.canFulfill(product.getItemNumber(), order.getQuantity(product)) && (!this.warehouse.isRestockable(product.getItemNumber()))) { ////Return 0 when there is enough quantity for all of the products in the warehouse OR when all the products are not on the do not restock list 
                return product.getItemNumber();            
            }
        } return 0; 
    }   
    protected Set<Product> addNewProducts(Collection<Product> products) { 
        Set<Product> addNewProducts = new HashSet<Product>();
        for (Product product : products) {  
            if (!this.discontinuedItems.contains(product)) { 
                this.allProducts.add(product);  
                if (!this.warehouse.isInCatalog(product.getItemNumber())) {
                    this.warehouse.addNewProductToWarehouse(product, this.defaultProductStockLevel);  
                    addNewProducts.add(product);
                }
            } 
        } return addNewProducts; //return set of products that were actually added.
    }
    protected void addServiceProvider(ServiceProvider provider) {  
        if (!this.providersBusyStatus.keySet().contains(provider)) { 
            this.providersBusyStatus.put(provider, "free");
            this.providerToOrderCounter.put(provider, 0);
            if (!provider.getServices().isEmpty()) { 
                for (Service service : provider.getServices()) { 
                    this.allServices.add(service);
                    if (this.servicesToProviders.containsKey(service)) {
                        Set<ServiceProvider> allProviders = new HashSet<>();
                        allProviders.addAll(this.servicesToProviders.get(service));
                        allProviders.add(provider);
                        this.servicesToProviders.put(service, allProviders); //STEP #3: create map of services to the List of service providers that provide them
                    } else {
                        Set<ServiceProvider> allProviders = new HashSet<>();
                        allProviders.add(provider);
                        this.servicesToProviders.put(service, allProviders); //STEP #3: create map of services to the List of service providers that provide them                        
                    }
                    if (this.providersPerService.containsKey(service)) {
                        int count = this.providersPerService.get(service); 
                        this.providersPerService.put(service, count + 1); 
                    } else { 
                        this.providersPerService.put(service, 1); 
                    }       
                }
            }
        }
    }
    public Set<Product> getProductCatalog() { 
        Set<Product> copyOfProducts = new HashSet<>();
        copyOfProducts.addAll(this.allProducts);
        return copyOfProducts;
    }
    public Set<Service> getOfferedServices() { 
        Set<Service> copyOfServices = new HashSet<>();
        copyOfServices.addAll(this.allServices);
        return copyOfServices;
    }
    protected void discontinueItem(Item item) { 
        if (item instanceof Service) { 
            this.discontinuedItems.add(item); //Prevent the it from being added in the future
            this.servicesToProviders.remove(item);
            this.providersPerService.remove(item);
            this.allServices.remove(item);
        } else { //If it's a Product - still sell whatever instances of this Product are in stock, but do not restock it (do not remove it from the set of products)
            this.warehouse.doNotRestock(item.getItemNumber()); 
            this.discontinuedItems.add(item); 
        } 
    }
    protected void setDefaultProductStockLevel(Product prod, int level) { //Set the default product stock level for the given product
        this.warehouse.setDefaultStockLevel(prod.getItemNumber(), level);
    }
}