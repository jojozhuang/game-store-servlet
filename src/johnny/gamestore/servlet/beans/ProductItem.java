package johnny.gamestore.servlet.beans;
public class ProductItem {
    private String id;
    private String name;
    private int type; //  1.Console 2.Accessory, 3.Game, 4.Tablet
    private double price;
    private String image;
    private String maker;
    private double discount;

    public ProductItem(String id, String name, int type, double price, String image, String maker, double discount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.maker = maker;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
    
    public String getMaker() {
        return maker;
    }
    
    public void setMaker(String maker) {
        this.maker = maker;
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
