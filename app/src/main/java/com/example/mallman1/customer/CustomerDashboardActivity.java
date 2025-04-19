package com.example.mallman1.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mallman1.models.Product;
import com.example.mallman1.adapters.ProductAdapter;
import com.example.mallman1.databinding.ActivityCustomerDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomerDashboardActivity extends AppCompatActivity {
    private ActivityCustomerDashboardBinding binding;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        loadProducts();
    }

    private void setupRecyclerView() {
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, product -> {
            // Handle product click
            // TODO: Navigate to product detail screen
            Toast.makeText(this, "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        binding.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewProducts.setAdapter(productAdapter);
    }

    private void loadProducts() {
        // TODO: Replace with actual API call or database query
        // For now, adding sample data
        List<Product> sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product("1", "Laptop", 999.99, 10, "High-performance laptop", "Electronics", "", "retailer1"));
        sampleProducts.add(new Product("2", "Smartphone", 699.99, 15, "Latest model", "Electronics", "", "retailer1"));
        sampleProducts.add(new Product("3", "Headphones", 199.99, 20, "Wireless headphones", "Electronics", "", "retailer2"));

        productList.clear();
        productList.addAll(sampleProducts);
        productAdapter.notifyDataSetChanged();

        // Show/hide loading and empty states
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerViewProducts.setVisibility(View.VISIBLE);
        binding.textEmpty.setVisibility(productList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}