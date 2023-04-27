import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user_id")
public class UserIDServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get the session object
	    HttpSession session = request.getSession();

	    // Get the user_id from the session
	    int user_id = (int) session.getAttribute("user_id");
	    double balance = (double) session.getAttribute("balance");

	    //String user_id_str = Integer.toString(user_id);
		String json = "{\"data\":[{\"id\":" + user_id + ", \"balance\":" + balance + "}]}";
	    
	    // Set the content type of the response
	    response.setContentType("application/json");

	    // Write the user_id to the response output stream
	    PrintWriter out = response.getWriter();
	    out.println(json);
	    //out.println(balance);
        out.flush();
        out.close();
	}
}

