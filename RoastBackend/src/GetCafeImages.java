/**
 * This servlet allows retrieval of images from Roast EC2 server via REST API
 * 
 * @author Nicholas Variz
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCafeImages
 */
public class GetCafeImages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext servletContext = getServletContext();
			String fName = request.getParameter("image");
			FileInputStream inputStream = new FileInputStream(new File(servletContext.getRealPath("images/" + fName)));
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			response.setContentType("image/jpeg");
			BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
			for (int data; (data = bis.read()) > -1;) {
				output.write(data);
			}
			output.flush();
			bis.close();
			output.close();
		} catch (IOException e) {
			
		}

	}
}
