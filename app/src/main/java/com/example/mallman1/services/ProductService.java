package com.example.mallman1.services;

import android.content.Context;
import com.example.mallman1.models.Product;
import com.example.mallman1.DatabaseHelper;
import java.util.List;

public class ProductService {
    private DatabaseHelper dbHelper;
    private Context context;

    public ProductService(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addProduct(Product product, ProductCallback callback) {
        long id = dbHelper.addProduct(product);
        if (id != -1) {
            product.setId(String.valueOf(id));
            callback.onSuccess(product);
        } else {
            callback.onError("Failed to add product");
        }
    }

    public void updateProduct(Product product, ProductCallback callback) {
        if (dbHelper.updateProduct(product)) {
            callback.onSuccess(product);
        } else {
            callback.onError("Failed to update product");
        }
    }

    public void searchProducts(String query, ProductListCallback callback) {
        List<Product> products = dbHelper.searchProducts(query);
        callback.onSuccess(products);
    }

    public void getRetailerProducts(String retailerId, ProductListCallback callback) {
        List<Product> products = dbHelper.getRetailerProducts(retailerId);
        callback.onSuccess(products);
    }

    public interface ProductCallback {
        void onSuccess(Product product);
        void onError(String errorMessage);
    }

    public interface ProductListCallback {
        void onSuccess(List<Product> products);
        void onError(String errorMessage);
    }
}