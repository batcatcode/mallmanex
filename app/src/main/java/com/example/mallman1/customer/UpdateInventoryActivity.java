package com.example.mallman1.customer;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mallman1.R;

public class UpdateInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_inventory);

        // Example placeholder content
        TextView tvMessage = findViewById(R.id.tvInventoryMsg);
        tvMessage.setText("This is where you can update inventory!");
    }
}
