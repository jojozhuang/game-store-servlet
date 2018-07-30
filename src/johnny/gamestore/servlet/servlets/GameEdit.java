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
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/GameEdit")
public class GameEdit extends HttpServlet {
    private String error_msg = "";

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
        String game = request.getParameter("game");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String image = request.getParameter("image");
        String retailer = request.getParameter("retailer");
        String condition = request.getParameter("condition");
        String discount = request.getParameter("discount");

        if(maker == null || game == null){
            error_msg = "Game can't be empty!";
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

        HashMap<String, Game> gamelist = helper.getGames(maker);
        Game gameobj = gamelist.get(game);
        if (gameobj == null) {
            error_msg = "Game ["+name+"] does not exist!";
        } else{
            gameobj.setPrice(dprice);
            gameobj.setImage(image);
            gameobj.setRetailer(retailer);
            gameobj.setCondition(condition);
            gameobj.setDiscount(ddiscount);
            error_msg = "Game ["+name+"] is updated!";
            System.out.println(error_msg);
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

        String maker = request.getParameter("maker");
        String game = request.getParameter("game");

        if (maker==null||game==null) {
            maker="";
            game="";
            errmsg = "<h3 style='color:red'>Invalid Paramters!</h3>";
        }
        String[][] arr = new String[3][2];
        arr[0][0] = "electronicarts";
        arr[0][1] = "Electronic Arts";
        arr[1][0] = "activision";
        arr[1][1] = "Activision";
        arr[2][0] = "taketwointeractive";
        arr[2][1] = "Take-Two InteractivePS3";
        String strOptionlist = "<tr><td><h5>Maker:</h5></td><td><select name='maker' class='input' disabled>";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0].equals(maker.toLowerCase())) {
                strOptionlist += "<option value='"+arr[i][0]+"' selected>"+arr[i][1]+"</option>";
            } else {
                strOptionlist += "<option value='"+arr[i][0]+"'>"+arr[i][1]+"</option>";
            }
        }
        strOptionlist += "</select></td></tr>";

        Game gameobj = helper.getGame(maker, game);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_GAMEMNG);
        if (error) {
            errmsg = "<h3 style='color:red'>"+error_msg+"</h3>";
        }
        String template = helper.getTemplate("game_edit.html");
        template = template.replace("$errmsg$", errmsg);
        template = template.replace("$option$", strOptionlist);
        if (gameobj != null) {
            template = template.replace("$maker$", maker)
                               .replace("$game$", game)
                               .replace("$name$", gameobj.getName())
                               .replace("$price$", String.valueOf(gameobj.getPrice()))
                               .replace("$image$", gameobj.getImage())
                               .replace("$retailer$", gameobj.getRetailer())
                               .replace("$condition$", gameobj.getCondition())
                               .replace("$discount$", String.valueOf(gameobj.getDiscount()));
        }
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
