package by.bsuir.shop.entity;

/**
 * Created by ASUS on 11.04.2017.
 */
public class Subscription {

    private int id;
    private User user;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
