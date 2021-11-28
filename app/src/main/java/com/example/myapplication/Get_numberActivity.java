package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Get_numberActivity extends AppCompatActivity {
     ImageButton Next;
    private View Backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        Next = findViewById(R.id.next_btn1);
        Backbtn = findViewById(R.id.topAppBar);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Get_numberActivity.this,Enter_OtpActivity.class);
                startActivity(intent);

            }
        });
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Get_numberActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}