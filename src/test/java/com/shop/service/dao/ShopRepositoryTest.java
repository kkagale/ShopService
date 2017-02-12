package com.shop.service.dao;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.number.OrderingComparison.greaterThan;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.code.geocoder.model.LatLng;
import com.shop.service.geo.client.GeoClient;
import com.shop.service.model.Shop;
import com.shop.service.rest.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class ShopRepositoryTest {
	private float latitude = 18.42429f;
	private float longitude = 73.78539f;

	@InjectMocks
	ShopRepository shopRepository;

	@Mock
	GeoClient geoClient;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		LatLng latLng = new LatLng();
		latLng.setLat(new BigDecimal(latitude));
		latLng.setLng(new BigDecimal(longitude));
		when(geoClient.getGeoLocation(anyString())).thenReturn(latLng);
	}

	@Test
	public void testStore() {
		Shop shop = TestUtil.createDummyShop();
		shopRepository.store(shop);
		assertEquals(shop.getShopLatitude(), latitude, 0.0002);
		assertEquals(shop.getShopLongitude(), longitude, 0.0002);
	}

	@Test
	public void testFindClosetShop() {
		shopRepository.store(TestUtil.createDummyShop());
		Shop shop = shopRepository.findClosestShop(longitude, latitude);
		assertEquals(shop.getShopLatitude(), latitude, 0.0002);
		assertEquals(shop.getShopLongitude(), longitude, 0.0002);
	}

	@Test
	public void testFindClosetShopWithMutipleItems() {
		shopRepository.store(TestUtil.createDummyShop());
		Shop shop = shopRepository.findClosestShop(longitude, latitude);
		assertEquals(shop.getShopLatitude(), latitude, 0.0002);
		assertEquals(shop.getShopLongitude(), longitude, 0.0002);
	}

	@Test
	public void testCalculateDistance() {
		Shop shop1 = TestUtil.createDummyShop();
		shop1.setShopLatitude(latitude);
		shop1.setShopLongitude(longitude);

		Shop shop2 = TestUtil.createDummyShop();
		shop2.setShopLatitude(latitude + 1);
		shop2.setShopLongitude(longitude + 1);

		double distance = ShopRepository.calculateDistance(shop1, shop2);
		assertThat(distance, greaterThan(0.0));
	}

}
