package com.thegs.coffeeapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="payments")
public class Payment {
	@Id
    private String id;
	@Column(name = "pay_type")
    private String payType;
    private String amount;
    @Column(name = "card_details")
    private String cardDetails;
    private String detail;

    public Payment(){

    }
    public Payment (String id, String payType, String amount){
        this.id = id;
        this.setPayType(payType);
        this.setAmount(amount);
    }
    
    public Payment (String id, String payType, String amount, String cardDetails){
        this.id = id;
        this.setPayType(payType);
        this.setAmount(amount);
        this.setCardDetails(cardDetails);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
	}
	
    
}
