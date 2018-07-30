package johnny.gamestore.servlet.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import johnny.gamestore.servlet.beans.Accessory;
import johnny.gamestore.servlet.beans.Console;
import johnny.gamestore.servlet.beans.Game;
import johnny.gamestore.servlet.beans.ProductItem;
import johnny.gamestore.servlet.beans.Tablet;
import johnny.gamestore.servlet.data.ConsoleHashMap;
import johnny.gamestore.servlet.data.GameHashMap;
import johnny.gamestore.servlet.data.TabletHashMap;

public class ProductDao {
    public static ArrayList<ProductItem> getItems() {
        ArrayList<ProductItem> res = new ArrayList();
        for (int i = 1; i <= 4; i++) {
            ArrayList<ProductItem> items = getItems(i);
            res.addAll(items);
        }
        return res;
    }
    public static ArrayList<ProductItem> getItems(int type) {
        ArrayList<ProductItem> items = new ArrayList();
        switch(type) {
            case 1:
                HashMap<String, Console> hmc = new HashMap<String, Console>();
                hmc.putAll(ConsoleHashMap.Microsoft);
                hmc.putAll(ConsoleHashMap.Sony);
                hmc.putAll(ConsoleHashMap.Nintendo);
                for(Map.Entry<String, Console> entry : hmc.entrySet()){
                    Console cs = entry.getValue();
                    items.add(new ProductItem(cs.getKey(),cs.getName(), 1, cs.getPrice(), cs.getImage(), cs.getManufacturer(), cs.getDiscount()));
                }
                break;
            case 2:
                HashMap<String, Console> hmc2 = new HashMap<String, Console>();
                hmc2.putAll(ConsoleHashMap.Microsoft);
                hmc2.putAll(ConsoleHashMap.Sony);
                hmc2.putAll(ConsoleHashMap.Nintendo);
                for(Map.Entry<String, Console> entry : hmc2.entrySet()){
                    Console console = entry.getValue();
                    for (Map.Entry<String, Accessory> entry2 : console.getAccessories().entrySet()) {
                        Accessory ac = entry2.getValue();
                        items.add(new ProductItem(ac.getKey(),ac.getName(), 2, ac.getPrice(), ac.getImage(), ac.getRetailer(), ac.getDiscount()));
                    }
                }
                break;
            case 3:
                HashMap<String, Game> hmg = new HashMap<String, Game>();
                hmg.putAll(GameHashMap.ElectronicArts);
                hmg.putAll(GameHashMap.Activision);
                hmg.putAll(GameHashMap.TakeTwoInteractive);
                for(Map.Entry<String, Game> entry : hmg.entrySet()){
                    Game gm = entry.getValue();
                    items.add(new ProductItem(gm.getKey(),gm.getName(), 3, gm.getPrice(), gm.getImage(), gm.getMaker(), gm.getDiscount()));
                }
                break;
            case 4:
                HashMap<String, Tablet> hmt = new HashMap<String, Tablet>();
                hmt.putAll(TabletHashMap.Apple);
                hmt.putAll(TabletHashMap.Microsoft);
                hmt.putAll(TabletHashMap.Samsung);
                for(Map.Entry<String, Tablet> entry : hmt.entrySet()){
                    Tablet tb = entry.getValue();
                    items.add(new ProductItem(tb.getKey(),tb.getName(), 4, tb.getPrice(), tb.getImage(), tb.getMaker(), tb.getDiscount()));
                }
                break;
        }
        
        return items;
    }    
    public static ProductItem getItem(String id, int type) {
        
        if (id == null || id.isEmpty()) {
            return null;
        }
        ArrayList<ProductItem> items = getItems(type);
        ProductItem item;
        for(int i = 0; i < items.size(); i++) {
            item = items.get(i);
            if (id.equals(item.getId())&& type == item.getType()) {
                return item;
            }
        }
        return null;
    }
    
    public static ArrayList<ProductItem> searchProduct(String keyword) {
        ArrayList<ProductItem> res = new ArrayList();
        ArrayList<ProductItem> list = getItems();
        if (keyword == null || keyword.isEmpty()) {
            return list;
        }
        ProductItem item;
        for(int i = 0; i < list.size(); i++) {
            item = list.get(i);
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                res.add(item);
            }
        }
        return res;
    }    
    
    public static ArrayList<String> autoCompleteProducts(String keyword) {
        ArrayList<String> res = new ArrayList();
        ArrayList<ProductItem> list = getItems();
        if (keyword == null || keyword.isEmpty()) {
            return res;
        }
        ProductItem item;
        for(int i = 0; i < list.size(); i++) {
            item = list.get(i);
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                res.add(item.getName());
            }
        }
        return res;
    }
    
    public static ProductItem getProduct(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        
        ArrayList<ProductItem> list = getItems();
        
        ProductItem item;
        for(int i = 0; i < list.size(); i++) {
            item = list.get(i);
            if (item.getId().equals(id)) {
                 return item;
            }
        }
        return null;
    }
}
