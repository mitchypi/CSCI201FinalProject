

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	public static User user = null;
	private static final long serialVersionUID = 2L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    response.setContentType("text/plain");
	    PrintWriter pw = response.getWriter();
	    JDBCConnector JDBCConnector = new JDBCConnector();

	    
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
        if (username == null || password == null || 
        		username.isEmpty() || password.isEmpty()) {
        	System.out.println("Missing Field");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("All fields are required.");
            return;
        }
        
        int result = 0;
		try {
			result = JDBCConnector.loginUser(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        
        if (result == -1) {
        	System.out.println("-1");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Username does not exist.");
            return;
        } else if (result == -2) {
        	System.out.println("-2");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Incorrect Password.");
            return;
        } else if (result > 0) {
    		double balance  = JDBCConnector.getBalance(result);
    		user = new User(result, balance);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
        	System.out.println("-3");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Bad Connection.");
        }
	}
}
