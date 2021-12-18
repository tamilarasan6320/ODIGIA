package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;

public class SplashscreenActivity extends AppCompatActivity {


    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        activity = SplashscreenActivity.this;
        session = new Session(activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getBoolean(Constant.IS_USER_LOGIN)){
                    Intent intent = new Intent(SplashscreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent mainIntent= new Intent(SplashscreenActivity.this, IntroActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        },1000);



    }
}