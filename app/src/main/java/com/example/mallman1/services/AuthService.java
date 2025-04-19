package com.example.mallman1.services;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.MutableLiveData;

public class AuthService {
    private static AuthService instance;
    private final SharedPreferences preferences;
    private final MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>(false);
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_USERNAME = "username";

    private AuthService(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Check if user is already logged in
        isAuthenticated.setValue(preferences.contains(KEY_USERNAME));
    }

    public static AuthService getInstance(Context context) {
        if (instance == null) {
            instance = new AuthService(context.getApplicationContext());
        }
        return instance;
    }

    public void login(String username, String password, AuthCallback callback) {
        // TODO: Add proper password hashing and validation
        if (isValidCredentials(username, password)) {
            preferences.edit().putString(KEY_USERNAME, username).apply();
            isAuthenticated.setValue(true);
            callback.onSuccess();
        } else {
            callback.onError("Invalid credentials");
        }
    }

    public void signup(String username, String password, String email, AuthCallback callback) {
        // TODO: Add proper password hashing and storage
        if (isValidSignupData(username, password, email)) {
            preferences.edit()
                    .putString(KEY_USERNAME, username)
                    .putString(username + "_password", password) // Not secure, just for demo
                    .putString(username + "_email", email)
                    .apply();
            isAuthenticated.setValue(true);
            callback.onSuccess();
        } else {
            callback.onError("Invalid signup data");
        }
    }

    public void logout() {
        preferences.edit().clear().apply();
        isAuthenticated.setValue(false);
    }

    public MutableLiveData<Boolean> getAuthState() {
        return isAuthenticated;
    }

    private boolean isValidCredentials(String username, String password) {
        // TODO: Implement proper credential validation
        String storedPassword = preferences.getString(username + "_password", null);
        return storedPassword != null && storedPassword.equals(password);
    }

    private boolean isValidSignupData(String username, String password, String email) {
        // Basic validation
        return username != null && !username.trim().isEmpty() &&
                password != null && password.length() >= 6 &&
                email != null && email.contains("@");
    }

    public interface AuthCallback {
        void onSuccess();
        void onError(String error);
    }
}