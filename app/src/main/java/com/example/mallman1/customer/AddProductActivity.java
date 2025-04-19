package com.example.mallman1.customer;
import com.example.mallman1.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    EditText etProductName, etProductPrice, etProductDescription;
    Button btnSubmitProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductDescription = findViewById(R.id.etProductDescription);
        btnSubmitProduct = findViewById(R.id.btnSubmitProduct);

        btnSubmitProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString().trim();
            String price = etProductPrice.getText().toString().trim();
            String description = etProductDescription.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Save the product to database
                Toast.makeText(this, "Product added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
