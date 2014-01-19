/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to query the database
 * and receive the query results as JSON.
 * 
 * @author Nicholas Variz
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HttpToSqlAdapter {

	//This result will be used converted to JSON by the calling Servlet
	private ArrayList<String> results = new ArrayList<String>();
	
	/* Common class to query DB via JDBC, performs the following:
	 * 
	 * 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
	 * 2) Execute query passed in from servlet
	 * 3) Call "filterResults" helper method to filter results and add to arraylist
	 * 4) Close DB connection
	 * 
	 * TODO: Implement connection pooling, this implementation is highly inefficient
	 */
	private void queryDatabase(String query, String filter){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery(query);
			          
		    filterResults(results, rs, filter);
		    
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
	 * Given a result set of servlet's MySQL query, add the requested relation fields to arraylist
	 */
	private void filterResults(ArrayList<String> results, ResultSet rs, String filter) throws SQLException{
		
		String name = null;
		
		if (rs == null || filter == null)
			return;
		while (rs.next() ) {
	    	name = rs.getString("name");	  
	        results.add(name);
	   }
		
	}
	
	/*
	 * To Query Database for any drink containing the string "drink" send HTTP request to: 
	 * "<EC2IP>:8080/roast/FindMyDrink?drink="<drink search string>""
	 * This executes the following MYSQL query: "SELECT name FROM roast.drinks where name like '%" + drink + "%'"
	 * 
	 */
	public void findADrink(String drink){
		String query = "SELECT name FROM roast.drinks where name like '%" + drink + "%'";
		queryDatabase(query, "name");
	}
	
	/*
	 * To Query Database for any cafe containing the string "cafe" send HTTP request to: 
	 * "<EC2IP>:8080/roast/FindACafe?cafe="<cafe search string>""
	 * This executes the following MYSQL query: "SELECT name FROM roast.cafes where name like '%" + cafe + "%'"
	 * 
	 */
	public void findACafe(String cafe){
		String query = "SELECT name FROM roast.cafes where name like '%" + cafe + "%'";
		queryDatabase(query, "name");
	}
		
}
