import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * This class resides in webapps/roast/WEB-INF/classes on Tomcat server and allows servlets to query the carouselpics
 * relation in the Roast DB and receive the query results as JSON.
 * 
 * @author Nicholas Variz
 */

public class CafeCarouselFilenames {
	
		//This result will be used converted to JSON by the calling Servlet
		private ArrayList<String> filenames = new ArrayList<String>();

		/* 1) Connect to MySql database on port 3306 using user/pass = roastapp/roastapp
		 * 2) Execute query passed in from servlet
		 * 3) Call "filterResults" helper method to filter results and add to arraylist
		 * 4) Close DB connection
		 * 
		 * TODO: Implement connection pooling, this implementation is highly inefficient
		 */
		private void queryDatabaseForPicFilenames(String query, String filter){
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try{

				Class.forName("com.mysql.jdbc.Driver").newInstance();
			    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
			    stmt = con.createStatement();
			    rs = stmt.executeQuery(query);
				          
			    String filename = null;
				
				if (rs == null)
					return;
				else if(filter == null){}
				
				while (rs.next() ) {
			        filename = rs.getString("filename");	  
			        filenames.add(filename);
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
		 * To Query Database for all pic filenames for a given cafe specified by string "cafe" send HTTP request to: 
		 * "<EC2IP>:8080/roast/GetCarouselFilenames?cafe"<cafe contains search>""
		 * This executes the following MYSQL query: "SELECT * FROM roast.carouselpics where name like '%" + cafe + "%'"
		 * 
		 */
		public void getCarouselFilenames(String cafe){
			String query = "SELECT filename FROM roast.carouselpics where cafe like '%" + cafe + "%'";
			queryDatabaseForPicFilenames(query, "cafe");
		}
}
