package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Add_SubscriberGroup extends AppCompatActivity {

    private View Backbtn , next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__subscriber_group);

        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberGroup.this,Channel_SettingActivity.class);
                startActivity(intent);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberGroup.this,Channel_SettingActivity.class);
                startActivity(intent);

            }
        });
    }
}