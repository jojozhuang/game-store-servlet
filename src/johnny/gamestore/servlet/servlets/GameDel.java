package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Game;

@WebServlet("/GameDel")
public class GameDel extends HttpServlet {
    private String error_msg = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String errmsg = "";
        String maker = request.getParameter("maker");
        String game = request.getParameter("game");

        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);
        HashMap<String, Game> gamelist = helper.getGames(maker);
        if (gamelist==null||gamelist.size() == 0) {
            errmsg = "<h3 style='color:red'>No Game found!</h3>";
        } else {
            gamelist.remove(game);
        }
        response.sendRedirect("GameMgn");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
