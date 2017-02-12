package com.shop.service.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Shop {	
	@NotNull(message = "shopName is required")
	private String shopName;
	@Valid
	@NotNull( message = "shopAddress is required")
	private ShopAddress shopAddress;	
	private float shopLongitude;
	private float shopLatitude;
	
	public String getShopName() {
		return shopName;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public ShopAddress getShopAddress() {
		return shopAddress;
	}
	
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}
	
	public float getShopLongitude() {
		return shopLongitude;
	}
	
	public void setShopLongitude(float shopLongitude) {
		this.shopLongitude = shopLongitude;
	}
	
	public float getShopLatitude() {
		return shopLatitude;
	}
	
	public void setShopLatitude(float shopLatitude) {
		this.shopLatitude = shopLatitude;
	}
}
