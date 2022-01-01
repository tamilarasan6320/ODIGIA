package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.IntroActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateProfileActivity;

public class SettingActivity extends AppCompatActivity {
    private View bckbtn;
    LinearLayout Signout,profile_move;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        activity = SettingActivity.this;

        bckbtn = findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HomeActivity.class);
                startActivity(intent);
            }
        });

        Signout =findViewById(R.id.signout);
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, IntroActivity.class);
                startActivity(intent);

            }
        });

        profile_move =findViewById(R.id.profile_move);
        profile_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateProfileActivity.class);
                startActivity(intent);

            }
        });
    }
}