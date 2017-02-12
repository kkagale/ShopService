package com.shop.service.dao;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.code.geocoder.model.LatLng;
import com.shop.service.geo.client.GeoClient;
import com.shop.service.model.Shop;

@Component
public class ShopRepository {
	public static final Logger log = LoggerFactory.getLogger(ShopRepository.class);
	private List<Shop> shopHolder = new CopyOnWriteArrayList<>();
	private GeoClient geoClient;
	
	@Autowired
	public ShopRepository(GeoClient geoClient){
		this.geoClient = geoClient;
	}
	

	public Shop store(Shop shop) {
		log.info("Fetching longitude and latitude for postal code {}", shop.getShopAddress().getPostalCode());
		LatLng location = geoClient.getGeoLocation(shop.getShopAddress().getPostalCode());
		shop.setShopLatitude(location.getLat().floatValue());
		shop.setShopLongitude(location.getLng().floatValue());
		this.shopHolder.add(shop);
		return shop;
	}
	
	public Shop findClosestShop(float customerLongitude, float customerLatitude) {
		log.info("Finding closest shop for location longitude {} and latitude  {}", customerLongitude, customerLatitude);
		Shop closestShop = null;
		Shop target = new Shop();
		target.setShopLongitude(customerLongitude);
		target.setShopLatitude(customerLatitude);
		double minDistance = -1;

		for (Shop shop : this.shopHolder) {
			double distance = calculateDistance(target, shop);
			if (minDistance == -1 || distance < minDistance) {
				minDistance = distance;
				closestShop = shop;
			}
		}
		return closestShop;
	}	

	public static double calculateDistance(Shop shop1, Shop shop2) {
		double theta = shop1.getShopLongitude() - shop2.getShopLongitude();
		double dist = Math.sin(deg2rad(shop1.getShopLatitude())) * Math.sin(deg2rad(shop2.getShopLatitude()))
				+ Math.cos(deg2rad(shop1.getShopLatitude())) * Math.cos(deg2rad(shop2.getShopLatitude()))
						* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		return dist;
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
