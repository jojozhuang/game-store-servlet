package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.CartItem;
import johnny.gamestore.servlet.beans.ShoppingCart;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
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
        
        HttpSession session = request.getSession();
        ShoppingCart cart;
        synchronized(session) {
            cart = (ShoppingCart)session.getAttribute(helper.SESSION_CART);
            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute(helper.SESSION_CART, cart);
            }
            String id = request.getParameter("id");
            String strtype = request.getParameter("type");
            int type = 0;
            if (strtype!=null) {
                try {
                    type = Integer.parseInt(strtype);
                } catch (NumberFormatException nfe) {
                    
                }
            }
            if (id != null && !id.isEmpty()) {
                String strQuantity = request.getParameter("quantity");
                if (strQuantity == null) {                  
                    cart.addItem(id, type);
                } else {                  
                    int quantity;
                    try {
                        quantity = Integer.parseInt(strQuantity);
                    } catch(NumberFormatException nfe) {
                        quantity = 1;
                    }
                    cart.setItemQuantity(id, type, quantity);
                }
            }
        }

        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_CART);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>My Cart</h3>";
        List<CartItem> items = cart.getItems();
        if (items.size() == 0) {
             content += "<h3 style='color:red'>Your Cart is empty!</h3>";
        } else {
            content += "<table cellspacing='0'>";
            content += "<tr><th>No.</th><th>Name</th><th>Price</th><th>Quantity</th><th>SubTotal</th><th>Management</th></tr>"; 
            CartItem cartItem;            
            double total = 0;
            for(int i = 0; i < items.size(); i++) {
                cartItem = items.get(i);
                content += "<tr>";
                content += "<td>"+(i+1)+"</td><td>"+cartItem.getItemName()+"</td><td>"+helper.formatCurrency(cartItem.getUnitPrice())+"</td>";
                content += "  <td>" +
                   "<form>" +  // Submit to current URL
                   "<input type=\"hidden\" name=\"id\"" +
                   "       value=\"" + cartItem.getItemId() + "\">" +
                   "<input type=\"hidden\" name=\"type\"" +
                   "       value=\"" + cartItem.getItemType()+ "\">" +
                   "<input type=\"text\" name=\"quantity\" size=3 value=\"" + 
                   cartItem.getQuantity() + "\">\n" +
                   "<input type=\"submit\" class=\"formbutton2\" value=\"Update\">"+      
                   "</form></td>" +
                   "  <td>" +  helper.formatCurrency(cartItem.getTotalCost())+ "</td>";
                content += "<td>";
                content += "  <span><a href='Cart?id="+cartItem.getItemId()+"&type="+cartItem.getItemType()+"&quantity=0' class='button3' onclick = \"return confirm('Are you sure to delete this product?')\">Delete</a></span>";
                content += "</td>";
                content += "</tr>";
                total = total +cartItem.getTotalCost();
            }
            content += "<tr class='total'><td></td><td></td><td></td><td>Total</td><td>"+helper.formatCurrency(total)+"</td><td></td></tr>";
            content += "<tr><td></td><td></td><td></td><td></td><td></td><td><a href='Checkout' class='button2'>Check Out</a></td></tr></table>";
        }
        content += "  </div>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
