package com.thegs.coffeeapp.dao;

import java.util.HashMap;
import java.util.Map;

import com.thegs.coffeeapp.model.Payment;



public enum PaymentDao {
    instance;

    private Map<String, Payment> contentStore = new HashMap<String, Payment>();

    private PaymentDao() {

    	//not created yet
        //Payment p = new Payment("3", "card", "3.20");
		//p.setCardDetails("9874654321");
        //contentStore.put(p.getId(), p);
    }
    public Map<String, Payment> getStore(){
        return contentStore;
    }

}
