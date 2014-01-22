package com.parlour.business;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ContactUsActivity extends BaseActivity implements OnClickListener{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected int getLayoutResourceId() {
		return R.layout.contact_layout;
	}
	@Override
	public void onClick(View v) {
		
	}

}
