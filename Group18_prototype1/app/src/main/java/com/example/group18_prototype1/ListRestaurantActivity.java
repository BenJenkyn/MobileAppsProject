package com.example.group18_prototype1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Restaurants> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);

        list = new ArrayList<>();
        RecyclerView rv = findViewById(R.id.recycler_view);
        db = new DatabaseHelper(this);
        List<Restaurants> l = db.getAllRestaurants();
        if (l != null){
            list.addAll(l);
        }
        if (l.size()==0){
            db.insertRestaurant("Test Restaurant 1");
            db.insertRestaurant("Test Restaurant 2");
            db.insertRestaurant("Test Restaurant 3");
            db.insertRestaurant("Test Restaurant 4");
            db.insertRestaurant("Test Restaurant 5");
        }
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        RestaurantAdapter adapter = new RestaurantAdapter(this, list);
        rv.setAdapter(adapter);
    }
}
