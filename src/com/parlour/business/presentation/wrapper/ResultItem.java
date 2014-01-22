package com.parlour.business.presentation.wrapper;

import java.io.Serializable;

/**
 * 
 * @author DEB
 *
 */
public interface ResultItem extends Serializable{
	
	public int CATEGORY_TYPE = 1001;
	public int SUBCATEGORY_TYPE = 2001;
	public int SERVICE_TYPE = 3001;
	
	public Object getItem();
	
	public int getItemType();

}
