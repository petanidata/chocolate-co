package com.example.nsjg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("statusLogin",MODE_PRIVATE);

                Intent i;

                if (sp.getString("username",null) == null){
                    i = new Intent(splash.this, LoginActivity.class);
                }else{
                    i = new Intent(splash.this, HomeActivity.class);
                }

                startActivity(i);
            }
        },3000);
    }
}
