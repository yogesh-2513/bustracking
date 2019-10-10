package com.developer.yogesh.bustracking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    Button btn;
    TextView driverID,name,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        btn=(Button)findViewById(R.id.proceed);
        driverID=(TextView) findViewById(R.id.driverID);
        name= (TextView) findViewById(R.id.driverName);
        age=(TextView) findViewById(R.id.driverAge);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Driver Info");
        }
        Intent intent=getIntent();
        driverID.setText(intent.getStringExtra("driverId"));
        name.setText(intent.getStringExtra("driverName"));
        age.setText(intent.getStringExtra("driverAge"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,ListRoutes.class));

            }
        });

    }
}
