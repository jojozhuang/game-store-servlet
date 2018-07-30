package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import johnny.gamestore.servlet.beans.ProductItem;
import johnny.gamestore.servlet.dao.ProductDao;

@WebServlet("/SearchProdcut")
public class SearchProduct extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String productName = request.getParameter("productname");
        productName = productName == null ? "" : productName;

        Helper helper = new Helper(request,pw);
        ArrayList<ProductItem> items = ProductDao.searchProduct(productName);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_HOME);
        String itemtemp = helper.getTemplate("shopping_item2.html");
        String content = "";
        content += "<section id='content'>";
        content += "  <h3>Product Search Result</h3>";
        if (items.size() == 0) {
            content += "<h3>No matched result found!</h3>";
        } else {
            int i = 1; int size= items.size();
            for(ProductItem product : items){
                if(i%3==1) {
                    content += "<div class='special_grid_row'>";
                }
                String item = itemtemp;
                item = item.replace("$itemname$", product.getName())
                           .replace("$image$", product.getImage())
                           .replace("$oldprice$", helper.formatCurrency(product.getPrice()))
                           .replace("$newprice$", helper.formatCurrency(product.getDiscountedPrice()))
                           .replace("$id$", product.getId())
                           .replace("$name$", product.getName())
                           .replace("$type$", String.valueOf(product.getType()))
                           .replace("$maker$", product.getMaker());
                content += item;
                if(i%3==0 || i == size) {
                    content += "</div>";
                }
                i++;
            }
        }
        content += "  <div class='clear'></div>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
