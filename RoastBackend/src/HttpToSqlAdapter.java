/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to query the database
 * and receive the query results as JSON.
 * 
 * @author Nicholas Variz
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HttpToSqlAdapter {

	//This result will be used converted to JSON by the calling Servlet
	private ArrayList<String> drinkNames = new ArrayList<String>();
	private ArrayList<String> foodNames = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	private ArrayList<String> prices = new ArrayList<String>();
	private ArrayList<String> types = new ArrayList<String>();

	/* Common class to query DB via JDBC, performs the following:
	 * 
	 * 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
	 * 2) Execute query passed in from servlet
	 * 3) Call "filterResults" helper method to filter results and add to arraylist
	 * 4) Close DB connection
	 * 
	 * TODO: Implement connection pooling, this implementation is highly inefficient
	 */
	private void queryDatabaseForCafeDrinks(String query, String filter){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery(query);
			          
		    String drinkName = null;
			String description = null;
			String price = null;
			String type = null;
			
			if (rs == null)
				return;
			else if(filter == null){
				drinkName = rs.getString("drinkName");
				drinkNames.add(drinkName);
			}
			while (rs.next() ) {
		        drinkName = rs.getString("drinkName");	  
		        drinkNames.add(drinkName);
		        description = rs.getString("description");	  
		        descriptions.add(description);
		        price = rs.getString("price");	  
		        prices.add(price);
		        type = rs.getString("type");	  
		        types.add(type);
		   }
		    
		   } catch (Exception e) {
		       e.printStackTrace();
			      
		   }finally {
		        try {
		            stmt.close();
		        } catch (Exception e) {
			    } ;
			    try {
			        rs.close();
			    } catch (Exception e) {
			    };
			    try {
			    	con.close();
			    } catch (Exception e) {
			    } ;
			}

	}

	private void queryDatabaseForCafeFood(String query, String filter){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery(query);
			          
		    String foodName = null;
			String description = null;
			String price = null;
			String type = null;
			
			if (rs == null)
				return;
			else if(filter == null){
				foodName = rs.getString("foodName");
				foodNames.add(foodName);
			}
			while (rs.next() ) {
		        foodName = rs.getString("foodName");	  
		        foodNames.add(foodName);
		        description = rs.getString("description");	  
		        descriptions.add(description);
		        price = rs.getString("price");	  
		        prices.add(price);
		        type = rs.getString("type");	  
		        types.add(type);
		   }
		    
		   } catch (Exception e) {
		       e.printStackTrace();
			      
		   }finally {
		        try {
		            stmt.close();
		        } catch (Exception e) {
			    } ;
			    try {
			        rs.close();
			    } catch (Exception e) {
			    };
			    try {
			    	con.close();
			    } catch (Exception e) {
			    } ;
			}

	}
	//Generic Insert Record into database
	/*private void insertDatabase(String record){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String command = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    command = "insert into roast.drinks values('" + record + "')";
		    rs = stmt.executeQuery(command);
		    
		   } catch (Exception e) {
		       e.printStackTrace();
			      
		   }finally {
		        try {
		            stmt.close();
		        } catch (Exception e) {
			    } ;
			    try {
			        rs.close();
			    } catch (Exception e) {
			    };
			    try {
			    	con.close();
			    } catch (Exception e) {
			    } ;
			}

	}
	
	//Insert a new Drink into the database
	public void insertDrink(String name){
		insertDatabase(name);
	}
	*/
	
	
	/*
	 * To Query Database for any drink containing the string "drink" send HTTP request to: 
	 * "<EC2IP>:8080/roast/GetCafeDrinks?cafe="<cafe contains search>""
	 * This executes the following MYSQL query: "SELECT * FROM roast.drinks where name like '%" + cafe + "%'"
	 * 
	 */
	public void getCafeDrinks(String cafe){
		String query = "SELECT drinkName,description,price,type FROM roast.drinks where shopName like '%" + cafe + "%'";
		queryDatabaseForCafeDrinks(query, "cafe");
	}
	
	public void getCafeFoods(String cafe){
		String query = "SELECT foodName,description,price,type FROM roast.food where shopName like '%" + cafe + "%'";
		queryDatabaseForCafeFood(query, "cafe");
	}

}
