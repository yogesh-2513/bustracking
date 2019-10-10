package com.developer.yogesh.bustracking;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CalculateLocation extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_location);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.e("Bus ID",id);
//        startService(new Intent(CalculateLocation.this, FindLocation.class).putExtra("bus_number",id));
        startActivity(new Intent(CalculateLocation.this,MyLocation.class).putExtra("bus",id));
        finish();
    }
}
