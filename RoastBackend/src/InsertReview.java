/**
 * This servlet allows retrieval  and insertion of tuples from the Roast.reviews relation via a REST API
 * 
 * @author Nicholas Variz
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertReview extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemID = request.getParameter("id");
		String comments = request.getParameter("comments");
		String rating = request.getParameter("rating");
		String user = request.getParameter("user");
			
		MenuReviewDBConnector driver = new MenuReviewDBConnector();
		driver.insertReview(itemID, comments, rating, user);
	}
}
