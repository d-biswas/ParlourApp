package com.parlour.business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Model class to represent Service
 * @author DEB
 */
public class Service implements Serializable{

	public static final long serialVersionUID = 20002L;
	private int serviceId;
	private int parentCategoryId;
	private String serviceName;
	private String serviceDesc;
	private double servicePrice = 10.0;
	private String serviceNote;
	private double serviceDuration = 1.30;
	private int iconId = -1;
	
	private List<ServiceImage> serviceImages;
	private List<ServicePrice> specialPrices;
	
	public Service(){
		specialPrices = new ArrayList<ServicePrice>();
		serviceImages = new ArrayList<ServiceImage>();
	}
	
	public Service(JSONObject jsonObject) throws JSONException{
		this();
		if(jsonObject.has(JsonKey.SERVICE_ID) && !jsonObject.isNull(JsonKey.SERVICE_ID)){
			this.serviceId = jsonObject.getInt(JsonKey.SERVICE_ID);
		}

		if(jsonObject.has(JsonKey.NAME) && !jsonObject.isNull(JsonKey.NAME)){
			this.serviceName = jsonObject.getString(JsonKey.NAME);
		}

		if(jsonObject.has(JsonKey.PRICE) && !jsonObject.isNull(JsonKey.PRICE)) {
			this.servicePrice = jsonObject.getDouble(JsonKey.PRICE);
		}
		if(jsonObject.has(JsonKey.NOTES) && !jsonObject.isNull(JsonKey.NOTES)) {
			this.serviceNote = jsonObject.getString(JsonKey.NOTES);
		}
		
	}
	
	public void setDetails(JSONObject jsonObject) throws JSONException{
		if(jsonObject.has(JsonKey.DESCRIPTION) && !jsonObject.isNull(JsonKey.DESCRIPTION)) {
			this.serviceDesc = jsonObject.getString(JsonKey.DESCRIPTION);
		}
		if(jsonObject.has(JsonKey.PARENT_ID) && !jsonObject.isNull(JsonKey.PARENT_ID)){
			this.parentCategoryId = jsonObject.getInt(JsonKey.PARENT_ID);
		}
		if(jsonObject.has(JsonKey.DURATION) && !jsonObject.isNull(JsonKey.DURATION)) {
			this.serviceDuration = jsonObject.getDouble(JsonKey.DURATION);
		}
		if(jsonObject.has(JsonKey.SPECIAL_PRICE) && !jsonObject.isNull(JsonKey.SPECIAL_PRICE)) {
			JSONArray specialPrices = jsonObject.getJSONArray(JsonKey.SPECIAL_PRICE);
			for(int i=0;i<specialPrices.length();i++){                        					      
			    JSONObject obj = specialPrices.getJSONObject(i);
			    ServicePrice servicePrice = new ServicePrice(obj);
			    this.specialPrices.add(servicePrice);	           
			}
		}
		if(jsonObject.has(JsonKey.SERVICE_IMAGES) && !jsonObject.isNull(JsonKey.SERVICE_IMAGES)) {
			JSONArray serviceImages = jsonObject.getJSONArray(JsonKey.SERVICE_IMAGES);
			for(int i=0;i<serviceImages.length();i++){                        					      
			    JSONObject obj = serviceImages.getJSONObject(i);
			    ServiceImage serviceImage = new ServiceImage(obj);
			    this.serviceImages.add(serviceImage);	           
			}
		}
	}
	
	public Service(int serviceId, int parentCategoryId, String serviceName, 
			String serviceDesc, double servicePrice, String serviceNote, double serviceDuration, int iconId) {
		this.serviceId = serviceId;
		this.parentCategoryId = parentCategoryId;
		this.serviceName = serviceName;
		this.serviceDesc = serviceDesc;
		this.servicePrice = servicePrice;
		this.serviceNote = serviceNote;
		this.serviceDuration = serviceDuration;
		this.iconId = iconId;
		
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getServiceNote() {
		return serviceNote;
	}

	public void setServiceNote(String serviceNote) {
		this.serviceNote = serviceNote;
	}
	
	

	public double getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(double serviceDuration) {
		this.serviceDuration = serviceDuration;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public List<ServiceImage> getServiceImages() {
		return serviceImages;
	}

	public void setServiceImages(List<ServiceImage> serviceImages) {
		this.serviceImages = serviceImages;
	}	

}
