package com.example.group18_prototype1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//Requirements:
//⁃Splash Screen
//⁃Home Page
//⁃Search
//⁃Add restaurant page
//⁃About
//⁃Search Restaurant

    Button btnEdit;
    Button btnAbout;
    Button btnSearch;
    Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEdit = findViewById(R.id.btnEdit);
        btnAbout = findViewById(R.id.btnAbout);
        btnSearch = findViewById(R.id.btnSearch);
        btnList = findViewById(R.id.btnList);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAboutActivity();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchActivity();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });
    }

    private void startAboutActivity(){
        Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(aboutIntent);
    }

    private void startSearchActivity(){
        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(searchIntent);
    }

    private void startEditActivity(){
        Intent editIntent = new Intent(getApplicationContext(), RestaurantInfoActivity.class);
        startActivity(editIntent);
    }

    private void startListActivity(){
        Intent listIntent = new Intent(getApplicationContext(), ListRestaurantActivity.class);
        startActivity(listIntent);
    }
}