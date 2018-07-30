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

import johnny.gamestore.servlet.beans.Console;

@WebServlet("/ConsoleList")
public class ConsoleList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String makerName = request.getParameter("maker");
        makerName = makerName == null ? "" : makerName;

        Helper helper = new Helper(request,pw);
        HashMap<String, Console> hm = helper.getConsoles(makerName);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_CONSOLES);
        String itemtemp = helper.getTemplate("shopping_item.html");
        String content = "";
        content += "<section id='content'>";
        content += "    <h3>"+makerName+" Consoles</h3>";
        int i = 1; int size= hm.size();
        for(Map.Entry<String, Console> entry : hm.entrySet()){
            Console console = entry.getValue();
            if(i%3==1) {
                content += "<div class='special_grid_row'>";
            }
            String item = itemtemp;
            item = item.replace("$itemname$", console.getName())
                       .replace("$image$", console.getImage())
                       .replace("$oldprice$", helper.formatCurrency(console.getPrice()))
                       .replace("$newprice$", helper.formatCurrency(console.getDiscountedPrice()))
                       .replace("$id$", console.getKey())
                       .replace("$name$", console.getName())
                       .replace("$type$", "1")
                       .replace("$maker$", console.getRetailer());
            content += item;
            if(i%3==0 || i == size) {
                content += "</div>";
            }
            i++;
        }
        content += "    <div class='clear'>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
