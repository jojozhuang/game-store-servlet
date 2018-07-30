package johnny.gamestore.servlet.beans;
public class Game {
    private String key;
    private String maker;
    private String name;
    private double price;
    private String image;
    private String retailer;
    private String condition;
    private double discount;

    public Game(String key, String maker, String name, double price, String image, String retailer,String condition,double discount){
        this.key = key;
        this.maker = maker;
        this.name = name;
        this.price = price;
        this.image = image;
        this.condition = condition;
        this.discount = discount;
        this.retailer = retailer;
    }

    public Game(){

    }

    public String getKey() {
        return key;
    }
    public String getMaker() {
        return maker;
    }
    public void setMaker(String maker) {
        this.maker = maker;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getRetailer() {
        return retailer;
    }
    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getDiscountedPrice() {
        return price * (100 - discount) / 100;
    }
}
