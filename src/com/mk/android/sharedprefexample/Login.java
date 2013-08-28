/*  Copyright (C) 2013 by Martin Kie\u00dfling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 */
package com.mk.android.sharedprefexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Martin Kie\u00dfling
 * @version 1.0
 * 
 */
public class Login extends Activity {

	/** SessionManager */
	private SessionManager session;

	/** declare components */
	private EditText pw;
	private Button btnCancel;
	private Button btnLogin;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		session = new SessionManager(getApplicationContext());

		// Variables for components in activity
		pw = (EditText) findViewById(R.id.password);
		btnCancel = (Button) findViewById(R.id.btncancel);
		btnLogin = (Button) findViewById(R.id.btnlogin);

		user = session.getUser();
		setTitle("Login - " + user.getName());
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = pw.getText().toString();
				if (user.checkPassword(password)) {
					pw.setText(null);
					session.loginUser();
					setResult(RESULT_OK);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.wrong_password),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
