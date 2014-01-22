package com.parlour.business;

import com.parlour.business.database.Constants;
import com.parlour.business.database.DatabaseHandler;
import com.parlour.business.model.Member;
import com.parlour.business.utils.ActivityConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText txtUserName;
	EditText txtPassword;
	Button btnLogin;
	TextView linkRegister;
	int destination = -1;
	private static final int DESTINATION_BOOK_ONLINE = 111;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	    destination = getIntent().getIntExtra("destination", -1);

		txtUserName = (EditText) findViewById(R.id.lg_email);
		txtPassword = (EditText) findViewById(R.id.lg_password);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		linkRegister = (TextView) findViewById(R.id.link_to_register);

//		dbAdapter = new MemberAdapter(this);
		final DatabaseHandler db = new DatabaseHandler(this);
		Log.d("Insert: ", "Inserting ..");
		db.addMember(new Member("root@4sense.com", "123456", "Debangshu Biswas", "01670270270"));

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();
				if (username.length() > 0 && password.length() > 0) {
					try {
						Member member = db.getMember(username, password);
						if (member != null) {
							
							Editor editor = getSharedPreferences("file_name", MODE_PRIVATE).edit();
							editor.clear();
							editor.putString(Constants.KEY_EMAIL, member.getEmail());
							editor.putString(Constants.KEY_PASSWORD, member.getPassword());
							editor.commit();
							if(destination == ActivityConstant.BOOKING_ONLINE_ACTIVITY){
								Intent i = new Intent(getApplicationContext(), BookOnlineActivity.class);
								startActivity(i);
								finish();
							}
							else{
								showHomePage();
							}
						} else {
							showFailedPage("Invalid username or password");
						}

					} catch (Exception e) {
						showFailedPage("Some problem occurred");
					}
				} else {
					Toast.makeText(LoginActivity.this,
							"Username or Password is empty", 20)
							.show();
				}
			}
		});

		linkRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if(destination == ActivityConstant.BOOKING_ONLINE_ACTIVITY) {
					Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
					i.putExtra("destination", ActivityConstant.BOOKING_ONLINE_ACTIVITY);
					startActivityForResult(i, DESTINATION_BOOK_ONLINE);
				}
				else {
					// Switching to Registration screen
					Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
					startActivity(i);
				}
				

			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  if(resultCode==RESULT_OK && requestCode==DESTINATION_BOOK_ONLINE){
		  Intent i = new Intent(getApplicationContext(), BookOnlineActivity.class);
		  startActivity(i);
		  String username = data.getStringExtra("username");
		  Toast.makeText(getApplicationContext(), "Registration successful, logged in as" + username, 20).show();
		  finish();
	  }
	}
	
	private void showHomePage() {
		Intent i = new Intent(this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		startActivity(i);
		finish();
	}
	
	private void showFailedPage(String statusMsg) {
		Intent intent = new Intent(this, StatusActivity.class);
		intent.putExtra("statusMsg", statusMsg);
		startActivity(intent);
	}
	
}