package johnny.gamestore.servlet.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import johnny.gamestore.servlet.beans.ProductReview;

/**
 *
 * @author Johnny
 */
public class ReviewHashMap {
    public static HashMap<String, ArrayList<ProductReview>> reviews = new HashMap<String, ArrayList<ProductReview>>();
    public ReviewHashMap() {
        ArrayList<ProductReview> list = new ArrayList<ProductReview>();
        ProductReview review = new ProductReview("1", "customer", 5, new Date(), "Easy to use, funny!");
        list.add(review);
        ProductReview review2 = new ProductReview("2", "storemanager", 4, new Date(), "Like it!");
        list.add(review2);
        reviews.put("xbox360", list);
        ArrayList<ProductReview> list2 = new ArrayList<ProductReview>();
        ProductReview review21 = new ProductReview("1", "customer", 3, new Date(), "Too expensive, doesn't worth");
        list2.add(review21);
        reviews.put("xbox360_wa", list2);
        ArrayList<ProductReview> list3 = new ArrayList<ProductReview>();
        ProductReview review31 = new ProductReview("1", "customer", 5, new Date(), "Great game, I spent all weekend playing with it.");
        list3.add(review31);
        reviews.put("ea_nfs", list3);
    }
}
