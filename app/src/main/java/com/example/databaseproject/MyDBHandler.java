package com.example.databaseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_SKU = "SKU";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Used by the tests to work on a separate database file
    public MyDBHandler(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_PRODUCTNAME + " TEXT," +
                COLUMN_SKU + " INTEGER" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_SKU, product.getSku());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product findProduct(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + COLUMN_PRODUCTNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{productName});

        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTNAME)));
            product.setSku(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SKU)));
        }
        cursor.close();
        db.close();
        return product;
    }

    public boolean removeProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_PRODUCTS, COLUMN_PRODUCTNAME + " = ?", new String[]{productName});
        db.close();
        return rows > 0;
    }
}
