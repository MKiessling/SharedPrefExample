/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 */
package com.mk.android.sharedprefexample;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Martin Kiessling
 * @version 1.0
 * 
 */
public class SessionManager {

	/** SharedPreferences */
	SharedPreferences pref;

	/** Editor to modify SharedPreferences */
	Editor editor;

	/** ApplicationContext */
	Context context;

	/** mode for SharedPreferences */
	int PRIVATE_MODE = 0;

	/** name of SharedPreferences */
	public static final String PREF_NAME = "UsersamplePref";

	/** Keys for SharedPreferences */
	public static final String KEY_LOGIN = "IsLoggedIn";
	public static final String KEY_NAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_PASSWORDSALT = "passwordSalt";

	/**
	 * Constructor
	 */
	@SuppressLint("CommitPrefEdits")
	public SessionManager(Context context) {
		this.context = context;
		pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 */
	public void createLoginSession(User newUser) {
		editor.putBoolean(KEY_LOGIN, true);
		editor.putString(KEY_NAME, newUser.getName());
		editor.putString(KEY_PASSWORD, newUser.getPassword());
		editor.putString(KEY_PASSWORDSALT, newUser.getPasswordSalt());
		editor.commit();
	}

	/**
	 * User logout
	 */
	public void logoutUser() {
		editor.putBoolean(KEY_LOGIN, false);
		editor.commit();
	}

	/**
	 * User login
	 */
	public void loginUser() {
		editor.putBoolean(KEY_LOGIN, true);
		editor.commit();
	}

	/**
	 * check if user is logged in
	 */
	public boolean isLoggedIn() {
		return pref.getBoolean(KEY_LOGIN, false);
	}

	/**
	 * Check if there is a user in prefs
	 */
	public boolean userExists() {
		return pref.contains(KEY_NAME);
	}

	/**
	 * return User from prefs
	 */
	public User getUser() {
		HashMap<String, String> userDetails = getUserDetails();
		return new User(userDetails.get(KEY_NAME),
				userDetails.get(KEY_PASSWORD),
				userDetails.get(KEY_PASSWORDSALT));
	}

	/**
	 * get User data from prefs
	 */
	private HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
		user.put(KEY_PASSWORDSALT, pref.getString(KEY_PASSWORDSALT, null));
		return user;
	}

	/**
	 * delete User from prefs
	 */
	public void deleteUser() {
		editor.clear();
		editor.commit();
	}
}
