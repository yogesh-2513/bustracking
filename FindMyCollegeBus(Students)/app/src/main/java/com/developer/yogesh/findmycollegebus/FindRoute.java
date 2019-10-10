package com.developer.yogesh.findmycollegebus;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class FindRoute extends AppCompatActivity {
    public Double lat,lan;
    String routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        routeId=intent.getStringExtra("id");
        GetLatLan getLatLan=new GetLatLan();
        getLatLan.execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route);
    }

    public class GetLatLan extends AsyncTask<Void,Void,String> {
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
                    String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lan + "&travelmode=driving";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
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
