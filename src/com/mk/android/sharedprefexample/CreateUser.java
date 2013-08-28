/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 */
package com.mk.android.sharedprefexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Martin Kiessling
 * @version 1.0
 * 
 */
public class CreateUser extends Activity {

	/** declare components and variables */
	private EditText un;
	private EditText pw;
	private EditText pwr;
	private Button btnExit;
	private Button btnCreate;

	private boolean userBool, pwBool = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createuser);

		/** initialize components in View */
		un = (EditText) findViewById(R.id.newUserName);
		pw = (EditText) findViewById(R.id.newPassword);
		pwr = (EditText) findViewById(R.id.newPasswordRepeat);
		btnExit = (Button) findViewById(R.id.btnexit);
		btnCreate = (Button) findViewById(R.id.btncreate);

		/** add TextWatchers to EditTexts */
		un.addTextChangedListener(userWatch);
		pw.addTextChangedListener(pwWatch);
		pwr.addTextChangedListener(pwWatch);

		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = un.getText().toString();
				String password = pw.getText().toString();
				String passwordRepeat = pwr.getText().toString();

				if (username.isEmpty() || username.length() < 3) {
					un.setError(getResources().getString(
							R.string.username_error));
					un.requestFocus();
					userBool = false;
				} else {
					userBool = true;
				}
				if (password.isEmpty() || password.length() < 4) {
					pw.setError(getResources().getString(
							R.string.password_error));
					pwBool = false;
				} else if (!password.equals(passwordRepeat)) {
					pwr.setError(getResources().getString(
							R.string.passwords_notequal));
					pwr.requestFocus();
					pwBool = false;
				} else {
					pwBool = true;
				}
				if (userBool && pwBool) {
					User newUser = new User(username, password);
					new SessionManager(getApplicationContext())
							.createLoginSession(newUser);
					Intent returnIntent = new Intent();
					setResult(RESULT_OK, returnIntent);
					finish();
				}
			}
		});

		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);
				finish();
			}
		});
	}

	/** TextWatcher for passwordFields */
	final TextWatcher pwWatch = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			pw.setError(null);
			pwr.setError(null);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	/** TextWatcher for userField */
	final TextWatcher userWatch = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			un.setError(null);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
}
