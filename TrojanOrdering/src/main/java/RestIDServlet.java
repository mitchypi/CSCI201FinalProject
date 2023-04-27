import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/RestIDServlet")
public class RestIDServlet extends HttpServlet {
	
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    // get the restaurant id from the request parameter
    String id = request.getParameter("id");
    int idint = Integer.parseInt(id);
    
    // set the restaurant id in the session
    HttpSession session = request.getSession();
    session.setAttribute("restaurant_id", id);
    Restaurant rest = JDBCConnector.getRestData(idint);
    session.setAttribute("restaurant_name", rest.getName());
    session.setAttribute("restaurant_img", rest.getURL());


    
    System.out.println("Session restaurant_id: " + session.getAttribute("restaurant_id"));
    System.out.println("Session restaurant_name: " + session.getAttribute("restaurant_name"));
    System.out.println("Session restaurant_img: " + session.getAttribute("restaurant_img"));
    

  }

}
