package com.thegs.coffeeapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="orders")
public class Order {
	@Id
    private String id;
	@Column(name = "coffee_type")
    private String coffeeType;
    private String cost;
    private String additions;
    private String status;
    private String released;

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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
    
}
