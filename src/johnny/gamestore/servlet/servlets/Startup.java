package johnny.gamestore.servlet.servlets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import johnny.gamestore.servlet.data.ConsoleHashMap;
import johnny.gamestore.servlet.data.GameHashMap;
import johnny.gamestore.servlet.data.ReviewHashMap;
import johnny.gamestore.servlet.data.TabletHashMap;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/Startup")
public class Startup extends HttpServlet
{
    public void init() throws ServletException
    {
        new ConsoleHashMap();
        new GameHashMap();
        new UserHashMap();
        new TabletHashMap();
        new ReviewHashMap();
    }
}
