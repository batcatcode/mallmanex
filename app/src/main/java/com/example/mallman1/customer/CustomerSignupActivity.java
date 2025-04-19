package com.example.mallman1.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.mallman1.R;
import com.example.mallman1.databinding.ActivityCustomerSignupBinding;

public class CustomerSignupActivity extends AppCompatActivity {
    private ActivityCustomerSignupBinding binding;
    private CustomerSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding using DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_signup);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(CustomerSignupViewModel.class);
        
        // Set the ViewModel for data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Set up click listeners
        setupClickListeners();

        // Observe registration result
        observeViewModel();
    }

    private void setupClickListeners() {
        binding.signupButton.setOnClickListener(v -> {
            String username = binding.usernameEditText.getText().toString().trim();
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString();
            String confirmPassword = binding.confirmPasswordEditText.getText().toString();

            if (username.isEmpty()) {
                binding.usernameEditText.setError("Username is required");
                return;
            }

            if (email.isEmpty()) {
                binding.emailEditText.setError("Email is required");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditText.setError("Please enter a valid email address");
                return;
            }

            if (password.isEmpty()) {
                binding.passwordEditText.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                binding.passwordEditText.setError("Password must be at least 6 characters");
                return;
            }

            if (!password.equals(confirmPassword)) {
                binding.confirmPasswordEditText.setError("Passwords do not match");
                return;
            }

            viewModel.setUsername(username);
            viewModel.setEmail(email);
            viewModel.setPassword(password);
            viewModel.registerUser();
        });

        binding.loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, CustomerLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void observeViewModel() {
        viewModel.getRegistrationSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}