/**
 * This servlet allows retrieval  and insertion of tuples from the Roast.reviews relation via a REST API
 * 
 * @author Nicholas Variz
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class GetReview extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemID = request.getParameter("id");

		MenuReviewDBConnector driver = new MenuReviewDBConnector();
		driver.getItemReview(itemID);
		
		PrintWriter out = response.getWriter();
		
		Gson gson = new Gson();
		String json = gson.toJson(driver);
		out.write(json);
	}
}
