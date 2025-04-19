package com.example.mallman1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RetailerLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}