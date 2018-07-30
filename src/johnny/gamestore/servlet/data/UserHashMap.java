package johnny.gamestore.servlet.data;
import java.util.HashMap;

import johnny.gamestore.servlet.beans.User;

public class UserHashMap {
    public static HashMap<String, User> Users = new HashMap<String, User>();

    public static final String CONST_TYPE_CUSTOMER = "Customer";
    public static final String CONST_TYPE_STOREMANAGER = "Store Manager";
    public static final String CONST_TYPE_SALESMAN = "Salesman";
    public static final String CONST_TYPE_CUSTOMER_LOWER = "customer";
    public static final String CONST_TYPE_STOREMANAGER_LOWER = "storemanager";
    public static final String CONST_TYPE_SALESMAN_LOWER = "salesman";

    public UserHashMap(){
        if(Users.isEmpty()){
            User user = new User("customer","customer", CONST_TYPE_CUSTOMER_LOWER);
            Users.put("customer",user);
            user = new User("storemanager","storemanager", CONST_TYPE_STOREMANAGER_LOWER);
            Users.put("storemanager",user);
            user = new User("salesman","salesman", CONST_TYPE_SALESMAN_LOWER);
            Users.put("salesman",user);
        }
    }
}
