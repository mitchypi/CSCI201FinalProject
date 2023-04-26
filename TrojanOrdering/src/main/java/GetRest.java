import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetRest")
public class GetRest extends HttpServlet {
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
			    throws ServletException, IOException {
			    // get the restaurant id from the request parameter
		  		PrintWriter out = response.getWriter();
			    HttpSession session = request.getSession();
			    String restID = (String) session.getAttribute("restaurant_id");
			    response.setContentType("text/plain");
			    out.println(restID);
			    out.flush();
			    out.close();
			    

			    

			  }
}
