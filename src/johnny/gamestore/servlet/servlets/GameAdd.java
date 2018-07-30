package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Console;
import johnny.gamestore.servlet.beans.Game;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/GameAdd")
public class GameAdd extends HttpServlet {
    private String error_msg = "";
    private HashMap<String, Console> consoles;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayGame(request, response, pw, false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request, pw);

        String maker = request.getParameter("maker");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String image = request.getParameter("image");
        String retailer = request.getParameter("retailer");
        String condition = request.getParameter("condition");
        String discount = request.getParameter("discount");

        if(maker == null){
            error_msg = "Maker can't be empty!";
        }else if(name == null){
            error_msg = "Name can't be empty!";
        }else if(price == null){
            error_msg = "Price can't be empty!";
        }else if(image == null){
            error_msg = "Image can't be empty!";
        }else if(retailer == null){
            error_msg = "Retailer can't be empty!";
        }else if(condition == null){
            error_msg = "Condition can't be empty!";
        }else if(discount == null){
            error_msg = "Discount can't be empty!";
        }

        double dprice = 0.0;
        if (error_msg.isEmpty()) {
            try {
                dprice = Double.parseDouble(price);
            } catch (NumberFormatException nfe) {
                error_msg = "Price must be number!";
            }
        }
        double ddiscount = 0.0;
        if (error_msg.isEmpty()) {
            try {
                ddiscount = Double.parseDouble(discount);
            } catch (NumberFormatException nfe) {
                error_msg = "Diccount must be number!";
            }
            if (error_msg.isEmpty() && (ddiscount < 0 || ddiscount > 100)) {
                error_msg = "Diccount must be between 0 and 100!";
            }
        }

        if (!error_msg.isEmpty()) {
            displayGame(request, response, pw, true);
            return;
        }

        HashMap<String, Game> hm = helper.getGames(maker);
        String key = name.trim().toLowerCase();
        if (hm==null) {
            error_msg = "No such maker ["+maker+"]!";
        } else if(hm.containsKey(key)) {
            error_msg = "Game ["+name+"] already exist!";
        } else{
            Game gm = new Game(key, maker, name, dprice, image, retailer,condition,ddiscount);
            hm.put(key, gm);
            error_msg = "Game ["+name+"] is created!";
        }

        displayGame(request, response, pw, true);
    }

    protected void displayGame(HttpServletRequest request,
                    HttpServletResponse response, PrintWriter pw, boolean error)
                    throws ServletException, IOException {
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
        if (error) {
            errmsg = "<h3 style='color:red'>"+error_msg+"</h3>";
        }
        String template = helper.getTemplate("game_add.html");
        template = template.replace("$errmsg$", errmsg);
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
