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

@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");

        Helper helper = new Helper(request,pw);
        User user = helper.getUser(username);
        if(user!=null){
            String user_password = user.getPassword();
            String user_usertype = user.getUsertype();
            if (password.equals(user_password)&&usertype.equals(user_usertype)) {
                HttpSession session = request.getSession(true);
                session.setAttribute(helper.SESSION_USERNAME, user.getName());
                session.setAttribute(helper.SESSION_USERTYPE, user.getUsertype());
                response.sendRedirect("Home");
                return;
            }
        }
        displayLogin(request, response, pw, true);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayLogin(request, response, pw, false);
    }

    protected void displayLogin(HttpServletRequest request,
                    HttpServletResponse response, PrintWriter pw, boolean error)
                    throws ServletException, IOException {

        Helper helper = new Helper(request,pw);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_HOME);
        String errmsg = "";
        if (error) {
            errmsg = "<h3 style='color:red'>Login failed! <br>Please check your username, password and user type!</h3>";
        }
        HttpSession session = request.getSession(true);
        if(session.getAttribute(helper.SESSION_LOGIN_MSG)!=null){
            errmsg = "<h3 style='color:red'>" + session.getAttribute(helper.SESSION_LOGIN_MSG) + "</h3>";
            session.removeAttribute(helper.SESSION_LOGIN_MSG);
        }
        String template = helper.getTemplate("account_login.html");
        template = template.replace("$errmsg$", errmsg);
        helper.prepareContent(template);
        //helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
