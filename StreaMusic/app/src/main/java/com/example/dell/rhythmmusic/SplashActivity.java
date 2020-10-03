package com.example.dell.rhythmmusic;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler h=new Handler();
        h.postDelayed(new Runnable( ) {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),MusicActivity.class));

            }
        },2000);

    }

}
