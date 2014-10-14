package com.thegs.coffeeapp.dao;

import java.util.HashMap;
import java.util.Map;

import com.thegs.coffeeapp.model.Order;



public enum OrderDao {
    instance;

    private Map<String, Order> contentStore = new HashMap<String, Order>();

    private OrderDao() {

        Order o = new Order("1", "Latte", "3.70");
        o.setDetail("http://oreilly.com/catalog/9780596529260");
        contentStore.put(o.getId(), o);
        o = new Order("2", "Capp", "4.50", "Extra Milk");
        o.setDetail("http://oreilly.com/catalog/9780596158057");
        contentStore.put(o.getId(), o);
    }
    public Map<String, Order> getStore(){
        return contentStore;
    }

}
