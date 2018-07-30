package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Trending")

public class Trending extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        Helper helper = new Helper(request, pw);
        //helper.printHtml("site_header.html");
        //helper.printHtml("site_sidebar.html");


        //helper.printHtml("site_footer.html");
    }

    protected void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {

    }
}
