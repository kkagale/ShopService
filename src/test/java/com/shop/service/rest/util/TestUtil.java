package com.shop.service.rest.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.service.model.Shop;
import com.shop.service.model.ShopAddress;

public class TestUtil {
	
	public static String convertObjectToJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
	
	public static Shop createDummyShop(){
		Shop shop = new Shop();
    	shop.setShopName("Peter England");    	
    	ShopAddress shopAddress = new ShopAddress();
    	shopAddress.setNumber("123");
    	shopAddress.setPostalCode("411014");    	
    	shop.setShopAddress(shopAddress); 
    	return shop;
	}

}
