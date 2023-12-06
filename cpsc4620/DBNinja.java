package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dinein";

	public final static String size_s = "Small";
	public final static String size_m = "Medium";
	public final static String size_l = "Large";
	public final static String size_xl = "XLarge";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";

	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		try {
			String maxOrdSql = "SELECT * FROM ToppingPopularity";
			PreparedStatement prepared = conn.prepareStatement(maxOrdSql);
			ResultSet report = prepared.executeQuery();
			int maxOrderID = -1;
			System.out.printf("%-20s  %-4s %n", "Topping", "ToppingCount");
			while (report.next()) {
				String topping = report.getString("Topping");
				Integer toppingCount = report.getInt("ToppingCount");
				System.out.printf("%-20s  %-4s %n", topping, toppingCount);
			}

			//DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Prints the ToppingPopularity view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static void printProfitByPizzaReport() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		try {
			String maxOrdSql = "SELECT * FROM ProfitByPizza";
			PreparedStatement prepared = conn.prepareStatement(maxOrdSql);
			ResultSet report = prepared.executeQuery();
			System.out.printf("%-15s  %-15s  %-10s %-30s%n", "Pizza Size", "Pizza Crust", "Profit", "LastOrderDate");
			while (report.next()) {

				String size = report.getString("Size");
				String crust = report.getString("Crust");
				Double profit = report.getDouble("Profit");
				String orderDate = report.getString("LastOrderDate");

				System.out.printf("%-15s  %-15s  %-10s %-30s%n", size, crust, profit, orderDate);

			}

			//DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printProfitByOrderType() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */

		try {
			String maxOrdSql = "SELECT * FROM ProfitByOrderType";
			PreparedStatement prepared = conn.prepareStatement(maxOrdSql);
			ResultSet report = prepared.executeQuery();
			System.out.printf("%-15s  %-15s  %-18s %-18s %-8s%n", "OrderType", "Order Month", "TotalOrderPrice",
					"TotalOrderCost", "Profit");
			while (report.next()) {

				String customerType = report.getString("CustomerType");
				String orderMonth = report.getString("OrderMonth");
				Double totalPrice = report.getDouble("TotalOrderPrice");
				Double totalCost = report.getDouble("TotalOrderCost");
				Double profit = report.getDouble("Profit");
				System.out.printf("%-15s  %-15s  %-18s %-18s %-8s%n", customerType, orderMonth, totalPrice,
						totalCost, profit);

			}

			//DO NOT FORGET TO CLOSE YOUR CONNECTION
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static void printInventory() throws SQLException, IOException {
		/*
		 * Queries the database and prints the current topping list with quantities.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 *
		 */
		try {
			connect_to_db();

			String sql = "SELECT ToppingID, ToppingName, ToppingCurInventoryLevel FROM topping order by ToppingID";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();
			System.out.println("ID  Name          CurINVT");
			while (results.next()) {
				System.out.printf("%-3s %-12s %5s\n",results.getString("ToppingID"),results.getString("ToppingName"), results.getString("ToppingCurInventoryLevel"));
			}

			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
		/*
		 * Updates the quantity of the topping in the database by the amount specified.
		 *
		 * */
		try {
			connect_to_db();
			String sql = "UPDATE topping SET ToppingCurInventoryLevel = ToppingCurInventoryLevel+? WHERE ToppingID = ?";
			Connection conn = DBConnector.make_connection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDouble(1, quantity);
			preparedStatement.setInt(2, t.getTopID());
			preparedStatement.executeUpdate();
			/*
			 * Adds toAdd amount of topping to topping t.
			 */
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static Topping fetchToppingById(int toppingID) throws SQLException, IOException {

		connect_to_db();
		Topping t=null;
		try {
			String maxOrdSql = "SELECT * FROM topping where ToppingID = ?";
			PreparedStatement maxOrderstmt = conn.prepareStatement(maxOrdSql);
			maxOrderstmt.setInt(1,toppingID);
			ResultSet results = maxOrderstmt.executeQuery();

			while (results.next()) {
				int tID = results.getInt("ToppingID");
				String toppingName = results.getString("ToppingName");
				double toppingPriceToCustomer = results.getDouble("ToppingPriceToCustomer");
				double toppingPriceToBusiness = results.getDouble("ToppingCostToBusiness");
				double toppingQuantityForPersonal = results.getDouble("ToppingSmall");
				double toppingQuantityForMedium = results.getDouble("ToppingMedium");
				double toppingQuantityForLarge = results.getDouble("ToppingLarge");
				double toppingQuantityForXLarge = results.getDouble("ToppingXLarge");
				int toppingCurrentInvLvl = results.getInt("ToppingCurInventoryLevel");
				int toppingMinInvLvl = results.getInt("ToppingMinInventoryLevel");

				t = new Topping(tID, toppingName, toppingQuantityForPersonal,
						toppingQuantityForMedium, toppingQuantityForLarge, toppingQuantityForXLarge,
						toppingPriceToCustomer, toppingPriceToBusiness, toppingMinInvLvl, toppingCurrentInvLvl);
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	public static ArrayList<Topping> getInventory() throws SQLException, IOException {

		connect_to_db();
		/*
		 * This function actually returns the toppings. The toppings
		 * should be returned in alphabetical order if you don't
		 * plan on using a printInventory function
		 */
		ArrayList<Topping> toppings=new ArrayList<Topping>();

		try {
			String sql = "SELECT * FROM topping";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int toppingID = results.getInt("ToppingID");
				String toppingName = results.getString("ToppingName");
				double toppingPriceToCustomer = results.getDouble("ToppingPriceToCustomer");
				double toppingPriceToBusiness = results.getDouble("ToppingCostToBusiness");
				double toppingQuantityForPersonal = results.getDouble("ToppingSmall");
				double toppingQuantityForMedium = results.getDouble("ToppingMedium");
				double toppingQuantityForLarge = results.getDouble("ToppingLarge");
				double toppingQuantityForXLarge = results.getDouble("ToppingXLarge");
				int toppingCurrentInvLvl = results.getInt("ToppingCurInventoryLevel");
				int toppingMinInvLvl = results.getInt("ToppingMinInventoryLevel");

				toppings.add(new Topping(toppingID, toppingName, toppingQuantityForPersonal,
						toppingQuantityForMedium, toppingQuantityForLarge, toppingQuantityForXLarge,
						toppingPriceToCustomer, toppingPriceToBusiness, toppingMinInvLvl, toppingCurrentInvLvl));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return toppings;
	}


	public static void dineIn(int orderId,int tableNumber) throws SQLException, IOException {
		connect_to_db();
		try {
			String sql = "SET FOREIGN_KEY_CHECKS=0;";
			PreparedStatement preparedStatement1 = conn.prepareStatement(sql);
			preparedStatement1.executeUpdate();
			String insertStatement = "INSERT INTO dinein" + "(DineinOrderID,DineinTableNumber) " + "VALUES (?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

			preparedStatement.setInt(1, orderId);
			preparedStatement.setInt(2, tableNumber);
			preparedStatement.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		/*
		 * Query the database fro the base customer price for that size and crust pizza.
		 *
		 */
		try {
			String selectQuery = "select * from baseprice;";


			PreparedStatement statement = conn.prepareStatement(selectQuery);
			ResultSet record = statement.executeQuery(selectQuery);
			while (record.next()) {
				String crusttype = record.getString("BasepriceCrustType");
				String sizebase = record.getString("BasepriceSize");


				if (crusttype.equals(crust) && sizebase.equals(size)) {

					bp = record.getDouble("BasepriceToCustomer");
				}
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return bp;
	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		/*
		 * Query the database fro the base business price for that size and crust pizza.
		 *
		 */
		try {
			String selectQuery = "select * from baseprice;";


			PreparedStatement statement = conn.prepareStatement(selectQuery);
			ResultSet record = statement.executeQuery(selectQuery);
			while (record.next()) {
				String crusttype = record.getString("BasepriceCrustType");
				String sizebase = record.getString("BasepriceSize");


				if (crusttype.equals(crust) && sizebase.equals(size)) {

					bp = record.getDouble("BasepriceToBusiness");
				}
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return bp;
	}

	public static int getMaxPizzaID() throws SQLException, IOException {
		int maxOrderID = -1;
		try {
			connect_to_db();
			String maxOrdSql = "SELECT * FROM pizza where PizzaID = (SELECT MAX(PizzaID) from pizza)";
			PreparedStatement maxOrderstmt = conn.prepareStatement(maxOrdSql);
			ResultSet maxOrder = maxOrderstmt.executeQuery();

			while (maxOrder.next())
				maxOrderID = Integer.parseInt(maxOrder.getString("PizzaID"));

		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return maxOrderID;

		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You wont need to implement this function if you didn't forget to do that
		 */

	}

	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}


	public static void addOrder(Order o) throws SQLException, IOException
	{
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 * 
		 */
		try{
			connect_to_db();
			if(o.getOrderID()==0) {
				String sql = "insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer,OrdertTotalCostToBusiness,OrdertType,IsCompleted) values(?, ?, ?,?,?,?)";

				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, o.getCustID());
				preparedStatement.setString(2, o.getDate());
				preparedStatement.setDouble(3, o.getCustPrice());
				preparedStatement.setDouble(4, o.getBusPrice());
				preparedStatement.setString(5, o.getOrderType());
				preparedStatement.setInt(6, o.getIsComplete());
				preparedStatement.executeUpdate();
			}
			else{

				String updateStatement = "update ordert set OrdertTotalPriceToCustomer=?,OrdertTotalCostToBusiness=?, OrdertType=? where OrdertID=? " ;

				PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

				preparedStatement.setDouble(1, o.getCustPrice());
				preparedStatement.setDouble(2, o.getBusPrice());
				preparedStatement.setString(3, o.getOrderType());
				preparedStatement.setInt(4, o.getOrderID());

				preparedStatement.executeUpdate();
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}


		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void addPizza(Pizza p) throws SQLException, IOException
	{
		/*
		 * Add the code needed to insert the pizza into into the database.
		 * Keep in mind adding pizza discounts and toppings associated with the pizza,
		 * there are other methods below that may help with that process.
		 * 
		 */
		try {
			connect_to_db();
			String sql = "insert into pizza(PizzaOrderID, PizzaCost, PizzaPrice,PizzaKitchenStatus,PizzaCrustType,PizzaSize) values(?, ?, ?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, p.getOrderID());
			preparedStatement.setDouble(2, p.getBusPrice());
			preparedStatement.setDouble(3, p.getCustPrice());
			preparedStatement.setString(4, p.getPizzaState());
			preparedStatement.setString(5, p.getCrustType());
			preparedStatement.setString(6, p.getSize());
			preparedStatement.executeUpdate();


			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	
	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{
		try {
			connect_to_db();
			/*
			 * This method should do 2 two things.
			 * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
			 * - connect the topping to the pizza
			 *   What that means will be specific to your yimplementatinon.
			 *
			 * Ideally, you should't let toppings go negative....but this should be dealt with BEFORE calling this method.
			 *
			 */
			String sql;
			if (p.getSize().equals("Small")) {

				if (isDoubled)
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingSmall*2 where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
				else
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingSmall where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
			} else if (p.getSize().equals("Medium")) {

				if (isDoubled)
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingMedium*2 where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
				else
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingMedium where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
			} else if (p.getSize().equals("Large")) {

				if (isDoubled)
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingLarge*2 where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
				else
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingLarge where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
			} else {
				if (isDoubled)
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingXLarge*2 where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
				else
					sql = "update topping set ToppingCurInventoryLevel = ToppingCurInventoryLevel - ToppingXLarge where ToppingID in (select PizzaToppingToppingID from pizzatopping where PizzaToppingPizzaID=?)";
			}
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, p.getPizzaID());
			preparedStatement.executeUpdate();
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	
	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		try {
			String sql = "insert into pizzadiscount(PizzadiscountPizzaId, PizzadiscountDiscountID) values(?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, p.getPizzaID());
			preparedStatement.setInt(2, d.getDiscountID());

			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{

		connect_to_db();
		try {
			String sql = "insert into orderdiscount(OrderdiscountOrdertID, OrderdiscountDiscountID) values(?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, o.getOrderID());
			preparedStatement.setInt(2, d.getDiscountID());

			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void addCustomer(Customer c) throws SQLException, IOException {
		/*
		 * This method adds a new customer to the database.
		 * 
		 */
		try {
			connect_to_db();
			String sql = "insert into customer(CustomerFirstName, CustomerLastName, CustomerPhone) values(?, ?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, c.getFName());
			preparedStatement.setString(2, c.getLName());
			preparedStatement.setString(3, c.getPhone());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static ArrayList<Order> getOrders(boolean status) throws SQLException, IOException {
		/*
		 * Return an arraylist of all of the orders.
		 * 	openOnly == true => only return a list of open (ie orders that have not been marked as completed)
		 *           == false => return a list of all the orders in the database
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 * 
		 * Don't forget to order the data coming from the database appropriately.
		 * 
		 */
		ArrayList<Order> orders = new ArrayList<Order>();

		try {
			connect_to_db();

			String selectQuery = "select * from ordert";
			if (status==false) {
				selectQuery += " where IsCompleted = " + status;
			}
			selectQuery += " order by OrdertTimeStamp desc;";

			Statement statement = conn.createStatement();

			ResultSet record = statement.executeQuery(selectQuery);

			while (record.next()) {
				Integer orderId = record.getInt("OrdertID");
				String orderType = record.getString("OrdertType");
				Integer customerId = record.getInt("OrdertCustomerID");
				Double orderCost = record.getDouble("OrdertTotalPriceToCustomer");
				Double orderPrice = record.getDouble("OrdertTotalCostToBusiness");
				String orderTimeStamp = record.getString("OrdertTimeStamp");
				Integer OrderCompleteState = record.getInt("IsCompleted");

				orders.add(
						new Order(orderId, customerId, orderType, orderTimeStamp, orderCost, orderPrice, OrderCompleteState));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return orders;

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static Order getLastOrder() throws SQLException, IOException{
		/*
		 * Query the database for the LAST order added
		 * then return an Order object for that order.
		 * NOTE...there should ALWAYS be a "last order"!
		 */
		connect_to_db();
		int maxOrderID = -1;
		Order last=null;
		try {
			String maxOrdSql = "SELECT * FROM ordert where OrdertID = (SELECT MAX(OrdertID) from ordert)";
			PreparedStatement maxOrderstmt = conn.prepareStatement(maxOrdSql);
			ResultSet record = maxOrderstmt.executeQuery();
			String orderType="",orderTimeStamp="";
			Integer customerId=0,OrderCompleteState=0;
			Double orderCost=0.0,orderPrice=0.0;


			while (record.next()) {
				maxOrderID = record.getInt("OrdertID");
				orderType = record.getString("OrdertType");
				customerId = record.getInt("OrdertCustomerID");
				orderCost = record.getDouble("OrdertTotalPriceToCustomer");
				orderPrice = record.getDouble("OrdertTotalCostToBusiness");
				orderTimeStamp = record.getString("OrdertTimeStamp");
				OrderCompleteState = record.getInt("IsCompleted");
			}

			maxOrderID = maxOrderID + 1;
			last = new Order(maxOrderID,customerId, orderType, orderTimeStamp, orderCost, orderPrice, OrderCompleteState);

			//DO NOT FORGET TO CLOSE YOUR CONNECTION
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return last;
	}

	public static int getNextOrderID() throws SQLException, IOException {
		/*
		 * A helper function I had to use because I forgot to make
		 * my OrderID auto increment...You can remove it if you
		 * did not forget to auto increment your orderID.
		 */
		connect_to_db();
		int maxOrderID = -1;
		try {
			String maxOrdSql = "SELECT * FROM ordert where OrdertID = (SELECT MAX(OrdertID) from ordert)";
			PreparedStatement maxOrderstmt = conn.prepareStatement(maxOrdSql);
			ResultSet maxOrder = maxOrderstmt.executeQuery();

			while (maxOrder.next()) {
				maxOrderID = Integer.parseInt(maxOrder.getString("OrdertID"));
			}

			maxOrderID = maxOrderID + 1;

			//DO NOT FORGET TO CLOSE YOUR CONNECTION
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxOrderID;
	}

	public static void pizzaToppingConnection(Integer pizzaId, Integer toppingId, Integer isComplete) throws SQLException, IOException {
		connect_to_db();
		try {
			String sql = "insert into pizzatopping(PizzaToppingPizzaID, PizzaToppingToppingID, PizzaToppingExtraTopping) values(?, ?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, pizzaId);
			preparedStatement.setInt(2, toppingId);
			preparedStatement.setInt(3, isComplete);
			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void pizzaDiscountConnection(Integer pizzaId, Integer discountId)throws SQLException, IOException {

		connect_to_db();
		try {
			String sql = "insert into pizzadiscount(PizzadiscountPizzaId, PizzadiscountDiscountID) values(?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, pizzaId);
			preparedStatement.setInt(2, discountId);

			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	public static void orderDiscountConnection(Integer orderId, Integer discountId) throws SQLException, IOException {

		connect_to_db();
		try {
			String sql = "insert into orderdiscount(OrderdiscountOrdertID, OrderdiscountDiscountID) values(?, ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);
			preparedStatement.setInt(2, discountId);

			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static ArrayList<Order> getOrdersByDate(String date){
		/*
		 * Query the database for ALL the orders placed on a specific date
		 * and return a list of those orders.
		 *  
		 */
		ArrayList<Order> orders = new ArrayList<Order>();

		try {
			connect_to_db();

			String selectQuery = "select * from ordert";
			if (date != null) {
				selectQuery += " where (OrdertTimeStamp >= '" + date + " 00:00:00')";
			}
			selectQuery += " order by OrdertTimeStamp desc;";

			Statement statement = conn.createStatement();

			ResultSet record = statement.executeQuery(selectQuery);

			while (record.next()) {
				Integer orderId = record.getInt("OrdertID");
				String orderType = record.getString("OrdertType");
				Integer customerId = record.getInt("OrdertCustomerID");
				Double orderCost = record.getDouble("OrdertTotalPriceToCustomer");
				Double orderPrice = record.getDouble("OrdertTotalCostToBusiness");
				String orderTimeStamp = record.getString("OrdertTimeStamp");
				Integer OrderCompleteState = record.getInt("IsCompleted");

				orders.add(
						new Order(orderId, customerId, orderType, orderTimeStamp, orderCost, orderPrice, OrderCompleteState));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orders;
	}
		
	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		/* 
		 * Query the database for all the available discounts and 
		 * return them in an arrayList of discounts.
		 * 
		*/
		ArrayList<Discount> discs = new ArrayList<Discount>();
		connect_to_db();
		//returns a list of all the discounts.
		try {
			String getDiscountssql = "SELECT * FROM discount";
			PreparedStatement dpreparedStatement = conn.prepareStatement(getDiscountssql);
			ResultSet discount = dpreparedStatement.executeQuery();
			while (discount.next()) {
				int discountID = discount.getInt("DiscountID");
				String discountName = discount.getString("DiscountName");
				boolean isPercent = discount.getBoolean("DiscountPercentage");
				double amount = discount.getDouble("DiscountDollars");
				discs.add(new Discount(discountID, discountName, amount, isPercent));
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return discs;
	}

	public static ArrayList<Order> getOrdersByDate(String date) throws SQLException, IOException {
		ArrayList<Order> orders = new ArrayList<Order>();

		try {
			connect_to_db();

			String selectQuery = "select * from ordert";
			if (date != null) {
				selectQuery += " where (OrdertTimeStamp >= '" + date + " 00:00:00')";
			}
			selectQuery += " order by OrdertID;";

			Statement statement = conn.createStatement();

			ResultSet record = statement.executeQuery(selectQuery);

			while (record.next()) {
				Integer orderId = record.getInt("OrdertID");
				String orderType = record.getString("OrdertType");
				Integer customerId = record.getInt("OrdertCustomerID");
				Double orderCost = record.getDouble("OrdertTotalPriceToCustomer");
				Double orderPrice = record.getDouble("OrdertTotalCostToBusiness");
				String orderTimeStamp = record.getString("OrdertTimeStamp");
				Integer OrderCompleteState = record.getInt("IsCompleted");

				orders.add(
						new Order(orderId, customerId, orderType, orderTimeStamp, orderCost, orderPrice, OrderCompleteState));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}






		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 *
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */


		//DO NOT FORGET TO CLOSE YOUR CONNECTION

		return orders;
	}

	public static ArrayList<Order> getOrdersByDate(int status) throws SQLException, IOException {
		ArrayList<Order> orders = new ArrayList<Order>();

		try {
			connect_to_db();

			String selectQuery = "select * from ordert";
			if (status==0) {
				selectQuery += " where IsCompleted = " + status;
			}
			selectQuery += " order by OrdertID;";

			Statement statement = conn.createStatement();

			ResultSet record = statement.executeQuery(selectQuery);

			while (record.next()) {
				Integer orderId = record.getInt("OrdertID");
				String orderType = record.getString("OrdertType");
				Integer customerId = record.getInt("OrdertCustomerID");
				Double orderCost = record.getDouble("OrdertTotalPriceToCustomer");
				Double orderPrice = record.getDouble("OrdertTotalCostToBusiness");
				String orderTimeStamp = record.getString("OrdertTimeStamp");
				Integer OrderCompleteState = record.getInt("IsCompleted");

				orders.add(
						new Order(orderId, customerId, orderType, orderTimeStamp, orderCost, orderPrice, OrderCompleteState));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}



		return orders;


		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 *
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */


		//DO NOT FORGET TO CLOSE YOUR CONNECTION

	}

	public static Discount findDiscountByName(String name) throws SQLException, IOException{
		/*
		 * Query the database for a discount using it's name.
		 * If found, then return an OrderDiscount object for the discount.
		 * If it's not found....then return null
		 *  
		 */
		Discount disc = null;
		connect_to_db();
		//returns a list of all the discounts.
		try {
			String getDiscountssql = "SELECT * FROM discount where DiscountName=?";
			PreparedStatement dpreparedStatement = conn.prepareStatement(getDiscountssql);
			dpreparedStatement.setString(1,name);
			ResultSet discount = dpreparedStatement.executeQuery();
			int discountID=0;
			String discountName="";
			boolean isPercent=false;
			double amount=0.0;
			while (discount.next()) {
				discountID = discount.getInt("DiscountID");
				discountName = discount.getString("DiscountName");
				isPercent = discount.getBoolean("DiscountPercentage");
				amount = discount.getDouble("DiscountDollars");
			}
			disc = new Discount(discountID, discountName, amount, isPercent);
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return disc;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {

		/*
		 * Query the data for all the customers and return an arrayList of all the customers. 
		 * Don't forget to order the data coming from the database appropriately.
		 * 
		*/
		ArrayList<Customer> custs = new ArrayList<Customer>();
		connect_to_db();
		try {
			String sql = "SELECT * FROM customer";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			ResultSet records = preparedStatement.executeQuery();
			while (records.next()) {
				int custID = records.getInt("CustomerID");
				String fName = records.getString("CustomerFirstName");
				String lName = records.getString("CustomerLastName");
				String phone = records.getString("CustomerPhone");

				custs.add(
						new Customer(custID, fName, lName, phone));

			}

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return custs;
	}

	public static void updatePickUp(int orderId) throws SQLException, IOException {

		connect_to_db();
		try {
			String insertStatement = "INSERT INTO pickup" + "(PickupOrderID) " + "VALUES (?)";



			PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateDelivery(int orderId, String customerAddress)throws SQLException, IOException {
		connect_to_db();
		try {
			String insertStatement = "INSERT INTO delivery" + "(DeliveryOrderID,DeliveryAddress) " + "VALUES (?,?)";


			PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

			preparedStatement.setInt(1, orderId);
			preparedStatement.setString(2, customerAddress);
			preparedStatement.executeUpdate();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Customer findCustomerByPhone(String phoneNumber) throws SQLException, IOException{
		/*
		 * Query the database for a customer using a phone number.
		 * If found, then return a Customer object for the customer.
		 * If it's not found....then return null
		 *
		 */
		Customer custs = null;
		connect_to_db();
		try {
			String sql = "SELECT * FROM customer where CustomerPhone = ?;";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,phoneNumber);
			ResultSet records = preparedStatement.executeQuery();
			while (records.next()) {
				int custID = records.getInt("CustomerID");
				String fName = records.getString("CustomerFirstName");
				String lName = records.getString("CustomerLastName");
				String phone = records.getString("CustomerPhone");

				custs = new Customer(custID, fName, lName, phone);

			}

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return custs;
	}

	public static void completeOrder(Order o) throws SQLException, IOException {

		try {
			connect_to_db();

			String updateStatement = "update ordert set IsCompleted = 1 where OrdertID = " + o.getOrderID() + " ;";

			PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

			preparedStatement.executeUpdate();
			String updatePizzaStatement = "update pizza set PizzaKitchenStatus = ?  where PizzaOrderID = ?";

			PreparedStatement pizzaPreparedStatement = conn.prepareStatement(updatePizzaStatement);
			pizzaPreparedStatement.setString(1, "Completed");
			pizzaPreparedStatement.setInt(2, o.getOrderID());

			pizzaPreparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for the aviable toppings and
		 * return an arrayList of all the available toppings.
		 * Don't forget to order the data coming from the database appropriately.
		 *
		 */
			ArrayList<Topping> toppings=new ArrayList<Topping>();

			try {
				String sql = "SELECT * FROM topping where ToppingCurInventoryLevel>=0";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				ResultSet results = preparedStatement.executeQuery();

				while (results.next()) {
					int toppingID = results.getInt("ToppingID");
					String toppingName = results.getString("ToppingName");
					double toppingPriceToCustomer = results.getDouble("ToppingPriceToCustomer");
					double toppingPriceToBusiness = results.getDouble("ToppingCostToBusiness");
					double toppingQuantityForPersonal = results.getDouble("ToppingSmall");
					double toppingQuantityForMedium = results.getDouble("ToppingMedium");
					double toppingQuantityForLarge = results.getDouble("ToppingLarge");
					double toppingQuantityForXLarge = results.getDouble("ToppingXLarge");
					int toppingCurrentInvLvl = results.getInt("ToppingCurInventoryLevel");
					int toppingMinInvLvl = results.getInt("ToppingMinInventoryLevel");

					toppings.add(new Topping(toppingID, toppingName, toppingQuantityForPersonal,
							toppingQuantityForMedium, toppingQuantityForLarge, toppingQuantityForXLarge,
							toppingPriceToCustomer, toppingPriceToBusiness, toppingMinInvLvl, toppingCurrentInvLvl));
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return toppings;
	}

	public static Topping findToppingByName(String name) throws SQLException, IOException{
		/*
		 * Query the database for the topping using it's name.
		 * If found, then return a Topping object for the topping.
		 * If it's not found....then return null
		 */
		ArrayList<Topping> toppings=new ArrayList<Topping>();
		connect_to_db();
		try {
			String sql = "SELECT * FROM topping where ToppingName=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,name);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				int toppingID = results.getInt("ToppingID");
				String toppingName = results.getString("ToppingName");
				double toppingPriceToCustomer = results.getDouble("ToppingPriceToCustomer");
				double toppingPriceToBusiness = results.getDouble("ToppingCostToBusiness");
				double toppingQuantityForPersonal = results.getDouble("ToppingSmall");
				double toppingQuantityForMedium = results.getDouble("ToppingMedium");
				double toppingQuantityForLarge = results.getDouble("ToppingLarge");
				double toppingQuantityForXLarge = results.getDouble("ToppingXLarge");
				int toppingCurrentInvLvl = results.getInt("ToppingCurInventoryLevel");
				int toppingMinInvLvl = results.getInt("ToppingMinInventoryLevel");

				toppings.add(new Topping(toppingID, toppingName, toppingQuantityForPersonal,
						toppingQuantityForMedium, toppingQuantityForLarge, toppingQuantityForXLarge,
						toppingPriceToCustomer, toppingPriceToBusiness, toppingMinInvLvl, toppingCurrentInvLvl));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return toppings.get(0);
	}


	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
	/*
		 * This is a helper method to fetch and format the name of a customer
		 * based on a customer ID. This is an example of how to interact with 
		 * your database from Java.  It's used in the model solution for this project...so the code works!
		 * 
		 * OF COURSE....this code would only work in your application if the table & field names match!
		 *
		 */
		String cname1 = "";
		try {
			connect_to_db();

			/*
			 * an example query using a constructed string...
			 * remember, this style of query construction could be subject to sql injection attacks!
			 *
			 */

			String query = "Select CustomerFirstName, CustomerLastName From customer WHERE CustomerID=" + CustID + ";";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next()) {
				cname1 = rset.getString(1) + " " + rset.getString(2);
			}

			/*
			 * an example of the same query using a prepared statement...
			 *
			 */
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cname1; // OR cname2
	}

	/*
	 * The next 3 private methods help get the individual components of a SQL datetime object. 
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}

	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		if(getYear(dateOfOrder) > year)
			return true;
		else if(getYear(dateOfOrder) < year)
			return false;
		else
		{
			if(getMonth(dateOfOrder) > month)
				return true;
			else if(getMonth(dateOfOrder) < month)
				return false;
			else
			{
				if(getDay(dateOfOrder) >= day)
					return true;
				else
					return false;
			}
		}
	}


}