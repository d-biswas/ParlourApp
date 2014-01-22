package com.parlour.business.presentation.wrapper;

import com.parlour.business.model.Category;

/**
 * 
 * @author DEB
 *
 */
public class CategoryResultItem implements ResultItem{

	private static final long serialVersionUID = 10001L;
	Category category;
	
	public CategoryResultItem (Category category){
		this.category = category; 
	}

	@Override
	public Object getItem() {
		return this.category;
	}

	@Override
	public int getItemType() {
		return CATEGORY_TYPE;
	}

}
