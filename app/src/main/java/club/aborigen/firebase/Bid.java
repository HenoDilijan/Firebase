package club.aborigen.firebase;

import java.util.Date;

public class Bid {
    private Date timestamp;
    private String user;
    private String type;
    private Integer amount;
    private Integer price;

    public Bid(String user, String type, Integer amount, Integer price) {
        this.timestamp = new Date();
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
