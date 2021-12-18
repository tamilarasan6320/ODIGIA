package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.Constant;

public class LoginActivity extends AppCompatActivity {
    ImageButton Next;
    private View Backbtn;
    EditText regPhoneNo;
    SharedPreferences sharedPreferences;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        Next = findViewById(R.id.next_btn1);
        Backbtn = findViewById(R.id.topAppBar);
        regPhoneNo = findViewById(R.id.reg_phoneNo);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF,MODE_PRIVATE);

        Next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(isValid()){

                    String phoneNo = regPhoneNo.getText().toString().trim();

                    //Call the next activity and pass phone no with it
//                    Intent intent = new Intent(getApplicationContext(), Enter_OtpActivity.class);
//                    intent.putExtra(Constant.Phonenumber, phoneNo);
//                    startActivity(intent);

                    Intent intent = new Intent(activity, Profile_entryActivity.class);
                    intent.putExtra(Constant.MOBILE, phoneNo);
                    startActivity(intent);
                    finish();

                }



            }
        });
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                startActivity(intent);

            }
        });

    }

    private boolean isValid() {
        if(regPhoneNo.length() != 10){
            regPhoneNo.setError("Invalid Mobile number");
            return false;
        }

        return  true;
    }

}
