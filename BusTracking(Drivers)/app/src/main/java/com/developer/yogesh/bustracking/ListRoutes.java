package com.developer.yogesh.bustracking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListRoutes extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_routes);
        listView=(ListView) findViewById(R.id.listview);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("Routes List");
        }
        Routes routes=new Routes();
        routes.execute();
    }
    public class Routes extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            final ArrayList arrayList=new ArrayList();
            super.onPostExecute(s);
            JSONArray jsonArray;
            try {
                jsonArray =new JSONArray(s);
                String[] list=new String[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String value="Bus Number : "+jsonObject.getString("bus_number")+" \n Source : "+jsonObject.getString("source")+" \n Destination : " +jsonObject.getString("destination");
                    arrayList.add(i,jsonObject.getString("id"));
                    list[i]=value;
                }
                MyAdapter myAdapter=new MyAdapter(getApplicationContext(),list);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(ListRoutes.this,CalculateLocation.class).putExtra("id",arrayList.get(position).toString()));

                    }
                });
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jsonObject=new JSONObject();
            String result=null;
            try {
                jsonObject.put("action","get_routes");
                JSONParser jsonParser=new JSONParser(LoginActivity.url,"POST",jsonObject);
                result=jsonParser.makeReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
