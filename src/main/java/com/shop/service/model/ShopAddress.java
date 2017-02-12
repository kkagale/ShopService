package com.shop.service.model;

import javax.validation.constraints.NotNull;

public class ShopAddress {	
	private String number;
	@NotNull(message = "postalCode is required")
	private String postalCode;
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
