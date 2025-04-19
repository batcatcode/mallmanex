package com.example.mallman1;
import com.example.mallman1.customer.CustomerLoginActivity;
import com.example.mallman1.customer.CustomerSignupActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnCustomerLogin, btnCustomerSignup, btnRetailerLogin, btnRetailerSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCustomerLogin = findViewById(R.id.btn_customer_login);
        btnCustomerSignup = findViewById(R.id.btn_customer_signup);
        btnRetailerLogin = findViewById(R.id.btn_retailer_login);
        btnRetailerSignup = findViewById(R.id.btn_retailer_signup);

        // Handling button clicks
        btnCustomerLogin.setOnClickListener(v -> {
            // Starting the CustomerLoginActivity when Customer Login is clicked
            startActivity(new Intent(LoginActivity.this, CustomerLoginActivity.class));
        });

        btnCustomerSignup.setOnClickListener(v -> {
            // Starting the CustomerSignupActivity when Customer Signup is clicked
            startActivity(new Intent(LoginActivity.this, CustomerSignupActivity.class));
        });

        btnRetailerLogin.setOnClickListener(v -> {
            // Starting the RetailerLoginActivity when Retailer Login is clicked
            startActivity(new Intent(LoginActivity.this, RetailerLoginActivity.class));
        });

        btnRetailerSignup.setOnClickListener(v -> {
            // Starting the RetailerSignupActivity when Retailer Signup is clicked
            startActivity(new Intent(LoginActivity.this, RetailerSignupActivity.class));
        });
    }
}
