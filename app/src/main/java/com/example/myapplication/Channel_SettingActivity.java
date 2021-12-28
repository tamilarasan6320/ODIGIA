package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Channel_SettingActivity extends AppCompatActivity {
    private View Backbtn;
    private View next;
    RadioGroup radioGroup;
    String type = "";
    String channel_id = "";
    Session session;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__setting);
        activity = Channel_SettingActivity.this;
        session = new Session(activity);
        channel_id = getIntent().getStringExtra(Constant.CHANNEL_ID);
        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);
        radioGroup = findViewById(R.id.radiogroup);


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
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton
                        = (RadioButton)radioGroup
                        .findViewById(selectedId);
                type = radioButton.getTag().toString();
                updateChannel();


//                Intent intent = new Intent(Channel_SettingActivity.this,Add_SubscriberChannel.class);
//                startActivity(intent);


            }
        });
    }

    private void updateChannel()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.CHANNEL_ID,channel_id);
        params.put(Constant.TYPE,type);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(Channel_SettingActivity.this,Add_SubscriberChannel.class);
                        intent.putExtra(Constant.CHANNEL_ID,channel_id);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
            else {
                Toast.makeText(this, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
        }, Channel_SettingActivity.this, Constant.UPDATE_CHANNEL_URL, params,true);

    }
}