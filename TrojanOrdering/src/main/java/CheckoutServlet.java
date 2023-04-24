import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		double total = Integer.parseInt(req.getParameter("total"));
		int user_id = Integer.parseInt(req.getParameter("userID"));
		double balance = 0;
		
		resp.setContentType("application/json");
		String json = "{\"data\":[";
		
		try {
		    balance = JDBCConnector.getBalance(user_id); 
			
		    if(total > balance) {	//Insufficient funds
		    	json += "{\"completed\":false}]}";
		    }else {	//Update the user's balance
		    	
		    	if (JDBCConnector.updateBalance(user_id, balance, total)) {
		    		json += json += "{\"completed\":true}]}";
		    	}
		    	
		    	JDBCConnector.deleteCartItems(user_id);
		    	
		    }
		    
		    if(json.equals("{\"data\":[")) {
		    	json += "]}";
		    }
		    
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		//System.out.println(json);
		out.println(json);
		out.flush();
		out.close();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int user_id = Integer.parseInt(req.getParameter("userID"));
		PrintWriter out = resp.getWriter();
		String json = "{\"data\":[";
		
		ArrayList<Integer> itemNumbers = null;
		try {
			itemNumbers = JDBCConnector.getCartItems(user_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			json += JDBCConnector.getItemData(itemNumbers);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json += json += "]}";
		
		out.println(json);
		out.flush();
		out.close();
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int user_id = Integer.parseInt(req.getParameter("userID"));
		String itemName = req.getParameter("itemName");
		
		try {
			JDBCConnector.deleteCartItems(user_id, itemName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
