package com.parlour.business.presentation.wrapper;

import com.parlour.business.model.Category;

/**
 * 
 * @author DEB
 *
 */
public class SubCategoryResultItem extends CategoryResultItem{
	
	private static final long serialVersionUID = 10002L;

	public SubCategoryResultItem(Category subCategory) {
		super(subCategory);
	}

	@Override
	public int getItemType() {
		return ResultItem.SUBCATEGORY_TYPE;
	}

}
