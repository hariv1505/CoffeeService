package com.thegs.coffeeapp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Payment {

    private String id;
    private String payType;
    private String amount;
    private String cardDetails;
    private String detail;

    public Payment(){

    }
    public Payment (String id, String payType, String amount){
        this.id = id;
        this.payType = payType;
        this.amount = amount;
        this.cardDetails = null;
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
		if (this.isCard() && cardDetails != null) {
			this.cardDetails = cardDetails;
		}
	}
	
	public boolean isCard() {
		return payType.equalsIgnoreCase("card");
	}
    
}
