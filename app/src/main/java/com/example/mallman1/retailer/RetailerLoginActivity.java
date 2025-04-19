package com.example.mallman1.retailer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.mallman1.R;
import com.example.mallman1.databinding.ActivityRetailerLoginBinding;

public class RetailerLoginActivity extends AppCompatActivity {
    private ActivityRetailerLoginBinding binding;
    private RetailerLoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding using DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_login);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(RetailerLoginViewModel.class);
        
        // Set the ViewModel for data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Set up click listeners
        setupClickListeners();

        // Observe login result
        observeViewModel();
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString();

            // Clear any previous errors
            binding.emailLayout.setError(null);
            binding.passwordLayout.setError(null);

            // Validate email
            if (email.isEmpty()) {
                binding.emailLayout.setError("Please enter your email");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.setError("Please enter a valid email address");
                return;
            }

            // Validate password
            if (password.isEmpty()) {
                binding.passwordLayout.setError("Please enter your password");
                return;
            }

            viewModel.setEmail(email);
            viewModel.setPassword(password);
            viewModel.onLoginClick();
        });

        binding.tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, RetailerSignupActivity.class);
            startActivity(intent);
            finish();
        });

        binding.tvForgotPassword.setOnClickListener(v -> {
            // TODO: Implement forgot password functionality
            Toast.makeText(this, "Forgot password functionality coming soon", Toast.LENGTH_SHORT).show();
        });
    }

    private void observeViewModel() {
        viewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RetailerDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                // Show error in the appropriate input field
                if (errorMessage.contains("email")) {
                    binding.emailLayout.setError(errorMessage);
                } else if (errorMessage.contains("password")) {
                    binding.passwordLayout.setError(errorMessage);
                }
            }
        });
    }
}