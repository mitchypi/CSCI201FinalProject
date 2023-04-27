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
public class MenuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	int restaurant_id = Integer.parseInt(request.getParameter("restaurant_id"));
    	
    	String json;
		try {
			json = JDBCConnector.getRestaurantItems(restaurant_id);
			System.out.println(json);
		    response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    out.println(json);
		    out.flush();
		    out.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
    	

    }
    
    
}