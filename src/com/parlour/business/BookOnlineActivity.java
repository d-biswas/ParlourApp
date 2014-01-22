package com.parlour.business;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BookOnlineActivity extends BaseActivity implements OnClickListener{
	private Button btnLogout;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		btnLogout = (Button) findViewById(R.id.btnLogout);
		// Listening to Logout button
		btnLogout.setOnClickListener(this);
	}
	
	@Override
    protected int getLayoutResourceId() {
        return R.layout.book_online_layout;
    }

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnLogout) {
			logout();
		}
		
	}

}
