package com.example.mallman1.retailer;
import com.example.mallman1.customer.AddProductActivity;
import com.example.mallman1.customer.ViewOrdersActivity;
import com.example.mallman1.customer.UpdateInventoryActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mallman1.R;
import com.example.mallman1.MainActivity;

public class RetailerDashboardActivity extends AppCompatActivity {
    Button btnAddProduct, btnViewOrders, btnUpdateInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);

        btnAddProduct = findViewById(R.id.btn_add_product);
        btnViewOrders = findViewById(R.id.btn_view_orders);
        btnUpdateInventory = findViewById(R.id.btn_update_inventory);

        btnAddProduct.setOnClickListener(v -> startActivity(new Intent(this, AddProductActivity.class)));
        btnViewOrders.setOnClickListener(v -> startActivity(new Intent(this, ViewOrdersActivity.class)));
        btnUpdateInventory.setOnClickListener(v -> startActivity(new Intent(this, UpdateInventoryActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.retailer_dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.action_profile) {
            startActivity(new Intent(this, RetailerProfileActivity.class));
            return true;
        } 
        else if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, RetailerSettingsActivity.class));
            return true;
        }
        else if (itemId == R.id.action_help) {
            startActivity(new Intent(this, HelpSupportActivity.class));
            return true;
        }
        else if (itemId == R.id.action_logout) {
            // Clear any saved login state
            getSharedPreferences("login_prefs", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
            
            // Navigate to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
