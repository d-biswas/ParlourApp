package com.parlour.business.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class to represent Service Price
 * @author DEB
 *
 */
public class ServicePrice implements Serializable {
	
	private static final long serialVersionUID = 20007L;
	
	private int priceId;
	private String name;
	private double price;
	
	public ServicePrice(){
		
	}
	
	public ServicePrice(JSONObject jsonObj) throws JSONException {
		if(jsonObj.has(JsonKey.PRICE_ID) && !jsonObj.isNull(JsonKey.PRICE_ID)) {
			this.priceId = jsonObj.getInt(JsonKey.PRICE_ID);
		}
		if(jsonObj.has(JsonKey.NAME) && !jsonObj.isNull(JsonKey.NAME)) {
			this.name = jsonObj.getString(JsonKey.NAME);
		}
		if(jsonObj.has(JsonKey.PRICE) && !jsonObj.isNull(JsonKey.PRICE)) {
			this.price = jsonObj.getDouble(JsonKey.PRICE);
		}
	}

	public int getPriceId() {
		return priceId;
	}

	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	
}
