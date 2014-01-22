package com.parlour.business.utils;

/**
 * 
 * @author DEB
 *
 */
public class Response {
	
	private Object data;
	private int requestID;
	private boolean status;
	private String message;
	private Exception exception;
	
	public Response(Object data, int requestID, boolean status){
		this.data = data;
		this.requestID = requestID;
		this.status = status;
	}
	
	public Response(Object data, int requestID, boolean status, String message, Exception exception) {
		this(data, requestID, status);
		this.message = message;
		this.exception = exception;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	
}
