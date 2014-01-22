package com.parlour.business;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StatusActivity extends Activity {
	private String statusMsg = "Default";
	private TextView txtStatusMsg;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success);
		
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	        statusMsg = extras.getString("statusMsg") != null ? extras.getString("statusMsg") : "";
	    }
		txtStatusMsg = (TextView) findViewById(R.id.success_msg);
		txtStatusMsg.setText(statusMsg);
	}
	

}
