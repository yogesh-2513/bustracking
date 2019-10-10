package com.developer.yogesh.bustracking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLocation extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    String bus_number;
    DrawingView drawingView;
    private double latitude,longtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingView = new DrawingView(this);
        setContentView(R.layout.activity_my_location);
        WebView webView=(WebView)findViewById(R.id.webview);
        webView.loadUrl("https://cdn.dribbble.com/users/174627/screenshots/4891881/dribbble_jumpingpin.gif");
        Intent intent=getIntent();
        bus_number=intent.getStringExtra("bus");
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            Log.e("Method","onCreate");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.longtitude=location.getLongitude();
        this.latitude=location.getLatitude();
        Log.e("Latitude : ",Double.toString(latitude));
        Log.e("Longtitude : ",Double.toString(longtitude));
        UpdateLocation updateLocation = new UpdateLocation();
        updateLocation.execute();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public class UpdateLocation extends AsyncTask<Void,Void,String> {

        String result=null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s.toString());
        }

        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("action","update_location");
                jsonObject.put("latitude",latitude);
                jsonObject.put("longtitude",longtitude);
                jsonObject.put("busID",bus_number);
                Log.e("BusNumber",bus_number);
                JSONParser jsonParser = new JSONParser(LoginActivity.url,"POST",jsonObject);
                result=jsonParser.makeReq();
                Log.e("Result ",result);
            } catch (Exception e){
                Log.e("Error",e.toString());
                e.printStackTrace();
            }
            return result;
        }
    }

    private class DrawingView extends View {

        boolean haveFace;
        Paint drawingPaint;

        public DrawingView(Context context) {
            super(context);
            drawingPaint = new Paint();
            drawingPaint.setColor(Color.RED);
            drawingPaint.setStyle(Paint.Style.STROKE);
            drawingPaint.setStrokeWidth(10);
        }

        public void setHaveFace(boolean h){
            haveFace = h;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub

            // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
            // UI coordinates range from (0, 0) to (width, height).

            int vWidth = getWidth();
            int vHeight = getHeight();

            int left = ((1+1000) * vWidth/2000)-200;
            int top  = ((1+1000) * vHeight/2000)-200;
            int right = ((1+1000) * vWidth/2000)-200;
            int bottom = ((1+1000) * vHeight/2000)-200;
            Log.i("Width:Height", String.valueOf(vWidth)+":"+String.valueOf(vHeight));
            //Log.i("Co-ordinates",String.valueOf(l)+String.valueOf(b)+String.valueOf(t)+String.valueOf(r));
//		     Toast.makeText(CameraActivity.this, String.valueOf(top), Toast.LENGTH_SHORT).show();
            canvas.drawRect(
                    left, top, right, bottom,
                    drawingPaint);
        }

    }

}
