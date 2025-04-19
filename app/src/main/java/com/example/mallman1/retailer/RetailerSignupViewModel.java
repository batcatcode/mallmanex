package com.example.mallman1.retailer;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mallman1.DatabaseHelper;

public class RetailerSignupViewModel extends AndroidViewModel {
    private final DatabaseHelper dbHelper;
    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<String> confirmPassword = new MutableLiveData<>("");
    private final MutableLiveData<String> storeName = new MutableLiveData<>("");
    private final MutableLiveData<String> storeLocation = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public RetailerSignupViewModel(Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String value) {
        name.setValue(value);
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

    public MutableLiveData<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String value) {
        confirmPassword.setValue(value);
    }

    public MutableLiveData<String> getStoreName() {
        return storeName;
    }

    public void setStoreName(String value) {
        storeName.setValue(value);
    }

    public MutableLiveData<String> getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String value) {
        storeLocation.setValue(value);
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void onSignupClick() {
        String userName = name.getValue();
        String userEmail = email.getValue();
        String userPassword = password.getValue();
        String userConfirmPassword = confirmPassword.getValue();
        String userStoreName = storeName.getValue();
        String userStoreLocation = storeLocation.getValue();
        
        // Validate inputs
        if (userName == null || userName.isEmpty()) {
            errorMessage.setValue("Please enter your name");
            return;
        }

        if (userEmail == null || userEmail.isEmpty()) {
            errorMessage.setValue("Please enter your email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            errorMessage.setValue("Please enter a valid email address");
            return;
        }

        if (userPassword == null || userPassword.isEmpty()) {
            errorMessage.setValue("Please enter a password");
            return;
        }

        if (userPassword.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters");
            return;
        }

        if (userConfirmPassword == null || !userConfirmPassword.equals(userPassword)) {
            errorMessage.setValue("Passwords do not match");
            return;
        }

        if (userStoreName == null || userStoreName.isEmpty()) {
            errorMessage.setValue("Please enter your store name");
            return;
        }

        if (userStoreLocation == null || userStoreLocation.isEmpty()) {
            errorMessage.setValue("Please enter your store location");
            return;
        }

        try {
            // Register the retailer (isCustomer = false)
            boolean userRegistered = dbHelper.registerUser(userName, userPassword, userEmail, false);
            if (userRegistered) {
                // Register store information
                boolean storeRegistered = dbHelper.registerRetailerStore(userName, userStoreName, userStoreLocation);
                if (storeRegistered) {
                    registrationSuccess.setValue(true);
                } else {
                    // If store registration fails, we should rollback the user registration
                    // TODO: Implement proper transaction handling
                    errorMessage.setValue("Failed to register store information");
                    registrationSuccess.setValue(false);
                }
            } else {
                errorMessage.setValue("Username already exists");
                registrationSuccess.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue("Registration failed: " + e.getMessage());
            registrationSuccess.setValue(false);
        }
    }

    public void onLoginClick() {
        // This will be handled by the activity
    }
}