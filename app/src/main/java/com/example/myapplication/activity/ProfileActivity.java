package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateProfileActivity;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    private View bckbtn,update_profile;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = ProfileActivity.this;


        bckbtn =findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HomeActivity.class);
                startActivity(intent);
            }
        });
        update_profile =findViewById(R.id.update_profile);
        session = new Session(activity);

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        TextView name = findViewById(R.id.name);
        TextView usedid = findViewById(R.id.username);
        ImageView profile = findViewById(R.id.profile);
        name.setText(session.getData(Constant.FIRSTNAME));
        usedid.setText(session.getData(Constant.USER_NAME));
        Picasso.get()
                .load(session.getData(Constant.PROFILE))
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .transform(new RoundedCornersTransformation(20, 0))
                .into(profile);








    }



}