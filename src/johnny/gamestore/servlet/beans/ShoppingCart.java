package johnny.gamestore.servlet.beans;

import java.util.ArrayList;
import java.util.List;

import johnny.gamestore.servlet.dao.ProductDao;

public class ShoppingCart {
    private ArrayList<CartItem> items;
    public ShoppingCart() {
        items = new ArrayList();
    }
    public List getItems() {
      return items;
    }
    
    public synchronized void addItem(String id, int type) {
        CartItem cartItem;
        for(int i = 0; i < items.size(); i++) {
            cartItem = items.get(i);
            if (cartItem.getItemId().equals(id)) {
                cartItem.incrementItemQuantity();
                return;
            }
        }
        CartItem newCartItem = new CartItem(ProductDao.getItem(id, type));
        items.add(newCartItem);
    }
    
    public synchronized void setItemQuantity(String id, int type, int quantity) {
        CartItem cartItem;
        for(int i = 0; i < items.size(); i++) {
            cartItem = items.get(i);
            if (cartItem.getItemId().equals(id)) {
                if (quantity <= 0) {
                    items.remove(i);
                } else {
                    cartItem.setQuantity(quantity);
                }
                return;
            }
        }
        CartItem newCartItem = new CartItem(ProductDao.getItem(id, type));
        items.add(newCartItem);
    }
}
