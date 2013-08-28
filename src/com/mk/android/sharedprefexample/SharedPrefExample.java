/*  Copyright (C) 2013 by Martin Kie\u00dfling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 */
package com.mk.android.sharedprefexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Martin Kie\u00dfling
 * @version 1.0
 * 
 */
public class SharedPrefExample extends Activity {

	/** SessionManager */
	private SessionManager session;

	/** declare components */
	private TextView tvUser;
	private Button btnDelete;
	private Button btnLogout;

	/** requestCodes for ActivityResults */
	private static final int REQ_CREATE = 0;
	private static final int REQ_LOGIN = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersession_main);

		tvUser = (TextView) findViewById(R.id.tvuser);
		btnDelete = (Button) findViewById(R.id.btndeleteuser);
		btnLogout = (Button) findViewById(R.id.btnlogout);

		session = new SessionManager(getApplicationContext());

		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				session.logoutUser();
				init();
			}
		});

		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				session.deleteUser();
				init();
			}
		});

		init();
	}

	/**
	 * decide which activity to show
	 */
	private void init() {
		if (session.userExists()) {
			if (!session.isLoggedIn()) {
				Intent createLoginActivity = new Intent(this, Login.class);
				startActivityForResult(createLoginActivity, REQ_LOGIN);
			}
		} else {
			Intent createNewUserActivity = new Intent(this, CreateUser.class);
			startActivityForResult(createNewUserActivity, REQ_CREATE);
		}
	}

	/**
	 * what to do on ActivityResult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQ_CREATE) {

			if (resultCode == RESULT_OK) {
				User user = session.getUser();
				tvUser.setText("Benutzername: " + user.getName()
						+ "\nPasswort: " + user.getPassword()
						+ "\nPasswordSalt: " + user.getPasswordSalt());
				btnDelete.setEnabled(true);
				btnLogout.setEnabled(true);
			}
			if (resultCode == RESULT_CANCELED) {
				tvUser.setText(getResources().getString(R.string.nouser));
				btnDelete.setEnabled(false);
			}
		}

		if (requestCode == REQ_LOGIN) {

			if (resultCode == RESULT_OK) {
				User user = session.getUser();
				tvUser.setText("Benutzername: " + user.getName()
						+ "\nPasswort: " + user.getPassword()
						+ "\nPasswordSalt: " + user.getPasswordSalt());
				btnDelete.setEnabled(true);
				btnLogout.setEnabled(true);
			}
			if (resultCode == RESULT_CANCELED) {
				tvUser.setText(getResources().getString(R.string.nologin));
				btnDelete.setEnabled(true);
				btnLogout.setEnabled(false);
			}
		}
	}
}
