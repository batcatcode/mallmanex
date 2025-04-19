package com.example.mallman1.customer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mallman1.R;
import com.example.mallman1.MainActivity;
import com.example.mallman1.adapters.ProductAdapter;
import com.example.mallman1.databinding.ActivityCustomerHomepageBinding;
import com.example.mallman1.models.Product;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerHomepageActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ActivityCustomerHomepageBinding binding;
    private ProductAdapter productAdapter;
    private List<Product> allProducts;
    private String currentSearchQuery = "";
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Available Products");
        }

        setupRecyclerView();
        setupSearchView();
        setupCategoryChips();
        setupBackPressHandler();
        loadProducts();
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProducts.setAdapter(productAdapter);
    }

    private void setupSearchView() {
        binding.searchView.setQueryHint("Search products...");
        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                binding.searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Add close button listener
        binding.searchView.setOnCloseListener(() -> {
            filterProducts("");
            binding.searchView.clearFocus();
            return false;
        });
    }

    private void setupCategoryChips() {
        binding.categoryChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                currentCategory = chip.getText().toString();
                filterProducts(currentSearchQuery);
            }
        });
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmation();
            }
        });
    }

    private void loadProducts() {
        binding.progressBar.setVisibility(View.VISIBLE);
        
        // Create sample products
        allProducts = new ArrayList<>();
        allProducts.add(new Product("1", "Smartphone", 299.99, 50, "Latest model smartphone", "Electronics", "", "retailer1"));
        allProducts.add(new Product("2", "Laptop", 899.99, 30, "High-performance laptop", "Electronics", "", "retailer1"));
        allProducts.add(new Product("3", "Headphones", 99.99, 100, "Wireless headphones", "Electronics", "", "retailer2"));
        allProducts.add(new Product("4", "Smart Watch", 199.99, 40, "Fitness tracking smartwatch", "Electronics", "", "retailer2"));
        allProducts.add(new Product("5", "Tablet", 399.99, 25, "10-inch tablet", "Electronics", "", "retailer3"));
        // Add more sample products for other categories
        allProducts.add(new Product("6", "T-Shirt", 19.99, 200, "Cotton T-Shirt", "Clothing", "", "retailer4"));
        allProducts.add(new Product("7", "Jeans", 49.99, 100, "Blue Denim Jeans", "Clothing", "", "retailer4"));
        allProducts.add(new Product("8", "Java Programming", 29.99, 50, "Learn Java Programming", "Books", "", "retailer5"));
        allProducts.add(new Product("9", "Pizza", 9.99, 20, "Fresh Italian Pizza", "Food", "", "retailer6"));

        filterProducts("");
        binding.progressBar.setVisibility(View.GONE);
    }

    private void filterProducts(String query) {
        currentSearchQuery = query.toLowerCase().trim();
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> {
                    boolean matchesCategory = currentCategory.equals("All") || 
                            product.getCategory().equals(currentCategory);
                    boolean matchesQuery = currentSearchQuery.isEmpty() ||
                            product.getName().toLowerCase().contains(currentSearchQuery) ||
                            product.getDescription().toLowerCase().contains(currentSearchQuery);
                    return matchesCategory && matchesQuery;
                })
                .collect(Collectors.toList());

        productAdapter.updateProducts(filteredProducts);
        
        if (filteredProducts.isEmpty()) {
            binding.tvNoProducts.setVisibility(View.VISIBLE);
            binding.tvNoProducts.setText(query.isEmpty() ? 
                    "No products available" : 
                    "No products found matching '" + query + "'");
        } else {
            binding.tvNoProducts.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProductClick(Product product) {
        // TODO: Implement product details view
        Toast.makeText(this, "Selected: " + product.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_cart) {
            Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to cart
            return true;
        } else if (itemId == R.id.action_orders) {
            Toast.makeText(this, "Orders clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to orders
            return true;
        } else if (itemId == R.id.action_profile) {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to profile
            return true;
        } else if (itemId == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to settings
            return true;
        } else if (itemId == R.id.action_logout) {
            showLogoutConfirmation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes", (dialog, which) -> {
                navigateToMain();
            })
            .setNegativeButton("No", null)
            .show();
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes", (dialog, which) -> {
                finishAffinity();
            })
            .setNegativeButton("No", null)
            .show();
    }

    private void navigateToMain() {
        // Start MainActivity fresh
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Prevent any new intents from modifying this activity
        setIntent(intent);
    }
}
