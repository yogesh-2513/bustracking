package com.developer.yogesh.findmycollegebus;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;



public class GetRoute extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Double lat,lan;
    String routeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_route);

        Intent intent=getIntent();
        routeId=intent.getStringExtra("id");
        GetLatLan getLatLan=new GetLatLan();
        getLatLan.execute();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lan);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in bus location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public class GetLatLan extends AsyncTask<Void,Void,String>{
        String result;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                int error=jsonObject.getInt("error");
                if(error == 0){
                    lan=jsonObject.getDouble("lat");
                    lat=jsonObject.getDouble("lan");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("id",routeId);
                jsonObject.put("action","get_route");
                JSONParser jsonParser=new JSONParser("http://192.168.43.212:3000/bustracking/api.php","POST",jsonObject);
                result = jsonParser.makeReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
