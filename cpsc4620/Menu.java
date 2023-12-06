package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * This file is where the front end magic happens.
 * 
 * You will have to write the methods for each of the menu options.
 * 
 * This file should not need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 * 
 * You can add and remove methods as you see necessary. But you MUST have all of the menu methods (including exit!)
 * 
 * Simply removing menu methods because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 * 
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 * 
 */

public class Menu {

	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Welcome to Pizzas-R-Us!");
		int menu_option = 0;

		// present a menu of options and take their selection
		
		PrintMenu();

		String option = reader.readLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
			case 1:// enter order
				EnterOrder();
				break;
			case 2:// view customers
				viewCustomers();
				break;
			case 3:// enter customer
				EnterCustomer();
				break;
			case 4:// view order
				// open/closed/date
				ViewOrders();
				break;
			case 5:// mark order as complete
				MarkOrderAsComplete();
				break;
			case 6:// view inventory levels
				ViewInventoryLevels();
				break;
			case 7:// add to inventory
				AddInventory();
				break;
			case 8:// view reports
				PrintReports();
				break;
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
		}

	}
	public static boolean CheckValidInput(String regex, String input) {
		if(input.length()==0)
			return false;
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(input);
		if(m.results().count() != 0)
			return true;
		else
			return false;
	}
	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException
	{
		Connection conn = DBConnector.make_connection();

		/*
		 * EnterOrder should do the following:
		 *
		 * Ask if the order is delivery, pickup, or dinein
		 *   if dine in....ask for table number
		 *   if pickup...
		 *   if delivery...
		 *
		 * Then, build the pizza(s) for the order (there's a method for this)
		 *  until there are no more pizzas for the order
		 *  add the pizzas to the order
		 *
		 * Apply order discounts as needed (including to the DB)
		 *
		 * return to menu
		 *
		 * make sure you use the prompts below in the correct order!
		 */

		 // User Input Prompts...
		int tableNumber = 0;
		String orderChoice, line,customerAddress="";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timestamp=dtf.format(now);
		int orderType=0,customerID=0;
		Order o=new Order(0,customerID,"abc",timestamp,0.0,0.0,0);
		int maxOrderID = DBNinja.getNextOrderID();
		o.setOrderID(maxOrderID);
		ArrayList<Customer> cus = new ArrayList<Customer>();
		cus=DBNinja.getCustomerList();
		customerID=cus.get(cus.size()-1).getCustID();
		o.setCustID(customerID);
		boolean inpValid = true;
		String regexp = "^([1-3]?)$";
		while(true)
		{
			System.out.println("Is this order for: \n1.) Dine-in\n2.) Pick-up\n3.) Delivery\nEnter the number of your choice:");
			orderChoice = reader.readLine().trim();
			inpValid = CheckValidInput(regexp, orderChoice);
			if(orderChoice.equals("1") || orderChoice.equals("2") || orderChoice.equals("3"))
			{
				orderType = Integer.parseInt(orderChoice);
				break;
			}
			else
				System.out.println("Provide only valid input");
		}
		if(orderType == 1)
		{
			System.out.println("What is the table number for this order?");
			line = reader.readLine();
			tableNumber = Integer.parseInt(line);
			o.setOrderType("dinein");
			DBNinja.dineIn(o.getOrderID(), tableNumber);
		}
		else
		{
			String choice="";
			inpValid = true;
			regexp = "^([YyNn]?)$";

			while(true)
			{
				System.out.println("Is this order for an existing customer? Answer y/n: ");
				choice = reader.readLine().trim();
				inpValid = CheckValidInput(regexp, choice);
				if(choice.equals("N") || choice.equals("n") || choice.equals("Y") || choice.equals("y"))
				{
					break;
				}
				else
					System.out.println("Provide only valid input");
			}
			if(choice.equals("N") || choice.equals("n"))
			{
				Menu.EnterCustomer();
				o.setOrderID(0);
			}
			else
			{
				System.out.println("Here's a list of current customers: ");
				viewCustomers();
				System.out.println("which customer is this order for? Enter ID Number");
				customerID = Integer.parseInt(reader.readLine());
				o.setCustID(customerID);
				/*
				ArrayList<Integer> custIDs = new ArrayList<Integer>();
				for(Customer cust:cus){
					custIDs.add(cust.getCustID());
				}
				if(custIDs.contains(customerID)){

				}
				else{

				}*/
				// check if cust ID in cus list
			}
			if(orderType==2)
			{
				o.setOrderType("pickup");
			}
			else
			{
				System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
				String aptNum = reader.readLine();
				System.out.println("What is the Street for this order? (e.g., Smile Street)");
				String street = reader.readLine();
				System.out.println("What is the City for this order? (e.g., Greenville)");
				String city = reader.readLine();
				System.out.println("What is the State for this order? (e.g., SC)");
				String state = reader.readLine();
				System.out.println("What is the Zip Code for this order? (e.g., 20605)");
				String zip = reader.readLine();
				customerAddress = aptNum+ " "+street + "," + city + " " + state + " " + zip;
				o.setOrderType("delivery");
			}
		}

		ArrayList<Integer[]> pizzaDiscountmap = new ArrayList<Integer[]>();
		ArrayList<Integer[]> orderDiscountmap = new ArrayList<Integer[]>();
		ArrayList<Integer[]> pizzatopping = new ArrayList<Integer[]>();

		ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
		double pricetocustomer=0.0;
		double pricetobusiness=0.0;
		System.out.println("Let's Build a pizza");
		boolean orderflag=true;
		while(true)
		{


			ArrayList<Integer> toppingList = new ArrayList<>();
			boolean[] isToppingDouble = new boolean[17];
			System.out.println("What size is the pizza?");
			regexp = "^[1-4]?$";
			inpValid = true;
			int s=1;
			while(inpValid)
			{
				System.out.println("1.Small\n2.Medium\n3.Large\n4.X-Large\nEnter the corresponding number:");
				line = reader.readLine();
				inpValid = CheckValidInput(regexp, line);
				if(inpValid)
				{
					inpValid = false;
					s = Integer.parseInt(line);
				}
				else
					System.out.println("Provide only valid input");
			}
			String size=null;
			String crust=null;
			if(s==1)
				size="Small";
			else if(s==2)
				size="Medium";
			else if(s==3)
				size="Large";
			else if(s==4)
				size="XLarge";

			System.out.println("What crust for this pizza?");
			regexp = "^[1-4]$";
			inpValid = false;
			int c = 1;
			while (!inpValid)
			{
				System.out.println("1.Thin\n2.Original\n3.Pan\n4.Gluten-Free\nEnter the corresponding number:");
				line = reader.readLine();
				inpValid = CheckValidInput(regexp, line);
				if(inpValid)
				{
					inpValid = true;
					c = Integer.parseInt(line);
				}
				else
					System.out.println("Provide only valid input");
			}
			if(c==1)
				crust="Thin";
			else if(c==2)
				crust="Original";
			else if(c==3)
				crust="Pan";
			else if(c==4)
				crust="Gluten-Free";

			double basepricecustomer=DBNinja.getBaseCustPrice(size,crust);
			double basepricebusiness=DBNinja.getBaseBusPrice(size,crust);

			int pizzaId=DBNinja.getMaxPizzaID();

			Pizza p=new Pizza(pizzaId+1,size,crust,maxOrderID,"Processing",timestamp,basepricecustomer,basepricebusiness);

			int toppingFlag = 1;
			ArrayList<Topping> toppingsList = new ArrayList<Topping>();
			while (toppingFlag != -1)
			{
				regexp = "^(-1|1[0-7]|[1-9])$";
				inpValid = false;
				int toppingID=-1;
				while (!inpValid)
				{
					ViewInventoryLevels();
					System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings:");

					line = reader.readLine();
					inpValid = CheckValidInput(regexp, line);
					if(inpValid)
					{
						inpValid = true;
						toppingID = Integer.parseInt(line);
					}
					else
						System.out.println("Provide only valid input");

				}
				if (toppingID != -1)
				{
					Topping t=DBNinja.fetchToppingById(toppingID);
					toppingsList=DBNinja.getInventory();
					System.out.println("Do you want to add extra topping? Enter y/n");
					String extra = reader.readLine();
					if(extra.equals("y") || extra.equals("Y"))
					{
						DBNinja.useTopping(p,t,true);
						p.addToppings(t,true);


						Integer mapping[]={p.getPizzaID(),t.getTopID(),1};
						pizzatopping.add(mapping);
				    }
				    else
				    {
					    DBNinja.useTopping(p,t,false);
					    p.addToppings(t,false);
					    Integer mapping[]={p.getPizzaID(),t.getTopID(),0};
					    pizzatopping.add(mapping);
				    }

				}
				else
					toppingFlag = -1;
				if(toppingFlag!=-1)
				{
					System.out.println("Available Toppings:");
				}
			}

			ArrayList<Integer> discountList = new ArrayList<Integer>();

			String choice = "N";
			inpValid = false;
			regexp = "^([YyNn]?)$";
			while(!inpValid)
			{
				System.out.println("Do you want to add discounts to this pizza? Enter y/n?");
				choice = reader.readLine();
				inpValid = CheckValidInput(regexp, choice);
				if(inpValid)
					inpValid = true;
				else
					System.out.println("Provide only valid input");
			}
			if (choice.equals("Y") || choice.equals("y"))
			{
				int discountflag = 1;
				while (discountflag != -1)
				{
					ArrayList<Discount> disc = new ArrayList<Discount>();
					Discount d=null;
					disc=DBNinja.getDiscountList();
					for (Discount discount:disc)
					{
						System.out.println(discount.toString());
					}
					System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding discounts:");
					int DiscountID = Integer.parseInt(reader.readLine());
					double custPrice1=p.getCustPrice();
					if (DiscountID != -1)
					{
						for (Discount discount:disc)
						{
							if(discount.getDiscountID()==DiscountID)
							{
								d=discount;
							}
						}
						if(d.isPercent())
						{
							p.setCustPrice(custPrice1-((custPrice1*d.getAmount())/100));
						}
						else
						{
							p.setCustPrice(custPrice1 - d.getAmount());;
						}

						Integer mapping[] = {p.getPizzaID(),d.getDiscountID()};
						pizzaDiscountmap.add(mapping);


					}
					else
						discountflag = -1;
				}
			}

			pricetocustomer=pricetocustomer+p.getCustPrice();
			pricetobusiness=pricetobusiness+p.getBusPrice();
			o.setCustPrice(pricetocustomer);
			o.setBusPrice(pricetobusiness);
			if(orderType==2 && orderflag)
			{
				o.setOrderID(0);
				DBNinja.addOrder(o);
				DBNinja.updatePickUp(maxOrderID);
				orderflag=false;
			}
			else if(orderType==3 && orderflag)
			{
				o.setOrderID(0);
				DBNinja.addOrder(o);
				DBNinja.updateDelivery(maxOrderID, customerAddress);
				orderflag=false;
			}
			else
			{
				if(orderType!=1)
				{
					o.setOrderID(maxOrderID);
					DBNinja.addOrder(o);
				}
				else {
					if(orderType==1 && orderflag)
					{
						o.setOrderID(0);
						DBNinja.addOrder(o);
						orderflag=false;
					}
					else{
						o.setOrderID(maxOrderID);
						DBNinja.addOrder(o);
					}

				}
			}
			pizzas.add(p);
			DBNinja.addPizza(p);

			System.out.println("Enter -1 to stop adding pizzas...Enter anything else to continue adding more pizzas to the order.");
			int addPizza = Integer.parseInt(reader.readLine());;

			if(addPizza==-1)
				break;
		}

		System.out.println("Do you want to add discounts to this order? Enter y/n:");
		String ordDischoice = reader.readLine();
		if (ordDischoice.equals("Y") || ordDischoice.equals("y"))
		{
			int discountflag = 1;
			while (discountflag != -1) {
				ArrayList<Discount> discorder=new ArrayList<Discount>();
				Discount d1=null;
				discorder=DBNinja.getDiscountList();
				for (Discount discount:discorder)
				{
					System.out.println(discount.toString());
				}
				System.out.println("Which Order Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts:");
				int DiscountID = Integer.parseInt(reader.readLine());
				double custPrice=o.getCustPrice();

				if (DiscountID != -1)
				{
					for (Discount discount:discorder)
					{
						if(discount.getDiscountID()==DiscountID)
						{
							d1=discount;
						}
					}
					if(d1.isPercent())
					{
						o.setCustPrice(custPrice-((custPrice*d1.getAmount())/100));
					}
					else
					{
						o.setCustPrice(custPrice - d1.getAmount());
					}


					Integer mapping[] = {maxOrderID,d1.getDiscountID()};
					orderDiscountmap.add(mapping);

				}
				else
					discountflag = -1;
			}
		}

		o.setOrderID(maxOrderID);
		DBNinja.addOrder(o);

		for(int i=0;i< pizzatopping.size();i++)
		{
			DBNinja.pizzaToppingConnection(pizzatopping.get(i)[0],pizzatopping.get(i)[1],pizzatopping.get(i)[2]);
		}
		for(int i=0;i< pizzaDiscountmap.size();i++)
		{
			DBNinja.pizzaDiscountConnection(pizzaDiscountmap.get(i)[0],pizzaDiscountmap.get(i)[1]);
		}
		for(int i=0;i< orderDiscountmap.size();i++)
		{
			DBNinja.orderDiscountConnection(orderDiscountmap.get(i)[0],orderDiscountmap.get(i)[1]);
		}
		System.out.println();


		System.out.println("Finished adding order...Returning to menu...");
		conn.close();

/*		System.out.println("ERROR: I don't understand your input for: Is this order an existing customer?");
		System.out.println("Let's build a pizza!");
		System.out.println("Enter -1 to stop adding pizzas...Enter anything else to continue adding pizzas to the order.");
		System.out.println("Do you want to add discounts to this order? Enter y/n?");
		System.out.println("Which Order Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
		System.out.println("What is the House/Apt Number for this order? (e.g., 111)");
		System.out.println("What is the Street for this order? (e.g., Smile Street)");
		System.out.println("What is the City for this order? (e.g., Greenville)");
		System.out.println("What is the State for this order? (e.g., SC)");
		System.out.println("What is the Zip Code for this order? (e.g., 20605)");

		System.out.println("Finished adding order...Returning to menu...");

 */
	}


	public static void viewCustomers() throws SQLException, IOException 
	{
		/*
		 * Simply print out all of the customers from the database. 
		 */
		Connection conn = DBConnector.make_connection();
		if(conn != null) {
			ArrayList<Customer> customers = null;
			customers=DBNinja.getCustomerList();
			for(Customer customer:customers){
				System.out.println(customer.toString());
			}
		}
		conn.close();
	}
	

	// Enter a new customer in the database
	public static void EnterCustomer() throws SQLException, IOException 
	{
		/*
		 * Ask for the name of the customer:
		 *   First Name <space> Last Name
		 * 
		 * Ask for the  phone number.
		 *   (##########) (No dash/space)
		 * 
		 * Once you get the name and phone number, add it to the DB
		 */
		
		// User Input Prompts...
		Connection conn = DBConnector.make_connection();
		System.out.println("Please Enter the Customer name (First Name <space> Last Name):");
		String fullName = reader.readLine();
		String[] names = fullName.trim().split("\\s+");
		System.out.println("What is this customer's phone number (##########) (No dash/space):");
		String phone = reader.readLine();
		Customer customer=new Customer(0,names[0],names[1],phone);
		DBNinja.addCustomer(customer);

	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException 
	{
		/*  
		* This method allows the user to select between three different views of the Order history:
		* The program must display:
		* a.	all open orders
		* b.	all completed orders 
		* c.	all the orders (open and completed) since a specific date (inclusive)
		* 
		* After displaying the list of orders (in a condensed format) must allow the user to select a specific order for viewing its details.  
		* The details include the full order type information, the pizza information (including pizza discounts), and the order discounts.
		* 
		*/
		System.out.println("Would you like to:\n(a) display all orders [open or closed]\n(b) display all open orders\n(c) display all completed [closed] orders\n(d) display orders since a specific date");
		String choice = reader.readLine().trim();
		try  {
			ArrayList<Order> orders = DBNinja.getOrdersByDate(null);
			ArrayList<Integer> Oids= new ArrayList<Integer>();
			for (Order order : orders) {
				Oids.add(order.getOrderID());
			}
			if (choice.equals("a") || choice.equals("A")) {
				for (Order order : orders) {
					System.out.println(order.toSimplePrint());
				}
				if(orders.size()==0)
				{
					System.out.println("No orders to display, returning to menu.");
				}
				else {

					System.out.print("Which order do you wish to see in detail? Enter the number (-1 to exit):\n ");
					int orderID = Integer.parseInt(reader.readLine());
						if (!Oids.contains(orderID) && orderID != -1) {
							System.out.println("Incorrect entry, returning to menu.");

						}
					if (orderID != -1 && Oids.contains(orderID)) {
						Map<Integer, Order> resultMap = orders.stream().collect(Collectors.toMap(x -> x.getOrderID(), x -> x));
						resultMap.get(orderID);
						System.out.println(resultMap.get(orderID).toString());
					}
				}


			}
			else if (choice.equals("b") || choice.equals("B")) {

				orders = DBNinja.getOrdersByDate(0);
				for (Order order : orders) {
					System.out.println(order.toSimplePrint());
				}
				if(orders.size()==0)
				{
					System.out.println("No orders to display, returning to menu.");
				}
				else {
					System.out.print("Which order do you wish to see in detail? Enter the number (-1 to exit):\n ");
					int orderID = Integer.parseInt(reader.readLine());
						if (!Oids.contains(orderID) && orderID != -1) {
							System.out.println("Incorrect entry, returning to menu.");

						}
					if (orderID != -1 && Oids.contains(orderID)) {
						Map<Integer, Order> resultMap = orders.stream().collect(Collectors.toMap(x -> x.getOrderID(), x -> x));
						resultMap.get(orderID);

						System.out.println(resultMap.get(orderID).toString());
					}
				}
			}
			else if (choice.equals("c") || choice.equals("C"))
			{
				orders = DBNinja.getOrdersByDate(1);
				for (Order order : orders) {
					System.out.println(order.toSimplePrint());
				}
				if(orders.size()==0)
				{
					System.out.println("No orders to display, returning to menu.");

				}
				else {
					System.out.print("Which order do you wish to see in detail? Enter the number (-1 to exit):\n ");
					int orderID = Integer.parseInt(reader.readLine());

						if (!Oids.contains(orderID) && orderID != -1) {
							System.out.println("Incorrect entry, returning to menu.");

						}
					if (orderID != -1 && Oids.contains(orderID)) {
						Map<Integer, Order> resultMap = orders.stream().collect(Collectors.toMap(x -> x.getOrderID(), x -> x));
						resultMap.get(orderID);

						System.out.println(resultMap.get(orderID).toString());
					}
				}
			}
			else if (choice.equals("d") || choice.equals("D"))
			{
				System.out.print("What is the date you want to restrict by? (Format=YYYY-MM-DD)\n");
				String dateString = reader.readLine();
				orders = DBNinja.getOrdersByDate(dateString);
				for (Order order : orders) {
					System.out.println(order.toSimplePrint());
				}
				if(orders.size()==0)
				{
					System.out.println("No orders to display, returning to menu.");
				}
				else {
					System.out.print("Which order do you wish to see in detail? Enter the number (-1 to exit):\n ");
					int orderID = Integer.parseInt(reader.readLine());
						if (!Oids.contains(orderID) && orderID != -1) {
							System.out.println("Incorrect entry, returning to menu.");
						}

					if (orderID != -1 && Oids.contains(orderID)) {


						Map<Integer, Order> resultMap = orders.stream().collect(Collectors.toMap(x -> x.getOrderID(), x -> x));
						resultMap.get(orderID);

						System.out.println(resultMap.get(orderID).toString());
					}
				}
			}
			else
			{
				System.out.println("I don't understand that input, returning to menu");
			}
		}catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// User Input Prompts...
		/*
		System.out.println("What is the date you want to restrict by? (FORMAT= YYYY-MM-DD)");
		System.out.println("I don't understand that input, returning to menu");
		System.out.println("Which order would you like to see in detail? Enter the number (-1 to exit): ");
		System.out.println("Incorrect entry, returning to menu.");
		System.out.println("No orders to display, returning to menu.");
		*/
	}

	
	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		/*
		 * All orders that are created through java (part 3, not the orders from part 2) should start as incomplete
		 * 
		 * When this method is called, you should print all of the "opoen" orders marked
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 * 
		 */
		ArrayList<Order> orders = DBNinja.getOrdersByDate(0);
		if(orders.size()!=0) {
			for (Order order : orders) {
				System.out.println(order.toSimplePrint());
			}



			System.out.println("Which order would you like mark as complete? Enter the OrderID: ");
			Integer orderId = Integer.parseInt(reader.readLine());
			Order order = null;
			int index = 0;
			for (int i = 0; i < orders.size(); i++) {
				if (orders.get(i).getOrderID() == orderId) {
					index = i;
				}
			}
			order = orders.get(index);
			DBNinja.completeOrder(order);
		}
		else{
			System.out.println("There are no open orders currently... returning to menu...");
		}



		// User Input Prompts...
		/*
		System.out.println("There are no open orders currently... returning to menu...");
		System.out.println("Which order would you like mark as complete? Enter the OrderID: ");
		System.out.println("Incorrect entry, not an option"); */

		
		

	}

	public static void ViewInventoryLevels() throws SQLException, IOException 
	{
		/*
		 * Print the inventory. Display the topping ID, name, and current inventory
		*/
		DBNinja.printInventory();

	}


	public static void AddInventory() throws SQLException, IOException 
	{
		/*
		 * This should print the current inventory and then ask the user which topping (by ID) they want to add more to and how much to add
		 */

		System.out.println("Current Inventory Levels:");
		Menu.ViewInventoryLevels();
		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
		int toppingID = Integer.parseInt(reader.readLine());
		Topping t= DBNinja.fetchToppingById(toppingID);
		System.out.println("How many units would you like to add? ");
		double quantity = Float.parseFloat(reader.readLine());
		DBNinja.addToInventory(t,quantity);
		
		// User Input Prompts...
		/*
		System.out.println("Which topping do you want to add inventory to? Enter the number: ");
		System.out.println("How many units would you like to add? ");
		System.out.println("Incorrect entry, not an option");
	
		*/
		
		
	}

	// A method that builds a pizza. Used in our add new order method
	public static Pizza buildPizza(int orderID) throws SQLException, IOException 
	{
		
		/*
		 * This is a helper method for first menu option.
		 * 
		 * It should ask which size pizza the user wants and the crustType.
		 * 
		 * Once the pizza is created, it should be added to the DB.
		 * 
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 * 
		 * We then need to add pizza discounts (again, to here and to the database)
		 * 
		 * Once the discounts are added, we can return the pizza
		 */

		Pizza ret = null;
		/*
		// User Input Prompts...
		System.out.println("What size is the pizza?");
		System.out.println("1."+DBNinja.size_s);
		System.out.println("2."+DBNinja.size_m);
		System.out.println("3."+DBNinja.size_l);
		System.out.println("4."+DBNinja.size_xl);
		System.out.println("Enter the corresponding number: ");
		System.out.println("What crust for this pizza?");
		System.out.println("1."+DBNinja.crust_thin);
		System.out.println("2."+DBNinja.crust_orig);
		System.out.println("3."+DBNinja.crust_pan);
		System.out.println("4."+DBNinja.crust_gf);
		System.out.println("Enter the corresponding number: ");
		System.out.println("Available Toppings:");
		System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
		System.out.println("Do you want to add extra topping? Enter y/n");
		System.out.println("We don't have enough of that topping to add it...");
		System.out.println("Which topping do you want to add? Enter the TopID. Enter -1 to stop adding toppings: ");
		System.out.println("Do you want to add discounts to this Pizza? Enter y/n?");
		System.out.println("Which Pizza Discount do you want to add? Enter the DiscountID. Enter -1 to stop adding Discounts: ");
		System.out.println("Do you want to add more discounts to this Pizza? Enter y/n?");
	    */
		return ret;
	}
	
	
	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This method asks the use which report they want to see and calls the DBNinja method to print the appropriate report.
		 * 
		 */

		System.out.println("Which report do you wish to print? Enter\n" +
				"(a) ToppingPopularity\n" +
				"(b) ProfitByPizza\n" +
				"(c) ProfitByOrderType:");
		String option = reader.readLine().trim();

		switch (option) {
			case "a":
				DBNinja.printToppingPopReport();

				break;
			case "b":
				DBNinja.printProfitByPizzaReport();
				break;
			case "c":
				DBNinja.printProfitByOrderType();
				break;
			default:
				System.out.println("I don't understand that input... returning to menu...");
		}
		// User Input Prompts...
	}

	//Prompt - NO CODE SHOULD TAKE PLACE BELOW THIS LINE
	// DO NOT EDIT ANYTHING BELOW HERE, THIS IS NEEDED TESTING.
	// IF YOU EDIT SOMETHING BELOW, IT BREAKS THE AUTOGRADER WHICH MEANS YOUR GRADE WILL BE A 0 (zero)!!

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}


	/*
	 * autograder controls....do not modiify!
	 */

	public final static String autograder_seed = "6f1b7ea9aac470402d48f7916ea6a010";

	
	private static void autograder_compilation_check() {

		try {
			Order o = null;
			Pizza p = null;
			Topping t = null;
			Discount d = null;
			Customer c = null;
			ArrayList<Order> alo = null;
			ArrayList<Discount> ald = null;
			ArrayList<Customer> alc = null;
			ArrayList<Topping> alt = null;
			double v = 0.0;
			String s = "";

			DBNinja.addOrder(o);
			DBNinja.addPizza(p);
			DBNinja.useTopping(p, t, false);
			DBNinja.usePizzaDiscount(p, d);
			DBNinja.useOrderDiscount(o, d);
			DBNinja.addCustomer(c);
			DBNinja.completeOrder(o);
			alo = DBNinja.getOrders(false);
			o = DBNinja.getLastOrder();
			alo = DBNinja.getOrdersByDate("01/01/1999");
			ald = DBNinja.getDiscountList();
			d = DBNinja.findDiscountByName("Discount");
			alc = DBNinja.getCustomerList();
			c = DBNinja.findCustomerByPhone("0000000000");
			alt = DBNinja.getToppingList();
			t = DBNinja.findToppingByName("Topping");
			DBNinja.addToInventory(t, 1000.0);
			v = DBNinja.getBaseCustPrice("size", "crust");
			v = DBNinja.getBaseBusPrice("size", "crust");
			DBNinja.printInventory();
			DBNinja.printToppingPopReport();
			DBNinja.printProfitByPizzaReport();
			DBNinja.printProfitByOrderType();
			s = DBNinja.getCustomerName(0);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}


}


