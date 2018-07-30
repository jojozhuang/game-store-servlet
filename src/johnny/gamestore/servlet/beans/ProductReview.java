package johnny.gamestore.servlet.beans;

import java.util.Date;

/**
 *
 * @author Johnny
 */
public class ProductReview {
    private String id;
    private String username;
    private int rating;    
    private Date reviewdate;
    private String reviewtext;
    public ProductReview(String id, String username, int rating, Date reviewdate, String reviewtext) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.reviewdate = reviewdate;
        this.reviewtext = reviewtext;
    }
    
    public String getId() {
        return id;
    }
    
    public String getUserName() {
        return username;
    }
    
    public int getRating() {        
        return rating;
    }    
    
    public Date getReviewDate() {
        return reviewdate;
    }    
        
    public String getReviewText() {
        return reviewtext;
    }
}
