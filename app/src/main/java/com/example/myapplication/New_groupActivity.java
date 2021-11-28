package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class New_groupActivity extends AppCompatActivity {

    private View Backbtn;
    private View next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);


        Backbtn = findViewById(R.id.topAppBar);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_groupActivity.this,Floating_ActionActivity.class);
                startActivity(intent);

            }
        });

        next = findViewById(R.id.favorite);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_groupActivity.this,Group_SettingsActivity.class);
                startActivity(intent);

            }
        });

    }
}