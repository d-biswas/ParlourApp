package com.parlour.business.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model class to represent Category and SubCategory
 * @author DEB
 */
public class Category implements Serializable{

	public static final long serialVersionUID = 20001L;
	private int categoryId;
	private String categoryName;
	private String categoryDesc;
	private int parentCategoryId;
	private String notes;
	private int iconId = -1;
	
	public Category(){
		
	}
	
	/**
	 * 
	 * @param jsonObject Category json object
	 * @throws JSONException
	 */
	public Category(JSONObject jsonObject) throws JSONException {	
		if(jsonObject.has(JsonKey.CATEGORY_ID) && !jsonObject.isNull(JsonKey.CATEGORY_ID)){
			this.categoryId = jsonObject.getInt(JsonKey.CATEGORY_ID);
		}
		if(jsonObject.has(JsonKey.NAME) && !jsonObject.isNull(JsonKey.NAME)) {
			this.categoryName = jsonObject.getString(JsonKey.NAME);
		}
//		if(jsonObject.has(JsonKey.DESCRIPTION) && jsonObject.getString(JsonKey.DESCRIPTION) != null) {
//			this.categoryDesc = jsonObject.getString(JsonKey.DESCRIPTION);
//		}
//		if(jsonObject.has(JsonKey.PARENT_ID)) {
//			this.parentCategoryId = jsonObject.getInt(JsonKey.PARENT_ID);
//		}
		if(jsonObject.has(JsonKey.NOTES) && !jsonObject.isNull(JsonKey.NOTES)){
			this.notes = jsonObject.getString(JsonKey.NOTES);
		}		
	}
	
	/**
	 * 
	 * @param categoryId id of the category
	 * @param categoryName name of the category
	 * @param categoryDesc description of the category
	 * @param parentCategory parent category of this category otherwise null
	 */
	public Category(int categoryId, String categoryName, String categoryDesc, int parentCategoryId, int iconId){
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDesc = categoryDesc;
		this.parentCategoryId = parentCategoryId;
		this.iconId = iconId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public int getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	

}
