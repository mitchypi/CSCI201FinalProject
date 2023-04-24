

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/plain");
	    PrintWriter pw = response.getWriter();

	    	    
	    String email = request.getParameter("email");
	    String username = request.getParameter("new-username");
	    String password = request.getParameter("new-password");
	    String confirmPassword = request.getParameter("confirm-password");
	        
        if (username == null || email == null|| password == null || confirmPassword == null|| 
        		username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
   
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("All fields are required.");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Password and confirm password do not match.");
            return;
        }
        
        int result = 0;
		try {
			result = JDBCConnector.registerUser(username, password, email, 3000.0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		
		if (result == -1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Email already exists.");
            return;
        } else if (result == -2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Username is taken.");
            return;
        } else if (result > 0) {
            response.setStatus(HttpServletResponse.SC_OK);
    		double balance  = JDBCConnector.getBalance(result);
    		LoginServlet.user = new User(result, balance);
            
        }
        else {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	pw.println("Bad connection to Database.");
        }
	}
}
