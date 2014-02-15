


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetCafeGear
 */
public class GetCafeGear extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCafeGear() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String d = request.getParameter("cafe");

		CafeGearDBServlet driver = new CafeGearDBServlet();
		driver.getCafeGear(d);
		
		PrintWriter out = response.getWriter();
		
		Gson gson = new Gson();
		String json = gson.toJson(driver);
		out.write(json);
	}

}
