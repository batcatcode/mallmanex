package com.example.mallman1;   // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mallman1.customer.CustomerLoginActivity;
import com.example.mallman1.customer.CustomerSignupActivity;
import com.example.mallman1.retailer.RetailerLoginActivity;
import com.example.mallman1.retailer.RetailerSignupActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the card views
        MaterialCardView customerCard = findViewById(R.id.card_customer);
        MaterialCardView retailerCard = findViewById(R.id.card_retailer);
        TextView signUpText = findViewById(R.id.tv_signup);

        // Set click listeners
        customerCard.setOnClickListener(v -> {
            // Navigate to Customer Login
            Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
            startActivity(intent);
        });

        retailerCard.setOnClickListener(v -> {
            // Navigate to Retailer Login
            Intent intent = new Intent(MainActivity.this, RetailerLoginActivity.class);
            startActivity(intent);
        });

        signUpText.setOnClickListener(v -> {
            // Show a dialog to select sign-up type
            showSignUpDialog();
        });
    }

    private void showSignUpDialog() {
        // Create a dialog to let the user choose between customer and retailer sign-up
        new android.app.AlertDialog.Builder(this)
            .setTitle("Choose Account Type")
            .setItems(new String[]{"Customer", "Retailer"}, (dialog, which) -> {
                Intent intent;
                if (which == 0) {
                    // Customer sign-up
                    intent = new Intent(MainActivity.this, CustomerSignupActivity.class);
                } else {
                    // Retailer sign-up
                    intent = new Intent(MainActivity.this, RetailerSignupActivity.class);
                }
                startActivity(intent);
            })
            .show();
    }

    @Override
    public void onBackPressed() {
        // Show exit confirmation
        new android.app.AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes", (dialog, which) -> {
                finishAffinity();
            })
            .setNegativeButton("No", null)
            .show();
    }
}
