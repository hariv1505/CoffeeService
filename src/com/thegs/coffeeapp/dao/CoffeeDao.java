package com.thegs.coffeeapp.dao;

import java.util.HashMap;
import java.util.Map;

import com.thegs.coffeeapp.model.Coffee;



public enum CoffeeDao {
    instance;

    private Map<String, Coffee> contentStore = new HashMap<String, Coffee>();

    private CoffeeDao() {

        Coffee b = new Coffee("1", "RESTful Web Services");
        b.setDetail("http://oreilly.com/catalog/9780596529260");
        contentStore.put("1", b);
        b = new Coffee("2", "RESTful Java with JAX-RS");
        b.setDetail("http://oreilly.com/catalog/9780596158057");
        contentStore.put("2", b);
    }
    public Map<String, Coffee> getStore(){
        return contentStore;
    }

}
