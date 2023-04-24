

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Stack;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static Stack<User> userStack;
	private static final long serialVersionUID = 2L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    response.setContentType("text/plain");
	    PrintWriter pw = response.getWriter();

	    
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
        if (email == null || password == null || 
        		email.isEmpty() || password.isEmpty()) {
        	System.out.println("Missing Field");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("All fields are required.");
            return;
        }
        
        int result = 0;
		try {
			result = JDBCConnector.loginUser(email,password); //this is the user_id
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
    		double balance = 0.00;
			try {
				balance = JDBCConnector.getBalance(result);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//user = new User(result, balance);
			String balanceString = Double.toString(balance);
			String resultString = Integer.toString(result);

			HttpSession session = request.getSession(true);
        
			// Set session attributes
			session.setAttribute("user_id", result);
			session.setAttribute("email", email);
			session.setAttribute("balance", balance);

			Cookie user_id = new Cookie("user_id",resultString);
			Cookie user_balance = new Cookie("balance",balanceString);
			//setting cookie to expiry in 30 mins
			response.addCookie(user_id);
			response.addCookie(user_balance);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
        	System.out.println("-3");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.println("Bad Connection.");
        }
	}
	public static void pushUser(User user) {
	    userStack.push(user);
	}
	
	public static User peekUser() {
	    return userStack.peek();
	}
}
