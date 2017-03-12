package com.example.us285899.Simpleweatherapp.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.us285899.Simpleweatherapp.R;

public class MainWeatherActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mainactivity_screen);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_weatherlayout, new ScreenFragment())
                    .commit();}
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

}
