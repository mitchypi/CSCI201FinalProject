package finalproj;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MenuServlet")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	int user_id = Integer.parseInt(request.getParameter("userID"));
    	String name = request.getParameter("name");
    	double price = Integer.parseInt(request.getParameter("price"));
    	String desc = request.getParameter("desc");
    	String imgUrl = request.getParameter("imgUrl");
    	
    	try {
			JDBCConnector.addCartItem(user_id, name, price, desc, imgUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		String json = "{\"name\":" + "\"" + name + "\","
			+ "\"description\":" + "\"" + desc + "\","
			+ "\"price\":" +  price + "}";
		System.out.println(json);

	    response.setContentType("application/json");
	    out.println(json);
	    out.flush();
	    out.close();
    	
    
    	

    }
    
    
}