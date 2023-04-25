import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ItemServlet") //adds cart items?
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) //this should probably be a doPost
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	int user_id = Integer.parseInt(request.getParameter("userID"));
    	int item_id = Integer.parseInt(request.getParameter("itemID"));
    	
    	try {
			JDBCConnector.addCartItem(user_id, item_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
		String json = "{\"name\":" + "\"" + name + "\","
			+ "\"description\":" + "\"" + desc + "\","
			+ "\"price\":" +  price + "}";
		System.out.println(json);

	    response.setContentType("application/json");
	    out.println(json);
	    */
	    out.flush();
	    out.close();
    	
    
    	

    }
    
    
}