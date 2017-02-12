package com.shop.service.geo.client;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

@Component
public class GeoClient {

	public LatLng getGeoLocation(String postalCode) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(postalCode).getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		List<GeocoderResult> results = geocoderResponse.getResults();
		return results.get(0).getGeometry().getLocation();
	}
}
