package com.parlour.business.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class to represent ServiceImage
 * @author DEB
 *
 */
public class ServiceImage implements Serializable{

	private static final long serialVersionUID = 20005L;
	private int imageId;
	private String imageUrl;
	
	public ServiceImage(){
		
	}
	
	public ServiceImage(JSONObject jsonObject) throws JSONException{
		if(jsonObject.has(JsonKey.IMAGE_ID) && !jsonObject.isNull(JsonKey.IMAGE_ID)) {
			this.imageId = jsonObject.getInt(JsonKey.IMAGE_ID);
		}
		if(jsonObject.has(JsonKey.IMAGE_URL) && !jsonObject.isNull(JsonKey.IMAGE_URL)) {
			this.imageUrl = jsonObject.getString(JsonKey.IMAGE_URL);
		}
	}
	
	public ServiceImage(int imageId, String imageUrl){
		this.imageId = imageId;
		this.imageUrl = imageUrl;
		
	}

	public int getImageId() {
		return imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

}
