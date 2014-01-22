package com.parlour.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parlour.business.R;
import com.parlour.business.database.Constants;
import com.parlour.business.database.DatabaseHandler;
import com.parlour.business.model.Category;
import com.parlour.business.model.JsonKey;
import com.parlour.business.model.Member;
import com.parlour.business.presentation.wrapper.CategoryResultItem;
import com.parlour.business.presentation.wrapper.ResultItem;
import com.parlour.business.utils.ActivityConstant;
import com.parlour.business.utils.DataCollector;
import com.parlour.business.utils.Response;
import com.parlour.business.utils.ServerConnector;
import com.parlour.business.utils.ServerResponseListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends BaseActivity implements OnClickListener, ServerResponseListener{
	
	private Handler handler;
	private ProgressDialog progDialog;
	
	private Button btnLogin;
	private Button btnLogout;
	private Button btnBookOnline;
	private Button btnSpecials;
	private Button btnLocation;
	private Button btnContactUs;
	private Button btnServicesCategory;
	
	private final int CATEGORIES_REQ = 7001;
	
	private TextView loginStatus;
	
	private DatabaseHandler db; 
	private SharedPreferences preferences;
	
	public void onResume(){
		super.onResume();
		doLoggedInStuff();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		handler = new Handler();
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		loginStatus = (TextView) findViewById(R.id.loginStatus);
		
		btnBookOnline = (Button) findViewById(R.id.btnBookOnline);
		btnServicesCategory = (Button) findViewById(R.id.btnServicesCategory);
		btnSpecials = (Button) findViewById(R.id.btnSpecials);
		btnLocation = (Button) findViewById(R.id.btnLocation);
		btnContactUs = (Button) findViewById(R.id.btnContactUs);
		
		db = new DatabaseHandler(this);
		preferences = getSharedPreferences("file_name", MODE_PRIVATE);
	
		doLoggedInStuff();
		
		btnServicesCategory.setOnClickListener(this);
		btnBookOnline.setOnClickListener(this);
		btnSpecials.setOnClickListener(this);
		btnLocation.setOnClickListener(this);
		btnContactUs.setOnClickListener(this);
	}
	
	private void doLoggedInStuff(){
		if(alreadyLoggedIn(db, preferences)){
			btnLogin.setVisibility(View.GONE);
			btnLogin.setOnClickListener(null);
			loginStatus.setText("You are logged in as " + preferences.getString(Constants.KEY_EMAIL, ""));
			
			btnLogout.setVisibility(View.VISIBLE);
			btnLogout.setOnClickListener(this);
		}
		else {
			btnLogin.setVisibility(View.VISIBLE);
			btnLogin.setOnClickListener(this);
			loginStatus.setText("");
			
			btnLogout.setVisibility(View.GONE);
			btnLogout.setOnClickListener(null);
		}
	}
	
	@Override
    protected int getLayoutResourceId() {
        return R.layout.home;
    }

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnLogin) {
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
		}
		if(v.getId() == R.id.btnLogout) {
			logout();
		}
		else if(v.getId() == R.id.btnBookOnline) {
			if(alreadyLoggedIn(db, preferences)) {
				startActivity(new Intent(getApplicationContext(), BookOnlineActivity.class));
			}
			else {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				intent.putExtra("destination", ActivityConstant.BOOKING_ONLINE_ACTIVITY);
				startActivity(intent);
			}
		}
		else if(v.getId() == R.id.btnServicesCategory) {
			
			handler.post(new Runnable() {						
				public void run() {
					progDialog = ProgressDialog.show(HomeActivity.this, "Please Wait.", "Fetching categories...", true, false);							
				}
			});
			String serverURL = "http://trinayanapps.com/webservice/category/getAllCategories";
			
			new ServerConnector(this, CATEGORIES_REQ, serverURL, ServerConnector.HTTP_GET_METHOD, null).connect();
			
			
		}
		else if(v.getId() == R.id.btnSpecials) {
			Intent intent = new Intent(getApplicationContext(), SpecialsActivity.class);
//			intent.putExtra("jsonString", (String)response.getData());
			startActivity(intent);
//			handler.post(new Runnable() {						
//				public void run() {
//					progDialog = ProgressDialog.show(HomeActivity.this, "Please Wait.", "Fetching...", true, false);							
//				}
//			});
			
//			String serverURL = "http://10.0.2.2:8080/webservice/category/getAllCategories";
//			String serverURL = "http://10.0.2.2:8080/webservice/product/saveOrUpdate";
//			Hashtable<String, String> params = new Hashtable<String, String>();
//			params.put("productName", "HairStyle");
//			params.put("productDescription", "Hair style description");
//			params.put("productNote", "Hair style note");
//			params.put("status", "true");
//			params.put("noOfTimeSlot", "3");
//			params.put("categoryID", "7");
//			new ServerConnector(this, serverURL, ServerConnector.HTTP_GET_METHOD, null).connect();
			
		}
		else if(v.getId() == R.id.btnLocation) {
			startActivity(new Intent(getApplicationContext(), LocationActivity.class));
		}
		else if(v.getId() == R.id.btnContactUs) {
			startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
		}
		
		
	}
	
	private boolean alreadyLoggedIn(DatabaseHandler db, SharedPreferences pref) {
		
		String email = pref.getString(Constants.KEY_EMAIL, "");
		String password = pref.getString(Constants.KEY_PASSWORD, "");
		if(!email.equals("") && !password.equals("")){
			try {
				Member member = db.getMember(email, password);
				if (member != null) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				return false;
			}
		}
		return false;
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
			if(response.getRequestID() == CATEGORIES_REQ) {
				
				
				List<ResultItem> resultList = new ArrayList<ResultItem>();
				try {
					JSONObject jsonResultObj = new JSONObject((String)response.getData());
					
					JSONArray json = null; 
					if(jsonResultObj.has(JsonKey.RESULT) && !jsonResultObj.isNull(JsonKey.RESULT)) {
						json = jsonResultObj.getJSONArray(JsonKey.RESULT);
					}
					if(json != null) {
						for(int i=0;i<json.length();i++){                        					      
						    JSONObject obj = json.getJSONObject(i);
						    CategoryResultItem resultItem = new CategoryResultItem(new Category(obj));
						    resultList.add(resultItem);	           
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
					
				HashMap<String, List<ResultItem>> map = new HashMap<String, List<ResultItem>>();
//				map.put("itemList", DataCollector.getAllCategoryItems());
				map.put("itemList", resultList);
				Intent intent = new Intent(getApplicationContext(), ServicesCategoryListActivity.class);
				intent.putExtra("map", map);
				startActivity(intent);
			}
			
		}
		else {
			
		}
		
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (progDialog != null)
		      return progDialog;
		    return null;
	}
	

}
