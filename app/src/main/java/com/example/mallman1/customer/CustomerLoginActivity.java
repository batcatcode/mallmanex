package com.example.mallman1.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mallman1.MainActivity;
import com.example.mallman1.databinding.ActivityCustomerLoginBinding;
import com.example.mallman1.viewmodels.CustomerLoginViewModel;

public class CustomerLoginActivity extends AppCompatActivity {
    private CustomerLoginViewModel viewModel;
    private ActivityCustomerLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(CustomerLoginViewModel.class);
        viewModel.initialize(this);

        // Setup data binding
        binding = ActivityCustomerLoginBinding.inflate(getLayoutInflater());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        // Setup back press handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CustomerLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Observe login success
        viewModel.loginSuccess.observe(this, success -> {
            if (success) {
                try {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    
                    // Start CustomerHomepageActivity
                    Intent intent = new Intent(this, CustomerHomepageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    
                    // Close this activity
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Error navigating to homepage", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        // Observe error messages
        viewModel.errorMessage.observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}