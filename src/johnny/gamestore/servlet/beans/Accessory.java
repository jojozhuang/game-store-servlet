package johnny.gamestore.servlet.beans;

public class Accessory {
    private String key;
    private String console;
    private String name;
    private double price;
    private String image;
    private String retailer;
    private String condition;
    private double discount;

    public Accessory(String key, String console, String name, double price, String image, String retailer,String condition,double discount){
        this.key = key;
        this.console = console;
        this.name = name;
        this.price = price;
        this.image = image;
        this.retailer = retailer;
        this.condition = condition;
        this.discount = discount;
    }

    public Accessory() {

    }

    public String getKey() {
        return key;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
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
