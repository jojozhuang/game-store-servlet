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

import johnny.gamestore.servlet.beans.Accessory;
import johnny.gamestore.servlet.beans.Console;

@WebServlet("/AccessoryDel")
public class AccessoryDel extends HttpServlet {
    private String error_msg = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String errmsg = "";
        String manufacturer = request.getParameter("manufacturer");
        String console = request.getParameter("console");
        String accessory = request.getParameter("accessory");

        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);

        Console conobj= helper.getConsole(manufacturer, console);
        if (conobj == null) {
            errmsg = "<h3 style='color:red'>No such console ["+console+"] found!</h3>";
        } else{
            HashMap<String, Accessory> hm = conobj.getAccessories();
            if(hm==null||!hm.containsKey(accessory)) {
                errmsg = "<h3 style='color:red'>No such accessory ["+accessory+"] !</h3>";
            } else {
                hm.remove(accessory);
            }
        }
        response.sendRedirect("AccessoryMgn");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
