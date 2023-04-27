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

	  private static final long serialVersionUID = 1L;
	  
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
			    throws ServletException, IOException {
			    // get the restaurant id from the request parameter
		  		PrintWriter out = response.getWriter();
			    HttpSession session = request.getSession();
			    String restID = (String) session.getAttribute("restaurant_id");
				String restName = (String) session.getAttribute("restaurant_name");
				String userID = (String) session.getAttribute("user_id");
				String restUrl = (String) session.getAttribute("restaurant_img");

				String result = "{\"restID\":" + "\"" + restID + "\",";
				result += "\"restName\":" + "\"" + restName + "\",";
				result += "\"restUrl\":" + "\"" + restUrl + "\",";
				result += "\"userID\":" +  userID + "}";



			    response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
			    out.println(result);
			    out.flush();
			    out.close();
			    

			    

			  }
}
