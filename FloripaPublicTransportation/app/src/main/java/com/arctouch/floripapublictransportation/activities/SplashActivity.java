package com.arctouch.floripapublictransportation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.arctouch.floripapublictransportation.R;

/**
 * Created by GabrielPacheco on 20/01/2015.
 */
public class SplashActivity extends AppCompatActivity implements Runnable{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, ListRouteActivity.class));
        finish();
    }
}
