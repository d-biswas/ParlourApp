package com.parlour.business.utils;

import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author DEB
 *
 */
public class DispatchHandler extends Handler{
	private ServerResponseListener serverResponseListener;
	private Response response;
	public DispatchHandler(ServerResponseListener serverResponseListener)
	{
		this.serverResponseListener = serverResponseListener;
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		serverResponseListener.serverResponse(response);
	}
	
	public void setResponse(Response response)
	{
		this.response = response;
	}

}
