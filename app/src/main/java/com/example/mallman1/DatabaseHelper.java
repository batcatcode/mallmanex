package com.example.mallman1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import com.example.mallman1.models.Product;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_IS_CUSTOMER = "is_customer";

    public static final String TABLE_STORES = "stores";
    public static final String COLUMN_STORE_NAME = "store_name";
    public static final String COLUMN_STORE_LOCATION = "store_location";
    public static final String COLUMN_RETAILER_USERNAME = "retailer_username";

    public DatabaseHelper(Context context) {
        super(context, "MallManDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_IS_CUSTOMER + " INTEGER)");
        
        // Create stores table
        db.execSQL("CREATE TABLE " + TABLE_STORES + "(" +
                COLUMN_RETAILER_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_STORE_NAME + " TEXT, " +
                COLUMN_STORE_LOCATION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_RETAILER_USERNAME + ") REFERENCES " + 
                TABLE_USERS + "(" + COLUMN_USERNAME + "))");
        
        // Create products table
        db.execSQL("CREATE TABLE products(id TEXT PRIMARY KEY, " +
                "name TEXT, description TEXT, price REAL, stock INTEGER, " +
                "imageUrl TEXT, retailerId TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    // User related methods
    public boolean isUserExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + 
                COLUMN_USERNAME + "=?", new String[]{username});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean registerUser(String username, String password, String email, boolean isCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_IS_CUSTOMER, isCustomer ? 1 : 0);

        try {
            db.insertOrThrow(TABLE_USERS, null, values);
            return true;
        } catch (SQLiteConstraintException e) {
            return false;
        }
    }

    public boolean registerRetailerStore(String username, String storeName, String storeLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RETAILER_USERNAME, username);
        values.put(COLUMN_STORE_NAME, storeName);
        values.put(COLUMN_STORE_LOCATION, storeLocation);

        try {
            db.insertOrThrow(TABLE_STORES, null, values);
            return true;
        } catch (SQLiteConstraintException e) {
            return false;
        }
    }

    public boolean authenticateUser(String email, String password, boolean isCustomer) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + 
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=? AND " + 
                COLUMN_IS_CUSTOMER + "=?",
                new String[]{email, password, isCustomer ? "1" : "0"});
        boolean authenticated = cursor.moveToFirst();
        cursor.close();
        return authenticated;
    }

    // Product related methods
    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", product.getId());
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());
        values.put("imageUrl", product.getImageUrl());
        values.put("retailerId", product.getRetailerId());
        
        return db.insert("products", null, values);
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());
        values.put("imageUrl", product.getImageUrl());
        values.put("retailerId", product.getRetailerId());
        
        return db.update("products", values, "id = ?", 
                new String[]{product.getId()}) > 0;
    }

    public List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE name LIKE ?", 
                new String[]{"%" + query + "%"});
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getString(0));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setStock(cursor.getInt(4));
                product.setImageUrl(cursor.getString(5));
                product.setRetailerId(cursor.getString(6));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public List<Product> getRetailerProducts(String retailerId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE retailerId = ?", 
                new String[]{retailerId});
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getString(0));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setStock(cursor.getInt(4));
                product.setImageUrl(cursor.getString(5));
                product.setRetailerId(cursor.getString(6));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }
}
