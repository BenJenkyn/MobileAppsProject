package com.example.group18_prototype1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "restaurant.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Restaurants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Restaurants.TABLE_NAME);
        onCreate(db);
    }

    public long insertRestaurant(String restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Restaurants.RESTAURANT_NAME, restaurant);
        values.put(Restaurants.RESTAURANT_ADDRESS, restaurant);
        values.put(Restaurants.PHONE_NUM, restaurant);
        values.put(Restaurants.RESTAURANT_TAGS, restaurant);
        values.put(Restaurants.RESTAURANT_DESCRIPTION, restaurant);


        long id = db.insert(Restaurants.RESTAURANT_NAME, null, values) +
        db.insert(Restaurants.RESTAURANT_ADDRESS, null, values) +
                db.insert(Restaurants.PHONE_NUM, null, values) +
                db.insert(Restaurants.RESTAURANT_TAGS, null, values) +
                db.insert(Restaurants.RESTAURANT_DESCRIPTION, null, values);



        db.close();
        return id;
    }

    public Restaurants getRestaurant(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Restaurants.RESTAURANT_NAME,
                new String[] {Restaurants.COLUMN_ID, Restaurants.RESTAURANT_NAME,
                        Restaurants.RESTAURANT_ADDRESS, Restaurants.PHONE_NUM,
                        Restaurants.RESTAURANT_TAGS, Restaurants.RESTAURANT_DESCRIPTION},
                Restaurants.COLUMN_ID+"=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if(cursor != null){
            cursor.moveToFirst();
            Restaurants restaurant = new Restaurants(
                    cursor.getInt(cursor.getColumnIndex(Restaurants.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_NAME)),
                    cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(Restaurants.PHONE_NUM)),
                    cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_TAGS)),
                    cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_DESCRIPTION))
            );

            cursor.close();
            return restaurant;
        }else{
            return null;
        }
    }

    public List<Restaurants> getAllRestaurants(){
        List<Restaurants> restaurants = new ArrayList<Restaurants>();
        String selectQuery = " SELECT * FROM " + Restaurants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Restaurants restaurant = new Restaurants(
                            cursor.getInt(cursor.getColumnIndex(Restaurants.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_NAME)),
                            cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_ADDRESS)),
                            cursor.getString(cursor.getColumnIndex(Restaurants.PHONE_NUM)),
                            cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_TAGS)),
                            cursor.getString(cursor.getColumnIndex(Restaurants.RESTAURANT_DESCRIPTION))
                    );
                    restaurants.add(restaurant);
                }while (cursor.moveToNext());
            }
        }
        db.close();
        return restaurants;
    }

    public int getRestaurantCount(){
        String selectQuery = "SELECT COUNT(*) C FROM "+Restaurants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToNext();
            int c = cursor.getInt(cursor.getColumnIndex("C"));
            return c;
        }
        db.close();
        return 0;
    }

    public int updateRestaurant(Restaurants restaurant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Restaurants.RESTAURANT_NAME, restaurant.getName());
        values.put(Restaurants.RESTAURANT_ADDRESS, restaurant.getAddress());
        values.put(Restaurants.PHONE_NUM, restaurant.getPhoneNum());
        values.put(Restaurants.RESTAURANT_TAGS, restaurant.getTags());
        values.put(Restaurants.RESTAURANT_DESCRIPTION, restaurant.getDescription());

        int count = db.update(Restaurants.TABLE_NAME, values,
                Restaurants.COLUMN_ID+"="+restaurant.getId(), null);
        db.close();
        return count;
    }

    public void deleteRestaurant(Restaurants restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Restaurants.TABLE_NAME,
                Restaurants.COLUMN_ID+"="+restaurant.getId(), null);
        db.close();
    }
}
