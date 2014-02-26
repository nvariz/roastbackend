/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to insert/retrieve
 * reviews from the reviews relation in the Roast DB and (in the case of retrieval) receive the query results as JSON.
 * 
 * @author Nicholas Variz
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuReviewDBConnector{
	
	//This result will be used converted to JSON by the calling Servlet
	private ArrayList<String> itemIDs = new ArrayList<String>();
	private ArrayList<String> comments = new ArrayList<String>();
    private ArrayList<String> ratings = new ArrayList<String>();
    private ArrayList<String> users = new ArrayList<String>();

    //Insert a new review entry into the reviews table
    public void insertReview(String itemID, String comments, String rating, String user){
    	
		String updateString = "insert into roast.reviews values('" + itemID + "','" + comments +
				"','" + rating + "','" + user + "')";
		insertIntoDB(updateString);
	}
    
    //Retrieve a review entry from the reviews table, query by itemID
    public void getItemReview(String itemID){
    	
		String query = "SELECT * FROM roast.reviews WHERE itemID = " + itemID;
		retrieveReview(query);
	}
    
    /* 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
	 * 2) Insert new record into DB
	 * 3) Close DB connection
	 * 4) Print number of updated rows (expected: 1) to STDOUT
	 * 
	 * TODO: Implement connection pooling, this implementation is highly inefficient
	 */
    private void insertIntoDB(String updateString){
		Connection con = null;
		Statement stmt = null;

		int rowsUpdated = 0;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rowsUpdated = stmt.executeUpdate(updateString);
		    System.out.println("Updated " + rowsUpdated + " rows in GetReview table");
		    
		   } catch (Exception e) {
		       e.printStackTrace();
			      
		   }finally {
		        try {
		            stmt.close();
		        } catch (Exception e) {
			    } ;
			    
			    try {
			    	con.close();
			    } catch (Exception e) {
			    } ;
			}

	}
    
    /* 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
	 * 2) Execute query passed in from servlet
	 * 3) Call "filterResults" helper method to filter results and add to arraylist
	 * 4) Close DB connection
	 * 
	 * TODO: Implement connection pooling, this implementation is highly inefficient
	 */
	private void retrieveReview(String query){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery(query);
			          
		    String itemID = null;
			String comment = null;
		    String rating = null;
		    String user = null;
			
			if (rs == null)
				return;

			while (rs.next() ) {
		        itemID = rs.getString("itemID");	  
		        itemIDs.add(itemID);
		        comment = rs.getString("comments");	  
		        comments.add(comment);
		        rating = rs.getString("rating");	  
		        ratings.add(rating);
		        user = rs.getString("user");
		        users.add(user);
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

}
