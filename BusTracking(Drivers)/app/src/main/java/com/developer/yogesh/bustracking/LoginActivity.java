package com.developer.yogesh.bustracking;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private EditText username,password;
    public final static String url="http://192.168.43.213:3000/bustracking/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        button=(Button) findViewById(R.id.login);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        if(actionBar != null){
            actionBar.setTitle("Driver Login");
        }
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                    validate();
                break;
        }
    }

    private void validate(){

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(getApplicationContext(),"Internet is required !!" ,Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        }else{
            final String uname=username.getText().toString();
            final String pword=password.getText().toString();

            if(uname.equals("")){
                username.setError("Username required !!");
            }else if(pword.equals("")){
                password.setError("Password required !!");
            }else{

                CheckCredentials checkCredentials=new CheckCredentials();
                checkCredentials.execute();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if(grantResults.length >0 ){
                    validate();
                }else{
                    Toast.makeText(getApplicationContext(),"Permission denied !!",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public class CheckCredentials extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;
            try {
                 jsonObject= new JSONObject(s);
                 int error=jsonObject.getInt("error");
                 String msg=jsonObject.getString("msg");

                 if(error == 0){
                     JSONArray jsonArray = jsonObject.getJSONArray("data");
                     String driverId=jsonArray.getString(0);
                     String driverName=jsonArray.getString(1);
                     String driverAge=jsonArray.getString(2);
                     Intent intent=new Intent(LoginActivity.this,InfoActivity.class);
                     intent.putExtra("driverId",driverId);
                     intent.putExtra("driverName",driverName);
                     intent.putExtra("driverAge",driverAge);
                     startActivity(intent);
                     finish();
                 }else{
                     Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                 }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            JSONObject jsonObject=new JSONObject();
            JSONParser jsonParser;
            String result=null;
            try {
                jsonObject.put("action","login");
                jsonObject.put("username",username.getText().toString());
                jsonObject.put("password",password.getText().toString());
                jsonParser=new JSONParser(LoginActivity.url,"POST",jsonObject);
                result=jsonParser.makeReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
