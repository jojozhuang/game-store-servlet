package johnny.gamestore.servlet.data;
import java.util.ArrayList;
import java.util.HashMap;

import johnny.gamestore.servlet.beans.OrderItem;

public class OrdersHashMap {
    public static HashMap<String, ArrayList<OrderItem>> orders = new HashMap<String, ArrayList<OrderItem>>();
    public OrdersHashMap() {
        //replaced with OrderList
    }
}
