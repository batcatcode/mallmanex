package com.example.mallman1.customer;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mallman1.DatabaseHelper;

public class CustomerSignupViewModel extends AndroidViewModel {
    private final DatabaseHelper dbHelper;
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public CustomerSignupViewModel(Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void registerUser() {
        String usernameValue = username.getValue();
        String emailValue = email.getValue();
        String passwordValue = password.getValue();

        if (usernameValue == null || emailValue == null || passwordValue == null) {
            errorMessage.setValue("Please fill in all fields");
            registrationSuccess.setValue(false);
            return;
        }

        try {
            boolean success = dbHelper.registerUser(usernameValue, passwordValue, emailValue, true);
            if (success) {
                registrationSuccess.setValue(true);
            } else {
                errorMessage.setValue("Username already exists");
                registrationSuccess.setValue(false);
            }
        } catch (Exception e) {
            errorMessage.setValue("Registration failed: " + e.getMessage());
            registrationSuccess.setValue(false);
        }
    }
}