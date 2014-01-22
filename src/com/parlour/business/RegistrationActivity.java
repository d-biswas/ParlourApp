package com.parlour.business;

import com.parlour.business.database.Constants;
import com.parlour.business.database.DatabaseHandler;
import com.parlour.business.model.Member;
import com.parlour.business.utils.ActivityConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	
	private EditText txtFullName;
	private EditText txtEmail;
	private EditText txtPassword;
	private EditText txtPhoneNo;
	private Button btnRegister;
	private TextView linkLogin;
	int destination = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		
		txtFullName = (EditText) findViewById(R.id.reg_fullname);
		txtEmail = (EditText) findViewById(R.id.reg_email);
		txtPassword = (EditText) findViewById(R.id.reg_password);
		txtPhoneNo = (EditText) findViewById(R.id.reg_phone);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		linkLogin = (TextView) findViewById(R.id.link_to_login);
        
		final DatabaseHandler db = new DatabaseHandler(this);
		destination = getIntent().getIntExtra("destination", -1);
		
		// Listening to Login Screen link
		linkLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtFullName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPhoneNo.getWindowToken(), 0);
				
				try {
					String fullName = txtFullName.getText().toString();
					String email = txtEmail.getText().toString();
					String password = txtPassword.getText().toString();
					String phoneNumber = txtPhoneNo.getText().toString();
					
					if(email.length() > 0 || password.length() > 0) {
						Member member = new Member(email, password, fullName, phoneNumber);
						long i = db.addMember(member);
						if (i != -1){
							// if come from attempting booking online ? if so then go to the book online page
							// otherwise go to the Home page
							
							Editor editor = getSharedPreferences("file_name", MODE_PRIVATE).edit();
							editor.clear();
							editor.putString(Constants.KEY_EMAIL, member.getEmail());
							editor.putString(Constants.KEY_PASSWORD, member.getPassword());
							editor.commit();
							
							if(destination == ActivityConstant.BOOKING_ONLINE_ACTIVITY){
								Intent intent= getIntent();
								intent.putExtra("username", member.getEmail());
								setResult(RESULT_OK, intent);
								finish();
							}
							else {
								showHomePage("Registration successful! you are logged in for first time");
							}
							
						}
					}
					else{
						Toast.makeText(RegistrationActivity.this,
								"Username or Password is empty", 20)
								.show();
					}

				} catch (SQLException e) {
					showStatusPage("Ops! Failed to Register. Try again.");

				}
				
			}
		});
	}
	
	private void showStatusPage(String statusMsg) {
		Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
		intent.putExtra("statusMsg", statusMsg);
		startActivity(intent);
		
	}
	
	private void showHomePage(String msg) {
		Intent i = new Intent(this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		startActivity(i);
		Toast.makeText(getApplicationContext(), msg, 20).show();
		finish();
	}
}
