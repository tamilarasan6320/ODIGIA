package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Channel_SettingActivity extends AppCompatActivity {
    private View Backbtn;
    private View next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__setting);
        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Channel_SettingActivity.this,New_channelActivity.class);
                startActivity(intent);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Channel_SettingActivity.this,Add_SubscriberChannel.class);
                startActivity(intent);


            }
        });
    }
}