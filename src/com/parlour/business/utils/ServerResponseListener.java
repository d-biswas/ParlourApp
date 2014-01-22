package com.parlour.business.utils;

/**
 * 
 * @author DEB
 *
 */
public interface ServerResponseListener {
	/**
	 * Call back method to send server response.
	 * @param response object of {@link Response}
	 */
	public void serverResponse(Response response);

}
