package com.example.mallman1.retailer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.mallman1.R;
import com.example.mallman1.databinding.ActivityRetailerSignupBinding;
import android.util.Patterns;

public class RetailerSignupActivity extends AppCompatActivity {
    private ActivityRetailerSignupBinding binding;
    private RetailerSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding using DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_signup);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(RetailerSignupViewModel.class);
        
        // Set the ViewModel for data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Set up click listeners
        setupClickListeners();

        // Observe registration result
        observeViewModel();

        // Set up text change listeners
        setupTextChangeListeners();
    }

    private void setupTextChangeListeners() {
        binding.etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateEmail();
            }
        });
    }

    private boolean validateEmail() {
        String email = binding.etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.emailLayout.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.setError("Please enter a valid email address");
            return false;
        } else {
            binding.emailLayout.setError(null);
            return true;
        }
    }

    private void setupClickListeners() {
        binding.btnSignup.setOnClickListener(v -> {
            // Clear previous errors
            binding.emailLayout.setError(null);

            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();
            String storeName = binding.etStoreName.getText().toString().trim();
            String storeLocation = binding.etStoreLocation.getText().toString().trim();

            // Validate email first
            if (!validateEmail()) {
                binding.etEmail.requestFocus();
                return;
            }

            viewModel.setName(name);
            viewModel.setEmail(email);
            viewModel.setPassword(password);
            viewModel.setConfirmPassword(confirmPassword);
            viewModel.setStoreName(storeName);
            viewModel.setStoreLocation(storeLocation);
            viewModel.onSignupClick();
        });

        binding.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, RetailerLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void observeViewModel() {
        viewModel.getRegistrationSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, RetailerLoginActivity.class);
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