package com.example.mallman1.customer;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mallman1.R;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvProductName, tvProductDescription, tvProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvProductPrice = findViewById(R.id.tvProductPrice);

        // Example data
        tvProductName.setText("Product Name");
        tvProductDescription.setText("Detailed product description.");
        tvProductPrice.setText("$100");
    }
}
