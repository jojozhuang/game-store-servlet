package johnny.gamestore.servlet.dao;

import java.util.ArrayList;
import java.util.List;

import johnny.gamestore.servlet.beans.Order;
import johnny.gamestore.servlet.beans.OrderItem;

public class OrderDao {
    private ArrayList<Order> orders;
    public OrderDao() {        
        orders = new ArrayList();
    }    
  
    public List getOrders() {
      return orders;
    }
    
    public List getOrders(String username) {
        if (username == null || username.isEmpty()) {
            return orders;
        }
        ArrayList<Order> res = new ArrayList<Order>();
        for (Order order: orders) {
            if (order.getUserName().equals(username)) {
                res.add(order);
            }
        }
        return res;
    }
    
    public synchronized void addOrder(Order order) {
        orders.add(order);
    }
    
    public synchronized Order getOrder(String id) {
        if (orders==null || orders.isEmpty()) {
            return null;
        } 
        for (Order order: orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    
    public synchronized void removeOrder(String id) {
        if (orders==null || orders.isEmpty()) {
            return;
        } 
        
        Order order = getOrder(id);
        if (order==null) {
            return;
        } else {
            orders.remove(order);
        }
    }
    
    public synchronized void setItemQuantity(String orderid, String itemid, int type, int quantity) {
        Order order;
        for(int i = 0; i < orders.size(); i++) {
            order = orders.get(i);
            if (order.getId().equals(orderid)) {
                List<OrderItem> items= order.getItems();
                for (int j = 0; j < items.size(); j++) {
                    OrderItem orderItem = items.get(j);
                    if (orderItem.getItemId().equals(itemid)) {
                        if (quantity <= 0) {
                            items.remove(j);
                        } else {
                            orderItem.setQuantity(quantity);
                        }
                        return;
                    }
                }
            }
        }        
    }
}
