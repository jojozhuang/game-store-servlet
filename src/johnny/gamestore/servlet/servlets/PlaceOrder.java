package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.CartItem;
import johnny.gamestore.servlet.beans.Order;
import johnny.gamestore.servlet.beans.OrderItem;
import johnny.gamestore.servlet.beans.ShoppingCart;
import johnny.gamestore.servlet.dao.OrderDao;

public class PlaceOrder extends HttpServlet {    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);
        if(!helper.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute(helper.SESSION_LOGIN_MSG, "Please login first!");
            response.sendRedirect("Login");
            return;
        }
        
        String username = helper.username();
        String address = request.getParameter("address");
        String creditcard = request.getParameter("creditcard");
        
        HttpSession session = request.getSession();
        ShoppingCart cart = null;
        List<CartItem> items = null;
        String errmsg = "";
        synchronized(session) {
            cart = (ShoppingCart)session.getAttribute(helper.SESSION_CART);
            if (cart == null) {
                errmsg = "No item in shopping cart, can't place order!";
            } else {
                items = cart.getItems();
                if (items == null || items.size() == 0) { 
                    errmsg = "No item in shopping cart, can't place order!";
                }                
            }
        }        
        
        String orderid = helper.generateUniqueId();
        String confirmation = username + orderid.substring(orderid.length()-4) + creditcard.substring(creditcard.length() - 4);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>Order - Confirmation</h3>";        
        
        if (!errmsg.isEmpty()) {
             content += "<h3 style='color:red'>"+errmsg+"</h3>";
        } else {
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.DATE, 14); // 2 weeks
             //create order
            Order order = new Order(orderid, helper.username(), address, creditcard, confirmation, c.getTime());
            for (CartItem ob: items) {
                OrderItem item = new OrderItem(ob.getItem());
                item.setQuantity(ob.getQuantity());
                order.addItem(item);
            }

            OrderDao orders;
            orders = (OrderDao)session.getAttribute(helper.SESSION_ORDERS);
            if (orders == null) {
                orders = new OrderDao();
                session.setAttribute(helper.SESSION_ORDERS, orders);
            }
            // create 
            orders.addOrder(order);            
            // remove cart from session
            session.removeAttribute(helper.SESSION_CART); 
            content += "<table class=\"order_table\">";
            content += "<tr><td width=\"30%\"><h5><i>Order Id: </i></h5></td><td width=\"70%\">"+orderid+"</td></tr>";
            content += "<tr><td><h5><i>Customer Name: </i></h5></td><td>"+username+"</td></tr>";
            content += "<tr><td><h5><i>Address: </i></h5></td><td>"+address+"</td></tr>";
            content += "<tr><td><h5><i>Confirmation Number: </i></h5></td><td>"+confirmation+"</td></tr>";            
            content += "<tr><td><h5><i>Delivery Date: </i></h5></td><td>"+helper.formateDate(c.getTime())+"</td><td></td></tr>";
            content += "</table>";
            content += "<table cellspacing='0'>";
            content += "<tr><th>No.</th><th>Product Name</th><th>Price</th><th>Quantity</th><th>SubTotal</th></tr>"; 
            CartItem cartItem;
            double total = 0;
            for(int i = 0; i < items.size(); i++) {
                cartItem = items.get(i);
                content += "<tr>" +
                   "  <td>" + (i + 1) + "</td>" +
                   "  <td>" + cartItem.getItemName() + "</td>" +
                   "  <td>" + helper.formatCurrency(cartItem.getUnitPrice())+ "</td>" +
                   "  <td>" + cartItem.getQuantity()+ "</td>" +
                   "  <td>" + helper.formatCurrency(cartItem.getTotalCost())+ "</td>";
                content += "</tr>";
                total = total +cartItem.getTotalCost();
            }
            content += "<tr class='total'><td></td><td></td><td></td><td>Total</td><td>"+helper.formatCurrency(total)+"</td></tr>";
            content += "</table>";
        }
        content += "  </div>";
        content += "</section>";
        
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_MYORDER);
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();              
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }    
}
