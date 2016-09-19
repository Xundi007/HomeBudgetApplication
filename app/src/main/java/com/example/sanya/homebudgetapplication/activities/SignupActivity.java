package com.example.sanya.homebudgetapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.HandlerUser;
import com.example.sanya.homebudgetapplication.model.User;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;

import java.util.List;


public class SignupActivity extends DyntellAppCompatActivity {
	private EditText userText;
	private EditText passwordText;
	private Button signupButton;
	private TextView loginLink;

	private String userName;
	private String userPassword;
	private User signUpUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		init();
		actions();
	}

	@Override
	public void init() {
		toolbar.setTitle(R.string.title_signup);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		userText = (EditText) findViewById(R.id.input_name);
		passwordText = (EditText) findViewById(R.id.input_password);
		signupButton = (Button) findViewById(R.id.btn_signup);
		loginLink = (TextView) findViewById(R.id.link_login);
	}

	@Override
	public void load() {

	}

	@Override
	public void actions() {
		signupButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signup();
			}
		});
		loginLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Finish the registration screen and return to the Login activity
				finish();
			}
		});
	}

	public void signup() {
		if (!validate()) {
			onSignupFailed();
			return;
		}

		signupButton.setEnabled(false);

		final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
				R.style.AppTheme_Dark_Dialog);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage(getString(R.string.signup_creating_account));
		progressDialog.show();

		userName = userText.getText().toString();
		userPassword = passwordText.getText().toString();

		new android.os.Handler().post(
				new Runnable() {
					public void run() {
						new HandlerUser(SignupActivity.this).addUser(userName, userPassword);
						signUpUser = new HandlerUser(SignupActivity.this).getUserByName(userName);
						onSignupSuccess();
						// onSignupFailed();
						progressDialog.dismiss();
					}
				});
	}


	public void onSignupSuccess() {
		signupButton.setEnabled(true);

		Intent intent = new Intent();
		intent.putExtra("USER_SU", signUpUser.getId());
		setResult(SignupActivity.RESULT_OK, intent);
		finish();
	}

	public void onSignupFailed() {
		Toast.makeText(getBaseContext(), R.string.signup_error, Toast.LENGTH_LONG).show();

		signupButton.setEnabled(true);
	}

	public boolean validate() {
		boolean valid = true;

		userName = userText.getText().toString();
		userPassword = passwordText.getText().toString();
		List<String> userNameList = new HandlerUser(SignupActivity.this).getAllUserNames();

		if (userName.isEmpty() || userName.length() < 4) {
			userText.setError(getString(R.string.signup_name_too_short));
			valid = false;
		} else if (userNameList.contains(userName)) {
			userText.setError(getString(R.string.signup_name_error));
			valid = false;
		} else {
			userText.setError(null);
		}

		if (userPassword.isEmpty() || userPassword.length() < 4 || userPassword.length() > 10) {
			passwordText.setError(getString(R.string.signup_password_error));
			valid = false;
		} else {
			passwordText.setError(null);
		}

		return valid;
	}
}