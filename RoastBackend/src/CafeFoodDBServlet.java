

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CafeFoodDBServlet
 */
public class CafeFoodDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private ArrayList<String> foodNames = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	private ArrayList<String> prices = new ArrayList<String>();
	private ArrayList<String> types = new ArrayList<String>();
	private ArrayList<String> imageNames = new ArrayList<String>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CafeFoodDBServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			String imageName = null;
			
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
		        imageName = rs.getString("imageName");
		        imageNames.add(imageName);
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
	
	public void getCafeFoods(String cafe){
		String query = "SELECT foodName,description,price,type,imageName FROM roast.food where shopName like '%" + cafe + "%'";
		queryDatabaseForCafeFood(query, "cafe");
	}
}
