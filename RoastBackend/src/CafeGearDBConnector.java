/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to query the gear
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

public class CafeGearDBConnector extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private ArrayList<String> gearNames = new ArrayList<String>();
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
	private void queryDatabaseForCafeGear(String query, String filter){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery(query);
			          
		    String gearName = null;
			String description = null;
			String price = null;
			String imageName = null;
			String id = null;
			
			if (rs == null)
				return;
			else if(filter == null){
				gearName = rs.getString("gearName");
				gearNames.add(gearName);
			}
			while (rs.next() ) {
		        gearName = rs.getString("gearName");	  
		        gearNames.add(gearName);
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
	 * To Query Database for all gear specified by string "cafe" send HTTP request to: 
	 * "<EC2IP>:8080/roast/GetCafeGear?cafe="<cafe contains search>""
	 * This executes the following MYSQL query: "SELECT * FROM roast.gear where name like '%" + cafe + "%'"
	 * 
	 */
	public void getCafeGear(String cafe){
		String query = "SELECT gearName,description,price,imageName,id FROM roast.gear where shopName like '%" + cafe + "%'";
		queryDatabaseForCafeGear(query, "cafe");
	}
}
