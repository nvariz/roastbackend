/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to query the food
 * relation in the Roast DB and receive the query results as JSON.
 * 
 * @author Nicholas Variz
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;

public class CafeFoodDBConnector extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private ArrayList<String> foodNames = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	private ArrayList<String> prices = new ArrayList<String>();
	private ArrayList<String> imageNames = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();

	/* 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
	 * 2) Execute query passed in from servlet
	 * 3) Call "filterResults" helper method to filter results and add to arraylist
	 * 4) Close DB connection
	 * 
	 * TODO: Implement connection pooling, this implementation is highly inefficient
	 */
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
			String imageName = null;
			String id = null;
			
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
		        imageName = rs.getString("imageName");
		        imageNames.add(imageName);
		        id = rs.getString("id");
		        ids.add(id);
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
	
	/*
	 * To Query Database for all food specified by string "cafe" send HTTP request to: 
	 * "<EC2IP>:8080/roast/GetCafeFoods?cafe="<cafe contains search>""
	 * This executes the following MYSQL query: "SELECT * FROM roast.food where name like '%" + cafe + "%'"
	 * 
	 */
	public void getCafeFoods(String cafe){
		String query = "SELECT foodName,description,price,imageName,id FROM roast.food where shopName like '%" + cafe + "%'";
		queryDatabaseForCafeFood(query, "cafe");
	}
}
