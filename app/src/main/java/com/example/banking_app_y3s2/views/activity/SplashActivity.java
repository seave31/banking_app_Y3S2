package com.example.banking_app_y3s2.views.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", null);
        if(token != null){
            //if user is logged in, redirect to dashboard
            startActivity(new Intent(this, DashboardActivity.class));
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
