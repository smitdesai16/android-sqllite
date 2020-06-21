package me.creatorguy.androidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main.db";
    private static final String TABLE_PRODUCTS_NAME = "products";
    private static final String TABLE_PRODUCTS_COLUMN_ID_NAME = "_id";
    private static final String TABLE_PRODUCTS_COLUMN_PRODUCT_NAME = "name";

    public ProductDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS_NAME + "( " +
                TABLE_PRODUCTS_COLUMN_ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_PRODUCTS_COLUMN_PRODUCT_NAME + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addProduct(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_PRODUCTS_COLUMN_PRODUCT_NAME, product.getName());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_PRODUCTS_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void removeProduct(String productName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_PRODUCTS_NAME + " WHERE " + TABLE_PRODUCTS_COLUMN_PRODUCT_NAME + "=\"" + productName + "\";");
        sqLiteDatabase.close();
    }
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS_NAME + " WHERE 1";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            products.add(new Product(
                    cursor.getInt(cursor.getColumnIndex(TABLE_PRODUCTS_COLUMN_ID_NAME)),
                    cursor.getString(cursor.getColumnIndex(TABLE_PRODUCTS_COLUMN_PRODUCT_NAME))
            ));
        }

        return products;
    }
}
