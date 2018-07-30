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

import johnny.gamestore.servlet.beans.User;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/UserMgn")
public class UserMgn extends HttpServlet {
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
        if (usertype==null || !usertype.equals(UserHashMap.CONST_TYPE_SALESMAN_LOWER)) {
            errmsg = "You have no authorization to manage user!";
        }

        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_USERS);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>User List</h3>";
        content += "  <div style='padding:5px'><a href='UserAdd' class='button'>Create New User</a></div>";
        if(errmsg.isEmpty()){
            content += "<table cellspacing='0'>";
            content += "<tr><th>No.</th><th>User Name</th><th>User Type</th><th>Management</th></tr>";
            int i = 1;
            for(Map.Entry<String, User> entry : helper.getUsers().entrySet()){
                User user = entry.getValue();
                content += "<tr>";
                content += "<td>"+i+"</td><td>"+user.getName()+"</td><td>"+user.getUsertype()+"</td>";
                content += "<td>";
                content += "  <span style='padding-right:3px;'><a href='UserEdit?usertype="+user.getUsertype()+"&name="+user.getName()+"' class='button'>Edit</a></span>";
                content += "  <span><a href='UserDel?usertype="+user.getUsertype()+"&name="+user.getName()+"' class='button' onclick = \"return confirm('Are you sure to delete this user?')\">Delete</a></span>";
                content += "</td>";
                content += "</tr>";
                    i++;
            }
            content += "</table>";
        } else {
            content += "<h4 style='color:red'>"+errmsg+"</h4>";
        }
        content += "	</div>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
