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

import johnny.gamestore.servlet.beans.User;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayRegistration(request, response, pw, false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request, pw);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String usertype = UserHashMap.CONST_TYPE_CUSTOMER;
        if(!helper.isLoggedin()) {
            usertype = request.getParameter("usertype");
        }

        if(!password.equals(repassword)){
            error_msg = "Password and Re-Password doesn't match!";
        } else {
            HashMap<String, User> hm = helper.getUsers();
            if(hm.containsKey(username)) {
                error_msg = "Username ["+username+"] already exist as " + usertype;
            } else {
                User user = new User(username,password,usertype);
                hm.put(username, user);
                HttpSession session = request.getSession(true);
                session.setAttribute(helper.SESSION_LOGIN_MSG, "Your "+usertype+" account has been created. Please login");
                if(!helper.isLoggedin()){
                    response.sendRedirect("Login"); return;
                } else {
                    response.sendRedirect("Account"); return;
                }
            }
        }
        if(helper.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute(helper.SESSION_LOGIN_MSG, error_msg);
            response.sendRedirect("Account"); return;
        }
        displayRegistration(request, response, pw, true);
    }

    protected void displayRegistration(HttpServletRequest request,
                    HttpServletResponse response, PrintWriter pw, boolean error)
                    throws ServletException, IOException {

        Helper helper = new Helper(request,pw);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_HOME);
        String errmsg = "";
        if (error) {
            errmsg = "<h3 style='color:red'>"+error_msg+"</h3>";
        }
        String template = helper.getTemplate("account_register.html");
        template = template.replace("$errmsg$", errmsg);
        helper.prepareContent(template);
        //helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
