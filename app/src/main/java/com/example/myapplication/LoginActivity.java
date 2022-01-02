package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageButton Next;
    private View Backbtn;
    EditText regPhoneNo;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        Next = findViewById(R.id.next_btn1);
        Backbtn = findViewById(R.id.topAppBar);
        regPhoneNo = findViewById(R.id.reg_phoneNo);


        Next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(isValid()){



                    //Call the next activity and pass phone no with it
//                    Intent intent = new Intent(getApplicationContext(), Enter_OtpActivity.class);
//                    intent.putExtra(Constant.Phonenumber, phoneNo);
//                    startActivity(intent);
                    CheckProfileExists();



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

    private void CheckProfileExists() {
        String phoneNo = regPhoneNo.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,phoneNo);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        new Session(activity).createUserLoginSession(jsonObject.getString(Constant.PROFILE),
                                jsonObject.getString(Constant.USER_ID),
                                jsonObject.getString(Constant.USER_NAME),
                                jsonObject.getString(Constant.FIRSTNAME),
                                jsonObject.getString(Constant.LASTNAME),
                                jsonObject.getString(Constant.MOBILE),
                                jsonObject.getString(Constant.DESCRIPTION),
                                jsonObject.getString(Constant.CITY),
                                jsonObject.getString(Constant.INSTAGRAM),
                                jsonObject.getString(Constant.TWITTER),
                                jsonObject.getString(Constant.FACEBOOK),
                                jsonObject.getString(Constant.LINKEDIN),
                                jsonObject.getString(Constant.YOUTUBE)
                        );

                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Intent intent = new Intent(activity, Profile_entryActivity.class);
                        intent.putExtra(Constant.MOBILE, phoneNo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, ""+phoneNo+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
            else {
                Toast.makeText(this, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
        }, LoginActivity.this, Constant.PROFILE_EXIST_URL, params,true);



    }

    private boolean isValid() {
        if(regPhoneNo.length() != 10){
            regPhoneNo.setError("Invalid Mobile number");
            return false;
        }

        return  true;
    }

}
