package com.parlour.business;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SpecialsActivity extends BaseActivity implements OnClickListener {
//	private String jsonString = "";
//	private TextView jsonTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		jsonString = getIntent().getStringExtra("jsonString");
//		jsonTextView = (TextView)findViewById(R.id.jsonText);
//		jsonTextView.setText(jsonString);
	}

	@Override
	public void onClick(View v) {

	}
	
	@Override
    protected int getLayoutResourceId() {
        return R.layout.specials_layout;
    }

}
