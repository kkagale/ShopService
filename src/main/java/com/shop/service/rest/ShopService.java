package com.shop.service.rest;

import org.springframework.web.bind.annotation.RestController;

import com.shop.service.dao.ShopRepository;
import com.shop.service.model.Shop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "ShopService", produces = "application/json")
@RestController
@RequestMapping("/v1/shopservice")
public class ShopService {
	public static final Logger log = LoggerFactory.getLogger(ShopService.class);

	@Autowired
	private ShopRepository shopRepo;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new shop", notes = "Creates new shop")
	@ApiResponses(value = { 
					@ApiResponse(code = 400, message = "Fields are with validation errors"),
					@ApiResponse(code = 201, message = "Created shop") 
				})
	public ResponseEntity<Object> create(@Validated @RequestBody Shop shop) {
		log.info("Storing shop {}", shop.getShopName());
		Shop createdShop = shopRepo.store(shop);
		return new ResponseEntity<Object>(createdShop,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, params = { "customerLongitude", "customerLatitude" })
	@ApiOperation(value = "Find closest shop", notes = "Find closest shop")
	@ApiResponses(value = {
					@ApiResponse(code = 400, message = "Fields are with validation errors"),
					@ApiResponse(code = 200, message = "") 
				})
	public ResponseEntity<Object> find(@RequestParam(value = "customerLongitude") float customerLongitude,
			@RequestParam(value = "customerLatitude") float customerLatitude) {
		Shop closestShop =  shopRepo.findClosestShop(customerLongitude, customerLatitude);		
		if(closestShop == null){			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(closestShop,HttpStatus.OK);
	}
}