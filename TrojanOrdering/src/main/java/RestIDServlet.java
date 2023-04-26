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
    
    // set the restaurant id in the session
    HttpSession session = request.getSession();
    session.setAttribute("restaurant_id", id);
    System.out.println("Session restaurant_id: " + session.getAttribute("restaurant_id"));
    

  }

}
