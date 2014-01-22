package com.parlour.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parlour.business.adapter.ListAdapter;
import com.parlour.business.model.Category;
import com.parlour.business.model.JsonKey;
import com.parlour.business.model.Service;
import com.parlour.business.model.ServiceImage;
import com.parlour.business.presentation.wrapper.CategoryResultItem;
import com.parlour.business.presentation.wrapper.ResultItem;
import com.parlour.business.presentation.wrapper.ServiceResultItem;
import com.parlour.business.presentation.wrapper.SubCategoryResultItem;
import com.parlour.business.utils.DataCollector;
import com.parlour.business.utils.Response;
import com.parlour.business.utils.ServerConnector;
import com.parlour.business.utils.ServerResponseListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ServicesCategoryListActivity extends BaseActivity implements OnItemClickListener, ServerResponseListener {
	
	private Handler handler;
	private ProgressDialog progDialog;
	
	ListView listView;
    List<ResultItem> rowItems = new ArrayList<ResultItem>();
    ResultItem resultItem;
    private TextView pageTitle;
    private final int SUBCAT_AND_OR_SERVC_REQ = 9001;
    private final int SERVICE_DETAIL_REQ = 9003;
    
	/** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        handler = new Handler();
        pageTitle = (TextView)findViewById(R.id.pageTitle);
        String categoryName = getIntent().getStringExtra("itemName");
        if(categoryName != null && !categoryName.equals("")){
        	pageTitle.setText(categoryName);
        }
        
        HashMap map = (HashMap)getIntent().getSerializableExtra("map");
        rowItems = (List<ResultItem>) map.get("itemList");
 
        listView = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(ServicesCategoryListActivity.this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
 
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
    	resultItem = rowItems.get(position);
    	if(resultItem.getItemType() == ResultItem.CATEGORY_TYPE || 
    			resultItem.getItemType() == ResultItem.SUBCATEGORY_TYPE) {
    		
    		final String msg = (resultItem.getItemType() == ResultItem.CATEGORY_TYPE) ? 
    				"Fetching subcategories and/or services..." : "Fetching services...";
    		handler.post(new Runnable() {						
				public void run() {
					progDialog = ProgressDialog.show(ServicesCategoryListActivity.this, "Please Wait.", msg, true, false);							
				}
			});
    		String serverURL = "http://trinayanapps.com/webservice/category/getAllSubCategoriesAndServices";
			StringBuilder URLBuilder = new StringBuilder(serverURL);
			URLBuilder.append("?categoryID=").append( ((Category)resultItem.getItem()).getCategoryId() );
			
			new ServerConnector(this, SUBCAT_AND_OR_SERVC_REQ, URLBuilder.toString(), ServerConnector.HTTP_GET_METHOD, null).connect();
    		
    		
    	}
    	else if(resultItem.getItemType() == ResultItem.SERVICE_TYPE) {
    		handler.post(new Runnable() {						
				public void run() {
					progDialog = ProgressDialog.show(ServicesCategoryListActivity.this, "Please Wait.", "Fetching service detail...", true, false);							
				}
			});
    		String serverURL = "http://trinayanapps.com/webservice/service/getDetailOfAService";
			StringBuilder URLBuilder = new StringBuilder(serverURL);
			URLBuilder.append("?serviceID=").append( ((Service)resultItem.getItem()).getServiceId() );
			
			new ServerConnector(this, SERVICE_DETAIL_REQ, URLBuilder.toString(), ServerConnector.HTTP_GET_METHOD, null).connect();
    		
    	}
        
    }

	@Override
	protected int getLayoutResourceId() {
		return R.layout.services_category_list;
	}

	@Override
	public void serverResponse(Response response) {
		handler.post(new Runnable() 
		{			
			public void run() {
				progDialog.dismiss();
				progDialog = null;				
			}			
		});	
		if (response != null && response.isStatus() == true) {
			if(response.getRequestID() == SUBCAT_AND_OR_SERVC_REQ) {
				
				List<ResultItem> resultList = new ArrayList<ResultItem>();
				try {
					JSONObject jsonObj = new JSONObject((String)response.getData());
					if(jsonObj.has(JsonKey.RESULT) && !jsonObj.isNull(JsonKey.RESULT)){
						JSONObject jsonResultObj = jsonObj.getJSONObject(JsonKey.RESULT);
						JSONArray jsonSubCategories = null;
						JSONArray jsonServices = null;
						if(jsonResultObj.has(JsonKey.SUBCATEGORIES) && !jsonResultObj.isNull(JsonKey.SUBCATEGORIES)) {
							jsonSubCategories = jsonResultObj.getJSONArray(JsonKey.SUBCATEGORIES);
						}
						if(jsonResultObj.has(JsonKey.SERVICES) && !jsonResultObj.isNull(JsonKey.SERVICES)) {
							jsonServices = jsonResultObj.getJSONArray(JsonKey.SERVICES);
						}
						
						if(jsonSubCategories != null) {
							for(int i=0;i<jsonSubCategories.length();i++){                        					      
							    JSONObject obj = jsonSubCategories.getJSONObject(i);
							    SubCategoryResultItem resultItem = new SubCategoryResultItem(new Category(obj));
							    resultList.add(resultItem);	           
							}
						}
						if(jsonServices != null){
							for(int i=0;i<jsonServices.length();i++){                        					      
							    JSONObject obj = jsonServices.getJSONObject(i);
							    ServiceResultItem resultItem = new ServiceResultItem(new Service(obj));
							    resultList.add(resultItem);	           
							}
						}
						
					}	
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
					
				HashMap<String, List<ResultItem>> map = new HashMap<String, List<ResultItem>>();
				map.put("itemList", resultList);
				Intent intent = new Intent(getApplicationContext(), ServicesCategoryListActivity.class);
				intent.putExtra("map", map);
				String itemTitle = ((Category)resultItem.getItem()).getCategoryName();
				intent.putExtra("itemName", itemTitle);
				
				startActivity(intent);
			}
			else if(response.getRequestID() == SERVICE_DETAIL_REQ) {
				Service service = (Service)resultItem.getItem();
				try {
					JSONObject jsonObj = new JSONObject((String)response.getData());
					if(jsonObj.has(JsonKey.RESULT) && !jsonObj.isNull(JsonKey.RESULT)){
						service.setDetails(jsonObj.getJSONObject(JsonKey.RESULT));
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}								
				Intent intent = new Intent(getApplicationContext(), ServiceDetailActivity.class);
	    		intent.putExtra("service", service);
	    		startActivity(intent);
				
			}
			
		}
		else {
			
		}
		
	}
	

}
