package com.example.mallman1.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mallman1.DatabaseHelper;

public class CustomerLoginViewModel extends ViewModel {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>(false);
    private DatabaseHelper databaseHelper;

    public void initialize(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void onLoginClicked(View view) {
        String emailStr = email.get();
        String passStr = password.get();

        // Reset error message
        errorMessage.setValue("");

        // Basic validation
        if (emailStr == null || emailStr.trim().isEmpty()) {
            errorMessage.setValue("Please enter email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            errorMessage.setValue("Please enter a valid email address");
            return;
        }

        if (passStr == null || passStr.trim().isEmpty()) {
            errorMessage.setValue("Please enter password");
            return;
        }

        // Show loading indicator
        isLoading.setValue(true);

        try {
            // Authenticate user
            boolean isAuthenticated = databaseHelper.authenticateUser(emailStr, passStr, true);
            isLoading.setValue(false);

            if (isAuthenticated) {
                loginSuccess.setValue(true);
            } else {
                errorMessage.setValue("Invalid email or password");
            }
        } catch (Exception e) {
            isLoading.setValue(false);
            errorMessage.setValue("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onSignupClicked(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, com.example.mallman1.customer.CustomerSignupActivity.class);
        context.startActivity(intent);
    }

    public void onForgotPasswordClicked(View view) {
        // TODO: Implement forgot password functionality
    }
}