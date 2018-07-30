package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Order;
import johnny.gamestore.servlet.beans.OrderItem;
import johnny.gamestore.servlet.dao.OrderDao;

public class OrderCancel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request, pw);
        
        String errmsg = "";
        String orderid = request.getParameter("orderid");
        if (orderid==null||orderid.isEmpty()) {
            errmsg = "Order id is empty!";
        }
        
        if (errmsg.isEmpty()) {        
            HttpSession session = request.getSession();
            OrderDao orderlist;
            synchronized(session) {
                orderlist = (OrderDao)session.getAttribute(helper.SESSION_ORDERS);
                if (orderlist == null) {
                    errmsg = "You have no order!";
                } else {
                    Order order = orderlist.getOrder(orderid);
                    if (order == null) {
                        errmsg = "Order ["+orderid+"] is not found!";
                    } else {                    
                        List<OrderItem> items = order.getItems();
                        for (OrderItem item: items) {
                            if (item.getItemType() == 4 || item.getItemType() == 5) {
                                Date deliverydate = order.getDeliveryDate();
                                Calendar c = Calendar.getInstance();
                                c.setTime(deliverydate);
                                c.add(Calendar.DATE, -5);
                                Date now = new Date();
                                int comparison = now.compareTo(c.getTime());
                                if (comparison > 0) {
                                    errmsg = "The order can only be cancelled within 5 days before delivery date ["+helper.formateDate(order.getDeliveryDate())+"]"
                                            + "<br><h2 style=\"color:red;\">You can't cancel it now.</h2>";
                                }
                            }
                        }
                    }
                }
                if (errmsg.isEmpty()) {
                    orderlist.removeOrder(orderid);
                    session.setAttribute(helper.SESSION_ORDERS, orderlist);
                    errmsg = "Your order ["+orderid+"] has been removed!";
                }
            }
        }        
        
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_ALLORDERS);
        String content = "<section id='content'>";
        content += "  <div class='cart'>";
        content += "  <h3>Cancel Order</h3>";
        content += "  <h3 style='color:red'>"+errmsg+"</h3>";
        content += "  </div>";
        content += "</section>";
        helper.prepareContent(content);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();   
    }
}
