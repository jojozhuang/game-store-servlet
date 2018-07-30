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

@WebServlet("/UserDel")
public class UserDel extends HttpServlet {
    private String error_msg = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String usertype = request.getParameter("usertype");
        String name = request.getParameter("name");

        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);
        HashMap<String, User> hm = helper.getUsers();
        if (hm==null||hm.size() == 0) {
            error_msg = "<h3 style='color:red'>No User found!</h3>";
        } else {
            hm.remove(name);
        }
        response.sendRedirect("UserMgn");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
