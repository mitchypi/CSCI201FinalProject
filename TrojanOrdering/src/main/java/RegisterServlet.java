

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
	    String password = request.getParameter("new-password");
	    String confirmPassword = request.getParameter("confirm-password");
	        
        if (email == null|| password == null || confirmPassword == null|| 
        		email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
   
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
			result = JDBCConnector.registerUser(password, email, 3000.0); //result equals user_id if valid, otherwise -1 or -2 based on error
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			result = JDBCConnector.registerUser(password, email, 3000.0);
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
    		double balance=3000.00;
			try {
				balance = JDBCConnector.getBalance(result);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            HttpSession session = request.getSession(true);
            
            session.setAttribute("user_id", result);
            session.setAttribute("email", email);
            session.setAttribute("balance", balance);
            
        }
        else {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	pw.println("Bad connection to Database.");
        }
	}
}
