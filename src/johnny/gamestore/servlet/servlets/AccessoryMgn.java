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

import johnny.gamestore.servlet.beans.Accessory;
import johnny.gamestore.servlet.beans.Console;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/AccessoryMgn")
public class AccessoryMgn extends HttpServlet {
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
            errmsg = "You have no authorization to manage accessary!";
        }

        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_ACCMNG);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "    <h3>Accessory Management</h3>";
        content += "    <div style='padding:5px'><a href='AccessoryAdd' class='button'>Create New Accessory</a></div>";
        if(errmsg.isEmpty()){
            content += "<table cellspacing='0'>";
            content += "<tr><th>No.</th><th>Accessory Name</th><th>Console</th><th>Price</th><th>Management</th></tr>";
            int i = 1;
            for(Map.Entry<String, Console> entry : helper.getConsoles().entrySet()){
                Console console = entry.getValue();
                for (Map.Entry<String, Accessory> entry2 : console.getAccessories().entrySet()) {
                    Accessory ac = entry2.getValue();
                    content += "<tr>";
                    content += "<td>"+i+"</td><td>"+ac.getName()+"</td><td>"+console.getName()+"</td><td>$"+ac.getPrice()+"</td>";
                    content += "<td>";
                    content += "  <span style='padding-right:3px;'><a href='AccessoryEdit?manufacturer="+console.getManufacturer()+"&console="+console.getKey()+"&accessory="+ac.getKey()+"' class='button'>Edit</a></span>";
                    content += "  <span><a href='AccessoryDel?manufacturer="+console.getManufacturer()+"&console="+console.getKey()+"&accessory="+ac.getKey()+"'' class='button' onclick=\"return confirm('Are you sure you want to delete this accessary?')\">Delete</a></span>";
                    content += "</td>";
                    content += "</tr>";
                    i++;
                }
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
