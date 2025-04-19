package com.example.mallman1.retailer;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mallman1.DatabaseHelper;

public class RetailerLoginViewModel extends AndroidViewModel {
    private final DatabaseHelper dbHelper;
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public RetailerLoginViewModel(Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email.setValue(value);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password.setValue(value);
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void onLoginClick() {
        String userEmail = email.getValue();
        String userPassword = password.getValue();

        // Validate inputs
        if (userEmail == null || userEmail.isEmpty()) {
            errorMessage.setValue("Please enter your email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            errorMessage.setValue("Please enter a valid email address");
            return;
        }

        if (userPassword == null || userPassword.isEmpty()) {
            errorMessage.setValue("Please enter your password");
            return;
        }

        try {
            // Authenticate retailer (isCustomer = false)
            boolean isAuthenticated = dbHelper.authenticateUser(userEmail, userPassword, false);
            if (isAuthenticated) {
                loginSuccess.setValue(true);
            } else {
                errorMessage.setValue("Invalid email or password");
                loginSuccess.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue("Login failed: " + e.getMessage());
            loginSuccess.setValue(false);
        }
    }

    public void onSignupClick() {
        // This will be handled by the activity
    }

    public void onForgotPasswordClick() {
        // This will be handled by the activity
    }
}