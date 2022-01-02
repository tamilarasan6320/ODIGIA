package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ListActivity extends AppCompatActivity {
    LinearLayout channel_click,group_click,lockesmsg,gifts_clicks;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        activity = ListActivity.this;
        channel_click = findViewById(R.id.channels_tv);
        group_click = findViewById(R.id.group_tv);
        lockesmsg = findViewById(R.id.lockedmsg);
        gifts_clicks = findViewById(R.id.gift_tv);



        channel_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChannelActivity.class);
                startActivity(intent);
            }
        });
        group_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GroupActivity.class);
                startActivity(intent);
            }
        });
        lockesmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LockedMessageActivity.class);
                startActivity(intent);
            }
        });
        gifts_clicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GiftActivity.class);
                startActivity(intent);
            }
        });

    }
}