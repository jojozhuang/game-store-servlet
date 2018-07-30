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

import johnny.gamestore.servlet.beans.User;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/UserAdd")
public class UserAdd extends HttpServlet {
    private String error_msg = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayUser(request, response, pw, false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request, pw);

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");

        if(name == null){
            error_msg = "Name can't be empty!";
        }else if(password == null){
            error_msg = "Password can't be empty!";
        }else if(usertype == null){
            error_msg = "User Type can't be empty!";
        }

        if (!error_msg.isEmpty()) {
            displayUser(request, response, pw, true);
            return;
        }

        HashMap<String, User> hm = helper.getUsers();
        if(hm.containsKey(name)) {
            error_msg = "User ["+name+"] already exist!";
        } else{
            User ur = new User(name, password, usertype);
            System.out.println(hm.size());
            hm.put(name, ur);
            System.out.println(hm.size());
            error_msg = "User ["+name+"] is created!";
        }

        displayUser(request, response, pw, true);
    }

    protected void displayUser(HttpServletRequest request,
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
        if (usertype==null || !usertype.equals(UserHashMap.CONST_TYPE_SALESMAN)) {
                errmsg = "You have no authorization to manage user!";
        }
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_USERS);
        if (error) {
                errmsg = "<h3 style='color:red'>"+error_msg+"</h3>";
        }
        String template = helper.getTemplate("user_add.html");
        template = template.replace("$errmsg$", errmsg);
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
