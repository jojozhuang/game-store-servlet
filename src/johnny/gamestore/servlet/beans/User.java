package johnny.gamestore.servlet.beans;
public class User {
    private int id;
    private String name;
    private String password;
    private String usertype;

    public User(String name, String password, String usertype) {
        this.name=name;
        this.password=password;
        this.usertype=usertype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
