package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Enter_OtpActivity extends AppCompatActivity {

    private View Backbtn;
    ImageButton Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__otp);
        Next = findViewById(R.id.next_btn2);

        Backbtn = findViewById(R.id.topAppBar);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Enter_OtpActivity.this,Profile_entryActivity.class);
                startActivity(intent);

            }
        });


        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Enter_OtpActivity.this,Get_numberActivity.class);
                startActivity(intent);

            }
        });

    }
}