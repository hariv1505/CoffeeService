package com.thegs.coffeeapp.dao;

import java.util.HashMap;
import java.util.Map;

import com.thegs.coffeeapp.model.Payment;



public enum PaymentDao {
    instance;

    private Map<String, Payment> contentStore = new HashMap<String, Payment>();

    private PaymentDao() {

        Payment p = new Payment("1", "Cash", "3.70");
        contentStore.put(p.getId(), p);
        p = new Payment("2", "Card", "4.50");
        p.setCardDetails("123456789");
        contentStore.put(p.getId(), p);
    }
    public Map<String, Payment> getStore(){
        return contentStore;
    }

}
