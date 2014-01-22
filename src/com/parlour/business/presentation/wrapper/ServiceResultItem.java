package com.parlour.business.presentation.wrapper;

import com.parlour.business.model.Service;

/**
 * 
 * @author DEB
 *
 */
public class ServiceResultItem implements ResultItem{

	private static final long serialVersionUID = 10003L;
	Service service;
	
	public ServiceResultItem (Service service) {
		this.service = service;
	}

	@Override
	public Object getItem() {
		return service;
	}

	@Override
	public int getItemType() {
		return SERVICE_TYPE;
	}

}
