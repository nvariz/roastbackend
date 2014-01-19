import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class HttpToSqlAdapter {

	private ArrayList<String> cafes = new ArrayList<String>();
	
	//where f is the name of the food
	public void getCafesWithThisDrink(String drink){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
			
		String name = null;
			
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roast?user=roastapp&password=roastapp");
		    stmt = con.createStatement();
		    rs = stmt.executeQuery("SELECT name FROM roast.drinks where name = '" + drink + "'" );
		    //rs = stmt.executeQuery("SELECT * FROM roast.drinks");
			      
		    while (rs.next() ) {
		    	name = rs.getString("name");	  
		        cafes.add(name);
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
