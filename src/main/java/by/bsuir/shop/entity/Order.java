package by.bsuir.shop.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Entity class {@code Order} is the class, that describes orders in the system.
 * It contains basic methods to deal with shop objects.
 */
public class Order {
    private Integer id;
    private User customer;
    private String comment;
    private Date orderDate;
    private String address;
    private String status;
    private Boolean delivery;
    private Double price;
    private Map<ShopItem, Integer> items;

    public Integer getSize(){
        Integer size = 0;

        if(items != null){
            Iterator iterator = items.values().iterator();
            while (iterator.hasNext()){
                size += (Integer) iterator.next();
            }
        }

        return size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Map<ShopItem, Integer> getItems() {
        return items;
    }

    public void setItems(Map<ShopItem, Integer> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (comment != null ? !comment.equals(order.comment) : order.comment != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (delivery != null ? !delivery.equals(order.delivery) : order.delivery != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        return items != null ? items.equals(order.items) : order.items == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (delivery != null ? delivery.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", comment='" + comment + '\'' +
                ", orderDate=" + orderDate +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", delivery=" + delivery +
                ", price=" + price +
                ", items=" + items +
                '}';
    }
}
