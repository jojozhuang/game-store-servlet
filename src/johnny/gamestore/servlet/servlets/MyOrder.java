package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Order;
import johnny.gamestore.servlet.beans.OrderItem;
import johnny.gamestore.servlet.dao.OrderDao;

public class MyOrder extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        String errmsg = "";
        List<Order> orders = null;
        synchronized(session) {
            OrderDao allorders = (OrderDao)session.getAttribute(helper.SESSION_ORDERS);
            if (allorders == null) {
                errmsg = "You have no order yet!";
            } else {
                orders = allorders.getOrders(helper.username()); 
                if (orders == null || orders.size() == 0) {
                    errmsg = "You have no order yet!";
                }
            }
        }
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_MYORDER);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>My Orders</h3>";
        if (!errmsg.isEmpty()) {
            content += "<h3 style='color:red'>"+errmsg+"</h3>";
        } else {
            for (Order order: orders) {
                content += "<div class=\"order_box\"><table class=\"order_table\"><tr><td><h5><i>Order Id: </i></h5></td><td>"+order.getId()+"</td>";
                content += "<td><form action=\"OrderCancel\" method=\"Post\">" + 
                   "<input type=\"hidden\" name=\"orderid\"" +
                   "       value=\"" + order.getId() + "\">" +
                   "<input type=\"submit\" value=\"Cancel Order\" class=\"formbutton\" onclick = \"return confirm('Are you sure to cancel this order?')\">"+      
                   "</form></td></tr>";
                content += "<tr><td><h5><i>Customer Name: </i></h5></td><td>"+order.getUserName()+"</td><td></td></tr>";
                content += "<tr><td><h5><i>Address: </i></h5></td><td>"+order.getAddress()+"</td><td></td></tr>";
                content += "<tr><td><h5><i>Confirmation Number: </i></h5></td><td>"+order.getConfirmation()+"</td><td></td></tr>";
                content += "<tr><td><h5><i>Delivery Date: </i></h5></td><td>"+helper.formateDate(order.getDeliveryDate())+"</td><td></td></tr>";
                content += "</table>";
                content += "<table cellspacing='0'>";
                content += "<tr><th>No.</th><th>Name</th><th>Price</th><th>Quantity</th><th>SubTotal</th></tr>"; 
                OrderItem orderItem;
                double total = 0;
                for(int i = 0; i < order.getItems().size(); i++) {
                    orderItem = (OrderItem)order.getItems().get(i);
                    content += "<tr>";
                    content += "<td>"+(i+1)+"</td><td>"+orderItem.getItemName()+"</td><td>"+helper.formatCurrency(orderItem.getUnitPrice())+"</td>";
                    content += "<td>" + orderItem.getQuantity()+"</td>";
                    content += "<td>" +  helper.formatCurrency(orderItem.getTotalCost())+ "</td>";
                    content += "</tr>";
                    total = total +orderItem.getTotalCost();
                }
                content += "<tr class='total'><td></td><td></td><td></td><td>Total</td><td>"+helper.formatCurrency(total)+"</td></tr>";
                content += "</table></div>";
            }
        }
        content += "  </div>";
        content += "</section>";
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
