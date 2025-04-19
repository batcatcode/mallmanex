package com.example.mallman1.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mallman1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_IS_CUSTOMER = "isCustomer";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_IS_CUSTOMER + " INTEGER)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to register user
    public boolean registerUser(String username, String password, String email, String phoneNumber, boolean isCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the user already exists
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();  // Close the cursor if the user exists
            return false;  // User already exists
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phoneNumber);
        values.put(COLUMN_IS_CUSTOMER, isCustomer ? 1 : 0);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;  // Return true if insertion was successful, false otherwise
    }

    // Method to validate user login
    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;  // Login successful
        }

        return false;  // Invalid username or password
    }

    // Method to get user details (optional)
    public String getUserEmail(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_EMAIL}, COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            cursor.close();
            return email;
        }

        return null;
    }

    public String getUserPhone(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_PHONE}, COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            cursor.close();
            return phone;
        }

        return null;
    }
}
