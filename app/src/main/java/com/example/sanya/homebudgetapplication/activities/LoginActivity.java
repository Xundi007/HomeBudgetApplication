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


public class LoginActivity extends DyntellAppCompatActivity {
	private static final int REQUEST_SIGNUP = 0;

	private EditText userText;
	private EditText passwordText;
	private Button loginButton;
	private TextView signupLink;

	private String userName;
	private String userPassword;
	private User loginUser;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();
		actions();
	}

	@Override
	public void init() {
		toolbar.setTitle(R.string.title_login);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		userText = (EditText) findViewById(R.id.input_username);
		passwordText = (EditText) findViewById(R.id.input_password);
		loginButton = (Button) findViewById(R.id.btn_login);
		signupLink = (TextView) findViewById(R.id.link_signup);
	}

	@Override
	public void load() {
	}

	@Override
	public void actions() {
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
		signupLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
				startActivityForResult(intent, REQUEST_SIGNUP);
			}
		});
	}

	public void login() {

		if (!validate()) {
			onLoginFailed();
			return;
		}

		loginButton.setEnabled(false);

		final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
				R.style.AppTheme_Dark_Dialog);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage(getString(R.string.login_auth));
		progressDialog.show();

		userName = userText.getText().toString();
		userPassword = passwordText.getText().toString();

		new android.os.Handler().post(
				new Runnable() {
					public void run() {
						loginUser = new HandlerUser(LoginActivity.this).getUserByName(userName);
						if (loginUser != null) {
							if (loginUser.getPassword().equals(userPassword))
								onLoginSuccess();
						} else {
							onLoginFailed();
						}
						progressDialog.dismiss();
					}
				});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_SIGNUP) {
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent();
				intent.putExtra("USER", data.getIntExtra("USER_SU", 0));
				setResult(LoginActivity.RESULT_OK, intent);
				this.finish();
			}
		}
	}

	@Override
	public void onBackPressed() {
		// Disable going back to the MainActivity
		moveTaskToBack(true);
	}

	public void onLoginSuccess() {
		loginButton.setEnabled(true);

		Intent intent = new Intent();
		intent.putExtra("USER", loginUser.getId());
		setResult(LoginActivity.RESULT_OK, intent);
		finish();
	}

	public void onLoginFailed() {
		Toast.makeText(getBaseContext(), R.string.login_fail, Toast.LENGTH_LONG).show();
		loginButton.setEnabled(true);
	}

	public boolean validate() {
		boolean valid = true;

		String user = userText.getText().toString();
		String password = passwordText.getText().toString();

		if (user.isEmpty() || user.length() < 4 || user.length() > 10) {
			userText.setError(getString(R.string.login_username_error));
			valid = false;
		} else {
			userText.setError(null);
		}

		if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
			passwordText.setError(getString(R.string.login_password_error));
			valid = false;
		} else {
			passwordText.setError(null);
		}

		return valid;
	}
}
