package com.example.apple.wireless_module_ad_hoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    Data getData;
    String TAG="MainActivity";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //ImageButton touchscreen = (ImageButton) findViewById(R.id.touch);
        View home=(LinearLayout)findViewById(R.id.LinearLayout_home);

        getData=((Data)getApplicationContext());
        getData.setRoute("123");
        getData.setFromID("11111");
        getData.setName("name");
        Log.d(TAG,"Finished setting values.");

        home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Set the original diviation of the fake location.
        String original="0.000";
        Data fAddress=((Data)getApplicationContext());
        fAddress.setFakeAddress(original);

    }



}
