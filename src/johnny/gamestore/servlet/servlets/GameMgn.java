package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Game;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/GameMgn")
public class GameMgn extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);
        if(!helper.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute(helper.SESSION_LOGIN_MSG, "Please login first!");
            response.sendRedirect("Login");
            return;
        }
        String usertype = helper.usertype();
        String errmsg = "";
        if (usertype==null || !usertype.equals(UserHashMap.CONST_TYPE_STOREMANAGER_LOWER)) {
            errmsg = "You have no authorization to manage game!";
        }

        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_GAMEMNG);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>Game Management</h3>";
        content += "  <div style='padding:5px'><a href='GameAdd' class='button'>Create New Game</a></div>";
        if(errmsg.isEmpty()){
            content += "<table cellspacing='0'>";
            content += "<tr><th>No.</th><th>Maker</th><th>Game Name</th><th>Price</th><th>Management</th></tr>";
            int i = 1;
            for(Map.Entry<String, Game> entry : helper.getGames().entrySet()){
                Game game = entry.getValue();
                content += "<tr>";
                content += "<td>"+i+"</td><td>"+game.getMaker()+"</td><td>"+game.getName()+"</td><td>$"+game.getPrice()+"</td>";
                content += "<td>";
                content += "  <span style='padding-right:3px;'><a href='GameEdit?maker="+game.getMaker()+"&game="+game.getKey()+"' class='button'>Edit</a></span>";
                content += "  <span><a href='GameDel?maker="+game.getMaker()+"&game="+game.getKey()+"'' class='button' onclick = \"return confirm('Are you sure you want to delete this game?')\">Delete</a></span>";
                content += "</td>";
                content += "</tr>";
                i++;
            }
            content += "</table>";
        } else {
            content += "<h4 style='color:red'>"+errmsg+"</h4>";
        }
        content += "  </div>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
