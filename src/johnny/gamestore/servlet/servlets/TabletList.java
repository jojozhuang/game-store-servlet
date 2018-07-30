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

import johnny.gamestore.servlet.beans.Tablet;

@WebServlet("/TabletList")
public class TabletList extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String makerName = request.getParameter("maker");
        makerName = makerName == null ? "" : makerName;

        Helper helper = new Helper(request,pw);
        HashMap<String, Tablet> hm = helper.getTablets(makerName);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_TABLETS);
        String itemtemp = helper.getTemplate("shopping_item2.html");
        String content = "";
        content += "<section id='content'>";
        content += "  <h3>"+makerName+" Tablets</h3>";
        int i = 1; int size= hm.size();
        for(Map.Entry<String, Tablet> entry : hm.entrySet()){
            Tablet tablet = entry.getValue();
            if(i%3==1) {
                content += "<div class='special_grid_row'>";
            }
            String item = itemtemp;
            item = item.replace("$itemname$", tablet.getName())
                       .replace("$image$", tablet.getImage())
                       .replace("$oldprice$", helper.formatCurrency(tablet.getPrice()))
                       .replace("$newprice$", helper.formatCurrency(tablet.getDiscountedPrice()))
                       .replace("$id$", tablet.getKey())
                       .replace("$name$", tablet.getName())
                       .replace("$type$", "4")
                       .replace("$maker$", makerName);
            content += item;
            if(i%3==0 || i == size) {
                content += "</div>";
            }
            i++;
        }
        content += "  <div class='clear'>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
