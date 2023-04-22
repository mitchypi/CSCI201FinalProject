import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
        ArrayList<Restaurant> r = JDBCConnector.getRestaurants();
        String html = makeHtml(r);
        //return html to javascript using fetch 
        response.setContentType("text/html");
        out.println(html);

    }

    protected String makeHtml(ArrayList<Restaurant> r){
        String html = "";
        for (Restaurant rest : r){ //placeholder stuff please give me a format to work with
            String name = rest.getName();
            String address = rest.getAddress();
            String image = rest.getURL();
            html += "//give me something here";
        }
        return html;
    }
}

