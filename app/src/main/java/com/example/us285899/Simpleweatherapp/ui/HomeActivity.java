package com.example.us285899.Simpleweatherapp.ui;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.us285899.Simpleweatherapp.R;
import com.example.us285899.Simpleweatherapp.utils.Constants;

public class HomeActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainWeatherActivity.class);
                HomeActivity.this.startActivity(intent);
                HomeActivity.this.finish();
            }
        }, Constants.SPLASH_DISPLAY_TIME);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
