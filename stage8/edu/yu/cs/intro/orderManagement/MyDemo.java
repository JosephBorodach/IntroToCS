package edu.yu.cs.intro.orderManagement; 
/*import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;*/
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Map; 
import java.util.Set; 
import java.util.*;
public class MyDemo { 
	private Set<Product> products; 
	private Set<Product>secondProducts;
	private Set<ServiceProvider> providers; 
	private Set<ServiceProvider> secondProviders; 
	private Set<Service> allServices; 
	private Set<Service> firstValidateServices;
	private Set<Service> secondValidateServices;  
	private Map<Integer, Product> idToProduct;
	private Map<Integer, Product> secondIdToProduct;
	private Map<Integer, Service> idToService; 
	private Map<Integer, Service> idToService_validateServices; 	
	private Map<Integer, Service> idToService_getServicesTotalPrice; 
	private Warehouse warehouse; 
	private Warehouse secondWarehouse; 


	public static void main(String[] args) { 
		MyDemo dd = new MyDemo(); 
		dd.runDemo(); 
	} 
	
	public MyDemo(){ 
		this.warehouse = new Warehouse();
		this.secondWarehouse = new Warehouse();
		this.products = new HashSet<>(); 
		this.secondProducts = new HashSet<>(); 
		this.idToProduct = new HashMap<>();
		this.secondIdToProduct = new HashMap<>();  
		this.idToService = new HashMap<>(); 
		this.idToService_validateServices = new HashMap<>();
		this.idToService_getServicesTotalPrice = new HashMap<>();
		this.firstValidateServices = new HashSet<>();
		this.secondValidateServices = new HashSet<>();
		this.allServices = new HashSet<>(); 
		this.providers = new HashSet<>(); 
		this.secondProviders = new HashSet<>(); 
	} 
	//@Test
	void runDemo(){
		//this.placeSuccessfulServiceOrder();
		//this.placeUnsuccessfulServiceOrder();

		//2nd Set of Tests - Using: OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders, Warehouse warehouse)
		OrderManagementSystem system = new OrderManagementSystem(this.products,5,this.providers,this.warehouse); //1st Set
		//populate our system with products and services 
		this.createDemoProducts(); 
		system.addNewProducts(this.products); 

		this.createDemoServiceProviders(); 
		for(ServiceProvider p : this.providers){ 
			system.addServiceProvider(p); 
		} 

		//make sure all the products added are in the catalog 
		Set<Product> catalog = system.getProductCatalog(); 
		assert this.products.size() == catalog.size(); 
		assert catalog.containsAll(this.products); 
	
		//make sure all the services are in the services offered 
		Set<Service> services = system.getOfferedServices(); 
		assert this.allServices.size() == services.size(); 
		assert services.containsAll(this.allServices); 

//Warehouse tests
	//Null values
		this.warehouse.addNewProductToWarehouse(null, 10); 

	//Exceptions Tests
		//doNotRestock: Add something to doNotRestock list and see if the methods that are supposed to check the doNotRestock list properly throw exceptions 
		//Test doNotRestock
		assert this.warehouse.doNotRestock(10) == 5; //Should have been set to 5
		boolean addNewProductToWarehouse = false; 
		try{ 
			this.warehouse.addNewProductToWarehouse(this.idToProduct.get(10), 500); //throws IllegalArgumentException if the product is (==true) in the "do not restock" set OR if the product is (==true) already in the warehouse 
		}catch (IllegalArgumentException e){ 
			addNewProductToWarehouse = true; 
		} 
		assert addNewProductToWarehouse;  

		boolean restock = false; 
		try{ 
			this.warehouse.restock(10, 500); //throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
		}catch (IllegalArgumentException e){ 
			restock = true; 
		} 
		assert restock;  

		boolean setDafaultStockLevel = false; 
		try{ 
			this.warehouse.setDefaultStockLevel(10, 500); //throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
		}catch (IllegalArgumentException e){ 
			setDafaultStockLevel = true; 
		} 
		assert setDafaultStockLevel;  

		boolean fulfill = false; 
		try{ 
			this.warehouse.fulfill(10, 500); //throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false --> Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog. --> if the given item number is in the warehouse's catalog, return true:
		}catch (IllegalArgumentException e){ 
			fulfill = true; 
		} 
		assert fulfill;  

		boolean doNotRestock = false; 
		try{ 
			this.warehouse.doNotRestock(10); //Probably don't need to worry about a product number that does not exsits but throw an IllegalArgumentException just in case.
		}catch (IllegalArgumentException e){ 
			doNotRestock = true; 
		} 
		assert doNotRestock;  		

//not in product catelog tests: 100 is not in the catelog
		/*Product test = new Product("pillow9",1000,1009);
		boolean addNewProductToWarehouse_NotInCatelog = false; 
		try{ 
			this.warehouse.addNewProductToWarehouse(test, 500); //throws IllegalArgumentException if the product is (==true) in the "do not restock" set OR if the product is (==true) already in the warehouse 
		}catch (IllegalArgumentException e){ 
			addNewProductToWarehouse_NotInCatelog = true; 
		} 
		assert addNewProductToWarehouse_NotInCatelog;*/ 

		boolean restock_NotInCatelog = false; 
		try{ 
			this.warehouse.restock(100, 500); //throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
		}catch (IllegalArgumentException e){ 
			restock_NotInCatelog = true; 
		} 
		assert restock_NotInCatelog;  

		boolean setDafaultStockLevel_NotInCatelog = false; 
		try{ 
			this.warehouse.setDefaultStockLevel(100, 500); //throws IllegalArgumentException if the product is in the "do not restock" set, or if it is not in the catalog
		}catch (IllegalArgumentException e){ 
			setDafaultStockLevel_NotInCatelog = true; 
		} 
		assert setDafaultStockLevel_NotInCatelog;  

		//Return false if (the product is not (==false) in the catalog OR) if there are fewer than quantity of the products in the catalog.
		boolean secondFulfill = false; 
		try{ 
			this.warehouse.fulfill(11, 500); //throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false --> Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog. --> if the given item number is in the warehouse's catalog, return true:
		}catch (IllegalArgumentException e){ //11 is currently set to zero
			secondFulfill = true; 
		} 
		assert secondFulfill;  

	//Test Change default stock level and check if it properly return the original # - It was originally set to 5
		assert this.warehouse.setDefaultStockLevel(11, 50) == 5; 
		assert this.warehouse.setDefaultStockLevel(13, 0) == 5;

	//Test: stock them
		this.warehouse.restock(11, 50); 
		this.warehouse.restock(12, 5);
		this.warehouse.fulfill(14, 5);//Should go to zero		
		//getStockLevel
		assert this.warehouse.getStockLevel(11) == 50;	
		assert this.warehouse.getStockLevel(12) == 5;
		assert this.warehouse.getStockLevel(14) == 0;
	
	//Test canFulfill method
		this.warehouse.restock(11, 50); 
		this.warehouse.restock(12, 100); 
		assert !this.warehouse.canFulfill(200, 1); //Not in catelog: Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog.
		assert !this.warehouse.canFulfill(200, 100); //Not sufficient quantity in stock: Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog.
		assert this.warehouse.canFulfill(11, 50); //Otherwise true: The product is both in the catalog AND there is sufficient stock of the requested product.
		assert this.warehouse.canFulfill(12, 75); //Otherwise true: The product is both in the catalog AND there is sufficient stock of the requested product.
		//May as well test canFulfill
		boolean thirdFulfill = false; 
		try{ 
			this.warehouse.fulfill(200, 1); //throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false --> Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog. --> if the given item number is in the warehouse's catalog, return true:
		}catch (IllegalArgumentException e){ 
			thirdFulfill = true; 
		} 
		assert thirdFulfill;  

		boolean fourthFulfill = false; 
		try{ 
			this.warehouse.fulfill(200, 100); //throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false --> Return false if the product is not (==false) in the catalog OR if there are fewer than quantity of the products in the catalog. --> if the given item number is in the warehouse's catalog, return true:
		}catch (IllegalArgumentException e){ 
			fourthFulfill = true; 
		} 
		assert fourthFulfill;

	//see if fulfill properly lowers the stock level
		this.warehouse.fulfill(11, 50);
		this.warehouse.fulfill(12,100);
		assert this.warehouse.getStockLevel(11) == 0;	
		assert this.warehouse.getStockLevel(12) == 0;

	//Test restock method
		this.warehouse.restock(11, 5); //The default for 11 is 50 so it should set it to 50 and not 5.
		assert this.warehouse.getStockLevel(11) == 50;	
		this.warehouse.restock(12, 50); //The default for 12 is 5 so it should set it to 50 and not 5.
		assert this.warehouse.getStockLevel(12) == 50;	
		this.warehouse.restock(13, 50); //The default for 13 is 0 so it should set it to 50 and not 0.
		assert this.warehouse.getStockLevel(13) == 50;	

	//Test isInCatelog method
		assert !this.warehouse.isInCatalog(200); //The default for 11 is 50 so it should set it to 50 and not 5.
		assert this.warehouse.isInCatalog(1);//if the given item number is in the warehouse's catalog, return true:
		assert this.warehouse.isInCatalog(10);//10 was placed on doNotRestock, but it should still be in the Catalog

	//Test isRestockable: return false if it's NOT (==false) in catalog OR IS (==true) in the "do not restock" set.
		assert !this.warehouse.isRestockable(200); //return false if it's NOT (==false) in catalog
		assert !this.warehouse.isRestockable(10); //return false if it IS (==true) in the "do not restock" set.
		assert this.warehouse.isRestockable(1); //Otherwise true
		assert this.warehouse.isRestockable(5); //Otherwise true

//Product Tests


		//create an order 
		Order order = new Order(); 
		order.addToOrder(this.idToProduct.get(1),3); //will use out of 5 of product #1 
		order.addToOrder(this.idToService.get(6),1); //will use the only service provider for #6 
		//if (order.getItems().size() == )//Test if getItems works
		system.placeOrder(order); 
		assert this.warehouse.getStockLevel(1) == 2; 
		assert order.isCompleted(); 

		//place another order, should throw IllegalStateException 
		order = new Order(); 
		order.addToOrder(this.idToService.get(6),1); //provider for #6 not available - should throw exception 
		boolean caught = false; 
		try{ 
			system.placeOrder(order); 
		}catch (IllegalStateException e){ 
			caught = true; 
		} 
		assert caught; 
		assert !order.isCompleted(); 

		//force it to throw exception for ordering more than available of a discontinued item 
		system.discontinueItem(this.idToProduct.get(1)); 
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(1),3); //only 2 left of product #1 
		caught = false; 
		try{ 
			system.placeOrder(order); 
		}catch (IllegalArgumentException e){ 
			caught = true; 
		} 
		assert caught; 
		assert !order.isCompleted(); 

		//order more than available of a current item, make sure it ups the stock level and fulfills it 
		assert this.warehouse.getStockLevel(2) == 5; 
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(2),10); 
		system.placeOrder(order); 
		assert order.isCompleted();

		assert this.warehouse.getStockLevel(2) == 0; 
		this.warehouse.restock(2,10); 
		assert this.warehouse.getStockLevel(2) == 10; 

		//place 2 more order2 to make 3 orders since service provider for 6 was all busy. Should then be able to place order for service #6 
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(3),1); 
		system.placeOrder(order); 
		assert order.isCompleted(); 
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(4),1); 
		system.placeOrder(order); 
		assert order.isCompleted(); 
		order = new Order(); 
		order.addToOrder(this.idToService.get(6),1); 
		system.placeOrder(order); 
		assert order.isCompleted(); 

		//Throw exception for order more services than available.
		Order secondOrder = new Order(); 
		secondOrder.addToOrder(this.idToService.get(6),500); 
		boolean secondOrder_exception = false; 
		try{ 
			system.placeOrder(secondOrder); 
		}catch (IllegalStateException e){ 
			secondOrder_exception = true; 
		} 
		assert secondOrder_exception; 

		//Throw an exception for ordering a product that is on doNotRestock and it has an insufficient amount
		assert this.warehouse.doNotRestock(13) == 50; //Should have been set to 50		
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(13),100); //only 2 left of product #1 
		caught = false; 
		try{ 
			system.placeOrder(order); 
		}catch (IllegalArgumentException e){ 
			caught = true; 
		} 
		assert caught; 
		assert !order.isCompleted(); 

		//Perform that order on for the current stock level of 50 - it should go threw
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(13),50); //only 2 left of product #1 
		system.placeOrder(order); 
		assert order.isCompleted(); 


//Test: Validate Services
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(3),1); 
		system.placeOrder(order); 
		assert order.isCompleted(); 
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(4),1); 
		system.placeOrder(order); 
		assert order.isCompleted(); 
		order = new Order(); 
		order.addToOrder(this.idToService.get(6),1); 
        Collection<Service> firstServices = new HashSet<>(); 
        firstServices.addAll(this.idToService_validateServices.values());
		assert system.validateServices(firstServices, order) == 0;

		//Try validating service with service that does not exists in the order management system.
		order.addToOrder(this.idToService.get(6),1); 
        Collection<Service> secondServices = new HashSet<>(); 
        secondServices.addAll(this.firstValidateServices);
		assert system.validateServices(secondServices, order) != 0;

//Tests for Order Method: 
	//Test getProductsTotalPrice
		order = new Order(); 
		order.addToOrder(this.idToProduct.get(3),3); //(item, quantity) --> Price is 3, so total price should be 9
		assert order.getProductsTotalPrice() == 9;
		order.addToOrder(this.idToProduct.get(11),5); //(item, quantity) --> Price is 11, so total price should be 55
		assert order.getProductsTotalPrice() == 64; //Should be 64 (55+9)
	
	//Test getServicesTotalPrice - The price returned by getPrice must be the per hour price multiplied by the number of hours the service takes
		order.addToOrder(this.idToService.get(6),3); //Price per hour is 6 and the # of hours it takes is 1
		assert order.getServicesTotalPrice() == 18; //Should be 18
		order.addToOrder(this.idToService_getServicesTotalPrice.get(60),2); //Price per hour is 60 and the # of hours it takes is 10 
		assert order.getServicesTotalPrice() == 1218; //Should be 1218 (1200+18)		
		//system.placeOrder(order); 
		//assert order.isCompleted(); 


		/*
		OrderManagementSystem secondSystem = new OrderManagementSystem(this.secondProducts,10,this.secondProviders,this.secondWarehouse); //1st Set
		//populate our system with products and services 
		this.createSecondDemoProducts(); 
		secondSystem.addNewProducts(this.secondProducts); */


	//Test - Version 1: Use the first OrderManagement constructor - null values
		Set<Product> firstNullProductSet = new HashSet<>();
		firstNullProductSet.add(null);
		Set<ServiceProvider> nullProviders = new HashSet<>();
		nullProviders.add(null);
		Set<Service> nullServices = new HashSet<>();
		nullServices.add(null);
		ServiceProvider nullProvider = new ServiceProvider("nully",1010,nullServices);
		nullProviders.add(nullProvider);

		OrderManagementSystem thirdSystem = new OrderManagementSystem(firstNullProductSet,10,nullProviders); //1st Set

	//
		thirdSystem.getProductCatalog(); 


	//Test: Add a set that contains null values.
		Set<Product> secondNullProductSet = new HashSet<>();
		secondNullProductSet.add(null);
		secondNullProductSet.addAll(firstNullProductSet);		
		thirdSystem.addNewProducts(secondNullProductSet);
		thirdSystem.getProductCatalog(); 

		//make sure all the products added are in the catalog 
		Set<Product> thirdCatalog = thirdSystem.getProductCatalog(); 
		//assert secondNullProductSet.size() == thirdCatalog.size(); 
		//assert thirdCatalog.containsAll(secondNullProductSet); 

	//See if it can distingush between null values and real products
		this.createDemoProducts(); 
		secondNullProductSet.addAll(this.products);
		thirdSystem.addNewProducts(secondNullProductSet);

		//make sure all the products added are in the catalog 
		Set<Product> thirdCatalogSecondTrial = thirdSystem.getProductCatalog(); 
		//assert nullProductSet.size() == thirdCatalogSecondTrial.size(); 
		//assert thirdCatalogSecondTrial.containsAll(nullProductSet); 

	//Test: Service Provider

		this.createDemoServiceProviders(); 
		for(ServiceProvider p : this.providers){ 
			thirdSystem.addServiceProvider(p); 
		} 

		this.setStockLevelRunDownViaOrdersReachNewLevel();
		this.runDownStockViaOrdersRestoreToDefaultLevel();

		Service service1 = new Service(10,10,10,"Service1"); //(double pricePerHour, int numberOfHours, int serviceID, String description)
		Set<Service> busySet = new HashSet<>();
		busySet.add(service1);
		ServiceProvider provider1 = new ServiceProvider("p1",1,busySet);
		thirdSystem.addServiceProvider(provider1);

		order = new Order(); 
		order.addToOrder(service1,3); //only 2 left of product #1 
		boolean corretlyThrowError = false;
		try {
			thirdSystem.placeOrder(order); 
		} catch (IllegalStateException e) {
			corretlyThrowError = true;
		}
		assert corretlyThrowError == true;
		//this.placeSuccessfulServiceOrder();
		/*//make sure all the products added are in the catalog 
		Set<Product> thirdCatalog = thirdSystem.getProductCatalog(); 
		assert this.products.size() == thirdCatalog.size(); 
		assert thirdCatalog.containsAll(this.products); 
	
		//make sure all the services are in the services offered 
		Set<Service> thirdServices = thirdSystem.getOfferedServices(); 
		assert this.allServices.size() == thirdServices.size(); 
		assert thirdServices.containsAll(this.allServices);*/

	} 

	private void createDemoProducts(){ 
		this.products.add(new Product("prod1",1,1)); //Name, price, productID
		this.products.add(new Product("prod2",2,2)); 
		this.products.add(new Product("prod3",3,3)); 
		this.products.add(new Product("prod4",4,4)); 
		this.products.add(new Product("prod5",5,5)); 
		this.products.add(new Product("prod6",6,6)); 
		this.products.add(new Product("prod7",7,7)); 
		this.products.add(new Product("prod10",10,10)); 
		this.products.add(new Product("prod11",11,11)); 
		this.products.add(new Product("prod12",12,12)); 
		this.products.add(new Product("prod13",13,13)); 
		this.products.add(new Product("prod13",14,14)); 

		for(Product p : this.products){ 
			this.idToProduct.put(p.getItemNumber(),p); 
		} 
	} 

	private void createDemoServiceProviders(){ 
		Service s1 = new Service(1,1,1,"srvc1"); //(double pricePerHour, int numberOfHours, int serviceID, String description)
		Service s2 = new Service(2,1,2,"srvc2"); 
		Service s3 = new Service(3,1,3,"srvc3"); 
		Service s4 = new Service(4,1,4,"srvc4"); 
		Service s5 = new Service(5,1,5,"srvc5"); 
		Service s6 = new Service(6,1,6,"srvc6"); 
		Set<Service> srvcSetAll = new HashSet<>(); 
		srvcSetAll.add(s1); 
		srvcSetAll.add(s2); 
		srvcSetAll.add(s3); 
		srvcSetAll.add(s4); 
		srvcSetAll.add(s5); 
		srvcSetAll.add(s6); 
		this.idToService_validateServices.put(s6.getItemNumber(), s6);
		this.allServices.addAll(srvcSetAll); 
		for(Service srvc : this.allServices){ 
			this.idToService.put(srvc.getItemNumber(),srvc); 
		} 
		this.providers.add(new ServiceProvider("p1",1,srvcSetAll)); 

		Set<Service> srvcSetThree = new HashSet<>();
		srvcSetThree.add(s1);
		srvcSetThree.add(s2);
		srvcSetThree.add(s3);
		this.providers.add(new ServiceProvider("p2",2,srvcSetThree));

		Set<Service> singleService = new HashSet<>();
		singleService.add(s1);
		this.providers.add(new ServiceProvider("p2",3,singleService));

		//Second set
		Service ss1 = new Service(10,10,10,"srvc10"); //(double pricePerHour, int numberOfHours, int serviceID, String description)
		Service ss2 = new Service(20,10,20,"srvc20"); 
		Service ss3 = new Service(30,10,30,"srvc30"); 
		Service ss4 = new Service(40,10,40,"srvc40"); 
		Service ss5 = new Service(50,10,50,"srvc50"); 
		Service ss6 = new Service(60,10,60,"srvc60"); 
		Set<Service> secondSrvcSetAll = new HashSet<>(); 
		secondSrvcSetAll.add(ss1); 
		secondSrvcSetAll.add(ss2); 
		secondSrvcSetAll.add(ss3); 
		secondSrvcSetAll.add(ss4); 
		secondSrvcSetAll.add(ss5); 
		secondSrvcSetAll.add(ss6); 
		this.firstValidateServices.addAll(srvcSetAll); 
		this.firstValidateServices.add(ss1);
		this.secondValidateServices.addAll(secondSrvcSetAll);
		for(Service srvc : this.secondValidateServices){ 
			this.idToService_getServicesTotalPrice.put(srvc.getItemNumber(),srvc); 
		} 
	}
	private void createSecondDemoProducts(){ 
		this.secondProducts.add(new Product("prod1",1,1)); //Name, price, productID
		this.secondProducts.add(new Product("prod2",2,2)); 
		this.secondProducts.add(new Product("prod3",3,3)); 
		this.secondProducts.add(new Product("prod4",4,4)); 
		this.secondProducts.add(new Product("prod5",5,5)); 
		this.secondProducts.add(new Product("prod6",6,6)); 
		this.secondProducts.add(new Product("prod7",7,7)); 
		this.secondProducts.add(new Product("prod10",10,10)); 
		this.secondProducts.add(new Product("prod11",11,11)); 
		this.secondProducts.add(new Product("prod12",12,12)); 
		this.secondProducts.add(new Product("prod13",13,13)); 
		this.secondProducts.add(new Product("prod13",14,14)); 

		/*for(Product p : this.products){ 
			this.idToProduct.put(p.getItemNumber(),p); 
		} */
	} 
	public void setStockLevelRunDownViaOrdersReachNewLevel(){
	    Warehouse warehouse = new Warehouse();
	    Product prod2 = new Product("prod2",2,2);
	    warehouse.addNewProductToWarehouse(new Product("prod1",1,1), 5);
	    warehouse.addNewProductToWarehouse(prod2, 5);
	    warehouse.addNewProductToWarehouse(new Product("prod3",3,3), 5);
	    warehouse.fulfill(2,4);
	    warehouse.setDefaultStockLevel(2,10);
	    warehouse.restock(2,3);
	    warehouse.fulfill(2,3);
	    
	    int level = warehouse.getStockLevel(2);
	    if(warehouse.getStockLevel(2)!= 7){
	        throw new IllegalStateException("Stock level of product 2 was initially 5, 4 were ordered, defaultStockLevel was raised to 10, 3 more ordered, warehouse.getStockLevel should now return 7, but it returned " + level);
	    }
	}
	public void runDownStockViaOrdersRestoreToDefaultLevel(){
	    Warehouse warehouse = new Warehouse();
	    Product prod2 = new Product("prod2",2,2);
	    warehouse.addNewProductToWarehouse(prod2, 5);
	    warehouse.fulfill(2,4);
	    warehouse.fulfill(2,1);
	    warehouse.restock(2,1);

	    //should be 5
	    int level = warehouse.getStockLevel(2);
	    if(warehouse.getStockLevel(2)!= 5){
	        throw new IllegalStateException("Stock level of product 2 was initially 5, 4 were ordered and then 1 was ordered, warehouse.getStockLevel should now return 5, but it returned " + level);
	    }
	}
	public void placeSuccessfulServiceOrder(){
	    //this.initTestData();
	    OrderManagementSystem system = new OrderManagementSystem(this.products,5,this.providers);
	    Order order = new Order();
	    Service svc = new Service(1,1,11,"srvc1");
	    order.addToOrder(svc,3);
	    system.placeOrder(order);
	    for(ServiceProvider sp : this.providers){
	        if(sp.getServices().contains(svc)){
	            try{
	                sp.assignToCustomer();
	                throw new IllegalArgumentException("ServiceProvider " + sp.getId() + " should've been assigned to fulfilling service " + svc.getItemNumber() + " but did not throw IllegalStateException when attempted to reassign");
	            }catch(IllegalStateException e){
	            }
	        }
	    }
	    if(!order.isCompleted()){
	        throw new IllegalArgumentException("Successful order should've been marked as completed");
	    }
	}
	public void placeUnsuccessfulServiceOrder(){
	    //this.initTestData();
	    OrderManagementSystem system = new OrderManagementSystem(this.products,5,this.providers);
	    Order order = new Order();
	    Service svc = new Service(1,1,1,"srvc1");
	    order.addToOrder(svc,5);
	    try {
	        system.placeOrder(order);
	        throw new IllegalArgumentException("Should NOT have been able to fulfill the order due to insufficient ServiceProviders, and thus should've thrown an IllegalStateException");
	    }catch(IllegalStateException e){}
	}


}

































