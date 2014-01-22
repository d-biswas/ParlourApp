package com.parlour.business.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Message;

/**
 * 
 * @author DEB
 *
 */
public class ServerConnector implements Runnable{
	
	public static final String HTTP_GET_METHOD = "GET";
	public static final String HTTP_POST_METHOD = "POST";
	
	private String httpMethod;
	private String serverURL;
	private int requestID;
	
	private DispatchHandler dispatchHandler;
	private ServerResponseListener serverResponseListener;
	private Hashtable<String, String> params;
	
	public ServerConnector(ServerResponseListener serverResponseListener, 
			int requestID, String serverURL, String httpMethod, 
			Hashtable<String, String> params){
		this.serverResponseListener = serverResponseListener;
		this.requestID = requestID;
		this.serverURL = serverURL;
		this.httpMethod = (httpMethod != null) ? httpMethod : HTTP_GET_METHOD;	
		this.params = params;
	}
	
	public void connect(){
		dispatchHandler = new DispatchHandler(serverResponseListener);
		(new Thread(this)).start();
    }

	@Override
	public void run() {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpUriRequest httpRequest = null;
		if(httpMethod.equalsIgnoreCase(HTTP_GET_METHOD)){
			httpRequest = new HttpGet(serverURL);
		}
		else if(httpMethod.equalsIgnoreCase(HTTP_POST_METHOD)) {
			httpRequest = new HttpPost(serverURL);
		}
        
		if(httpRequest != null){
	        try {
	        	// Execute the request
	            HttpResponse response;
	        	if(httpRequest instanceof HttpPost && params != null){
	        		((HttpPost)httpRequest).setEntity(new UrlEncodedFormEntity(getNameValuePairs(params)));
	        	}
	            response = httpclient.execute(httpRequest);
	            
	            // Get hold of the response entity
	            HttpEntity entity = response.getEntity();
	            // If the response does not enclose an entity, there is no need
	            // to worry about connection release
	 
	            if (entity != null) {
	 
	                // A Simple JSON Response Read
	                InputStream instream = entity.getContent();
	                String result= convertStreamToString(instream);
	                
	                if (this.dispatchHandler != null)
					{
						this.dispatchHandler.setResponse(new Response(new String(result), this.requestID, true));
						this.dispatchHandler.sendMessage(new Message());
					}
	 
	                // Closing the input stream will trigger connection release
	                instream.close();
	            }
	            
	        } catch (Exception e) {
	        	if (this.dispatchHandler != null)
				{
					this.dispatchHandler.setResponse(new Response(null, this.requestID, false, e.getMessage(), e));
					this.dispatchHandler.sendMessage(new Message());
				}
				System.out.println("Exception in Server connetion");
	            e.printStackTrace();
	        }
		}
	}
	
	private String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
	private ArrayList<NameValuePair> getNameValuePairs(Hashtable<String, String> params){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null){
			Enumeration <String>elements = params.keys();			
			int i = 0;
			while (elements.hasMoreElements()) {
				String key = (String) elements.nextElement();
				String value = (String) params.get(key);
				nameValuePairs.add(new BasicNameValuePair(key, value));
				i++;
			}
		}
		return nameValuePairs;
	}
	
}
