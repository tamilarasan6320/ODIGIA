package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Group_SettingsActivity extends AppCompatActivity {
    private View Backbtn,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__settings);

        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Group_SettingsActivity.this,New_groupActivity.class);
                startActivity(intent);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Group_SettingsActivity.this,Add_SubscriberGroup.class);
                startActivity(intent);

            }
        });

    }
}