package com.shop.service.rest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static com.shop.service.rest.util.TestUtil.convertObjectToJsonString;
import static com.shop.service.rest.util.TestUtil.createDummyShop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.code.geocoder.model.LatLng;
import com.shop.service.dao.ShopRepository;
import com.shop.service.geo.client.GeoClient;
import com.shop.service.model.Shop;
import com.shop.service.rest.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest {

	@InjectMocks
	ShopService shopService;

	GeoClient geoClient = Mockito.mock(GeoClient.class);

	@Spy
	ShopRepository shopRepository = new ShopRepository(geoClient);

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		LatLng latLng = new LatLng();
		latLng.setLat(new BigDecimal(18.42429f));
		latLng.setLng(new BigDecimal(78.42429f));
		when(geoClient.getGeoLocation(anyString())).thenReturn(latLng);
		this.mvc = MockMvcBuilders.standaloneSetup(shopService).build();
		Shop shop = TestUtil.createDummyShop();
		shopRepository.store(shop);
	}

	@Test
	public void testFindClosestShop() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/v1/shopservice?customerLongitude=73.555798&customerLatitude=18.5555")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testFindWithMissingRequiredParams() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/v1/shopservice?customerLongitude=73.555798")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateShop() throws Exception {
		Shop shop = createDummyShop();
		mvc.perform(MockMvcRequestBuilders.post("/v1/shopservice").content(convertObjectToJsonString(shop))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreateShopWithMissingShopName() throws Exception {
		Shop shop = createDummyShop();
		shop.setShopName(null);
		mvc.perform(MockMvcRequestBuilders.post("/v1/shopservice").content(convertObjectToJsonString(shop))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateShopWithMissingAddress() throws Exception {
		Shop shop = createDummyShop();
		shop.setShopAddress(null);
		mvc.perform(MockMvcRequestBuilders.post("/v1/shopservice").content(convertObjectToJsonString(shop))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateShopWithMissingPostalCode() throws Exception {
		Shop shop = createDummyShop();
		shop.getShopAddress().setPostalCode(null);
		mvc.perform(MockMvcRequestBuilders.post("/v1/shopservice").content(convertObjectToJsonString(shop))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
