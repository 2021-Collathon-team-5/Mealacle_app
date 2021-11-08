package com.naca.mealacle.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HomeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mealacle.db";
    private static final int DATABASE_VERSION = 1;
    private static final String RECENT_TABLE = "recent_table";
    private static final String ORDERED_TABLE = "ordered_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_MANUFACTURE = "manufacture";
    private static final String COLUMN_FOOD_URL = "food_url";
    private static final String COLUMN_DETAIL_URL = "detail_url";

    private Context context;
    public HomeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + RECENT_TABLE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRICE + " INTEGER, "
                + COLUMN_MANUFACTURE + " TEXT, "
                + COLUMN_FOOD_URL + " TEXT, "
                + COLUMN_DETAIL_URL + " TEXT); ";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + ORDERED_TABLE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRICE + " INTEGER, "
                + COLUMN_MANUFACTURE + " TEXT, "
                + COLUMN_FOOD_URL + " TEXT, "
                + COLUMN_DETAIL_URL + " TEXT); ";
        db.execSQL(query);
    }

    public void insertData(Food food, int checker){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_MANUFACTURE, food.getManufacture());
        values.put(COLUMN_FOOD_URL, food.getImages());
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_NAME, food.getName());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
