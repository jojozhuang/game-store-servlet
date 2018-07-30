package johnny.gamestore.servlet.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private String username;
    private String address;
    private String creditcard;
    private String confirmation;
    private Date deliverydate;
    private ArrayList<OrderItem> items;
    public Order(String id, String username, String address, String creditcard, String confirmation, Date deliverydate) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.creditcard = creditcard;
        this.confirmation = confirmation;
        this.deliverydate = deliverydate;
        items = new ArrayList();
    }
    
    public String getId() {
        return id;
    }
    
    public String getUserName() {
        return username;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCreditCard() {
        return creditcard;
    }
    
    public String getConfirmation() {
        return confirmation;
    }
    
    public Date getDeliveryDate() {
        return deliverydate;
    }
   
    public List getItems() {
      return items;
    }
    
    public synchronized void addItem(OrderItem item) {
        items.add(item);
    }
}
