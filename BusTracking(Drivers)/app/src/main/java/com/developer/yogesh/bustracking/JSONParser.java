package com.developer.yogesh.bustracking;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class JSONParser{

    private String url;
    private String method;
    HttpURLConnection httpURLConnection;
    JSONObject jsonObject;

    JSONParser(String url,String method,JSONObject jsonObject) throws JSONException {
        this.url=url;
        this.method=method;
        this.jsonObject=jsonObject;
    }

    private String encode(JSONObject jsonObject) throws JSONException, UnsupportedEncodingException {
        StringBuilder stringBuilder=new StringBuilder();
        Iterator <String> iterator = jsonObject.keys();
        boolean firstParam=true;
        while (iterator.hasNext()){
            String key=iterator.next();
            Object value=jsonObject.get(key);
            if(firstParam){
                firstParam = false;
            }else{
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(key,"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(value.toString(),"UTF-8"));
        }

        return  stringBuilder.toString();
    }

    public String makeReq() throws JSONException {
        String result=null;
        switch (this.method){
            case "POST":
                Log.e("Type ",this.method);
               result=makePostReq();
                break;
            case "GET":
                result=makeGetReq();
                break;
        }
        return result;

    }
    private String makeGetReq() throws JSONException {
        String json;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url=new URL(this.url);
            httpURLConnection=(HttpURLConnection)url.openConnection();

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( httpURLConnection.getInputStream()));

            while((json = bufferedReader.readLine()) != null) {
                stringBuilder.append(json + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }

    private String makePostReq() throws JSONException {
        String json;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url=new URL(this.url);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter =new BufferedWriter( new OutputStreamWriter( outputStream,"UTF-8" ));
            bufferedWriter.write(encode(this.jsonObject));
            Log.e("JSON Object",this.jsonObject.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( httpURLConnection.getInputStream() ));
            while((json = bufferedReader.readLine()) != null) {
                stringBuilder.append(json + "\n");
            }
        } catch (MalformedURLException e) {
            Log.e("Error ",e.toString());
            e.printStackTrace();
        }catch (IOException e) {
            Log.e("Error ",e.toString());
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }
}
