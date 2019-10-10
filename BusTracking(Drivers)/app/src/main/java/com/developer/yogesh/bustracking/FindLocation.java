package com.developer.yogesh.bustracking;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class FindLocation extends Service implements LocationListener {
    private LocationManager locationManager;
    private double latitude,longtitude;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            Log.e("Method","onCreate");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        }
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.longtitude=location.getLongitude();
        this.latitude=location.getLatitude();
        Log.e("Latitude : ",Double.toString(latitude));
        Log.e("Longtitude : ",Double.toString(longtitude));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    public class UpdateLocation extends AsyncTask<Void,Void,String> {

        FindLocation findLocation;

        UpdateLocation(){
            findLocation=new FindLocation();
        }

        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("action","update_location");
                jsonObject.put("latitude",findLocation.latitude);
                jsonObject.put("longtitude",findLocation.longtitude);
                jsonObject.put("bus_number",new Intent().getStringExtra("bus_number"));
                JSONParser jsonParser = new JSONParser(LoginActivity.url,"POST",jsonObject);
                jsonParser.makeReq();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }


}
