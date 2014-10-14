package com.thegs.coffeeapp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

    private String id;
    private String coffeeType;
    private String cost;
    private String additions;
    private String detail;

    public Order(){

    }
    public Order (String id, String coffeeType, String cost, String additions){
        this.id = id;
        this.setCoffeeType(coffeeType);
        this.setCost(cost);
        this.setAdditions(additions);
    }
    public Order (String id, String coffeeType, String cost){
        this.id = id;
        this.setCoffeeType(coffeeType);
        this.setCost(cost);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getCoffeeType() {
		return coffeeType;
	}
	public void setCoffeeType(String coffeeType) {
		this.coffeeType = coffeeType;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAdditions() {
		return additions;
	}
	public void setAdditions(String additions) {
		this.additions = additions;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
    
}
