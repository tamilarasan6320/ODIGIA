package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Floating_ActionActivity extends AppCompatActivity {
    private View Backbtn;
    LinearLayout newchannel,newgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating__action);
        Backbtn = findViewById(R.id.topAppBar);
        newchannel = findViewById(R.id.newchannel);
        newgroup = findViewById(R.id.newgroup);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Floating_ActionActivity.this,HomeActivity.class);
                startActivity(intent);

            }
        });
        newchannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Floating_ActionActivity.this,New_channelActivity.class);
                startActivity(intent);

            }
        });

        newgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Floating_ActionActivity.this,New_groupActivity.class);
                startActivity(intent);

            }
        });

    }
}