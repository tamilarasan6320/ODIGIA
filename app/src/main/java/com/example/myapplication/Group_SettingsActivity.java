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

public class Group_SettingsActivity extends AppCompatActivity {
    private View Backbtn,next;
    RadioGroup radioGroup;
    String type = "";
    String group_id = "";
    Session session;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__settings);

        activity = Group_SettingsActivity.this;
        session = new Session(activity);
        group_id = getIntent().getStringExtra(Constant.GROUP_ID);
        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);
        radioGroup = findViewById(R.id.radiogroup);
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
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton
                        = (RadioButton)radioGroup
                        .findViewById(selectedId);
                type = radioButton.getTag().toString();
                updateGroup();
//                Intent intent = new Intent(Group_SettingsActivity.this,Add_SubscriberGroup.class);
//                startActivity(intent);

            }
        });

    }

    private void updateGroup() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.GROUP_ID,group_id);
        params.put(Constant.TYPE,type);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(Group_SettingsActivity.this,Add_SubscriberGroup.class);
                        intent.putExtra(Constant.GROUP_ID,group_id);
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
        }, Group_SettingsActivity.this, Constant.UPDATE_GROUP_URL, params,true);

    }
}