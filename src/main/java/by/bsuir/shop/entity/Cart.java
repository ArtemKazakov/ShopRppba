package by.bsuir.shop.entity;

import java.util.*;

/**
 * Entity class {@code Cart} is the class, that describes Cart of this system.
 * It contains basic methods to deal with shop objects.
 */
public class Cart {
    private HashMap<ShopItem, Integer> items;

    public Cart(){
        items = new HashMap<>();
    }

    public HashMap<ShopItem, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<ShopItem, Integer> items) {
        this.items = items;
    }

    public void addItem(ShopItem item){
        if(items.containsKey(item)){
            items.put(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
    }

    public void removeItem(ShopItem item) {
        if (items.get(item) == 1){
            items.remove(item);
        } else {
            items.put(item, items.get(item) - 1);
        }
    }

    public Double getCost(){
        Double cost = 0.0;

        Iterator iterator = items.keySet().iterator();
        ShopItem item;
        while(iterator.hasNext()){
            item = (ShopItem) iterator.next();
            cost += item.getPrice();
        }

        return cost;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return items != null ? items.equals(cart.items) : cart.items == null;

    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }
}
