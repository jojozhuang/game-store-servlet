package johnny.gamestore.servlet.data;
import java.util.HashMap;

import johnny.gamestore.servlet.beans.Tablet;

public class TabletHashMap {
    public static HashMap<String, Tablet> Apple = new HashMap<String, Tablet>();
    public static HashMap<String, Tablet> Microsoft = new HashMap<String, Tablet>();
    public static HashMap<String, Tablet> Samsung = new HashMap<String, Tablet>();
    
    public static final String CONST_APPLE = "Apple";
    public static final String CONST_MICROSOFT = "Microsoft";
    public static final String CONST_SAMSUNG = "Samsung";

    public static final String CONST_APPLE_LOWER = "apple";
    public static final String CONST_MICROSOFT_LOWER = "microsoft";
    public static final String CONST_SAMSUNG_LOWER = "samsung";

    public TabletHashMap() {
        if(Apple.isEmpty()){
            Tablet ap_ipadpro = new Tablet("ap_ipadpro", CONST_APPLE, "iPad Pro 128GB",949.99,"tablets/ipadpro.jpg",CONST_APPLE,"New",10);
            Tablet ap_ipadair = new Tablet("ap_ipadair", CONST_APPLE, "iPad Air 2 16GB - Gold",399.99,"tablets/ipadair.jpg",CONST_APPLE,"New",10);
            Apple.put("ap_ipadpro", ap_ipadpro);
            Apple.put("ap_ipadair", ap_ipadair);
        }
        if(Microsoft.isEmpty()){
            Tablet ms_surface3 = new Tablet("ms_surface3", CONST_MICROSOFT, "Surface 3 - 10.8 128GB Silver",549.99,"tablets/surface3.jpg",CONST_MICROSOFT,"New",10);
            Tablet ms_surface4 = new Tablet("ms_surface4", CONST_MICROSOFT, "Surface 4 12.3 128GB Silver",999.99,"tablets/surface4.jpg",CONST_MICROSOFT,"New",10);
            Microsoft.put("ms_surface3", ms_surface3);
            Microsoft.put("ms_surface4", ms_surface4);
        }
        if(Samsung.isEmpty()){
            Tablet ss_galaxya = new Tablet("ss_galaxya", CONST_SAMSUNG, "Galaxy Tab A - 9.7 - 16GB ",299.99,"tablets/galaxya.jpg",CONST_SAMSUNG,"New",10);
            Tablet ss_kidse = new Tablet("ss_kidse", CONST_SAMSUNG, "Kids Galaxy Tab E Lite 7 8GB",129.99,"tablets/kidspad.jpg",CONST_SAMSUNG,"New",10);
            Samsung.put("ss_galaxya", ss_galaxya);
            Samsung.put("ss_kidse", ss_kidse);
        }
    }
}
