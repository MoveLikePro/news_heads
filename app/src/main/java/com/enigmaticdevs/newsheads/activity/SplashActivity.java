package com.enigmaticdevs.newsheads.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.enigmaticdevs.newsheads.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        sharedPreferences=this.getSharedPreferences("com.enigmaticdevs.newsheads",MODE_PRIVATE);
        if(sharedPreferences.getString("firstTime","yes").equals("yes")){
             i = new Intent(SplashActivity.this, CountryActivity.class);
        }
        else{
            i = new Intent(SplashActivity.this, MainActivity.class);
        }
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over

            startActivity(i);
            finish();
        }, 500);
    }
    }
