/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 */
package com.mk.android.sharedprefexample;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import android.os.Bundle;
import android.util.Base64;

/**
 * 
 * @author Martin Kiessling
 * @version 1.0
 * 
 */
public class User {

	/** private User attributes */
	private String username;
	private String password;
	private String passwordSalt;

	/** empty Constructor */
	public User() {

	}

	/** Constructor to assign new User */
	public User(String name, String password) {
		this.username = name;
		this.passwordSalt = createSalt(password.length());
		this.password = getPasswordHash(password, this.passwordSalt);
	}

	/** Constructor to use already created User */
	public User(String name, String password, String passwordSalt) {
		this.username = name;
		this.password = password;
		this.passwordSalt = passwordSalt;
	}

	/** @return get Username */
	public String getName() {
		return this.username;
	}

	/** set Username */
	public void setName(String name) {
		this.username = name;
	}

	/** @return get Userpassword */
	public String getPassword() {
		return this.password;
	}

	/** set Userpassword */
	public void setPassword(String enteredPassword) {
		this.password = enteredPassword;
	}

	/** change Userpassword */
	public void changePassword(String enteredPassword) {
		this.passwordSalt = createSalt(enteredPassword.length());
		this.password = getPasswordHash(enteredPassword, this.passwordSalt);
	}

	/** @return get Userpasswordsalt */
	public String getPasswordSalt() {
		return this.passwordSalt;
	}

	/** set Userpasswordsalt */
	public void setPasswordSalt(String salt) {
		this.passwordSalt = salt;
	}

	/** @return get a SHA-256 hashed String from given password and salt */
	protected String getPasswordHash(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			password = password + salt;
			md.update(password.getBytes("UTF-8"));
			byte[] byteData = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** @return get true if given enteredPassword equals Userpassword */
	public boolean checkPassword(String enteredPassword) {
		String tempPW = getPasswordHash(enteredPassword, this.passwordSalt);
		if (tempPW.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}

	/** @return get passwordsalt from given size */
	protected String createSalt(int size) {
		SecureRandom ran;
		try {
			ran = SecureRandom.getInstance("SHA1PRNG");
			byte[] buff = new byte[size];
			ran.nextBytes(buff);
			return Base64.encodeToString(buff, Base64.DEFAULT);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return this.username;
	}

	/** @return Bundle from given User */
	public static Bundle userToBundle(User user) {
		Bundle b = new Bundle();
		b.putString(SessionManager.KEY_NAME, user.getName());
		b.putString(SessionManager.KEY_PASSWORD, user.getPassword());
		b.putString(SessionManager.KEY_PASSWORDSALT, user.getPasswordSalt());

		return b;
	}

	/** @return User from given Bundle */
	public static User bundleToUser(Bundle bundle) {
		User user = new User();
		user.setName(bundle.getString(SessionManager.KEY_NAME));
		user.setPassword(bundle.getString(SessionManager.KEY_PASSWORD));
		user.setPasswordSalt(bundle.getString(SessionManager.KEY_PASSWORDSALT));

		return user;
	}
}
