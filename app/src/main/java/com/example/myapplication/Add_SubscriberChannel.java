package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Add_SubscriberChannel extends AppCompatActivity {


    private View Backbtn, next;
    RecyclerView recyclerView;
    ArrayList<ContactModal> arrayList = new ArrayList<ContactModal>();
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__subscriber);


        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);
        recyclerView = findViewById(R.id.recycler_view);
        //cheack permission
        checkPermission();

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberChannel.this, Channel_SettingActivity.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberChannel.this, Channel_SettingActivity.class);
                startActivity(intent);
            }
        });


    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(Add_SubscriberChannel.this
               , Manifest.permission.READ_CONTACTS

        )
        != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(Add_SubscriberChannel.this

            ,new String[]{Manifest.permission.READ_CONTACTS},100
            );
        }else {
            getContantList();

        }
    }

    private void getContantList() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"ASC";

        Cursor cursor = getContentResolver().query(
                uri,null,null,null
        );


        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){

                String id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID

                ));

                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));

                Uri uriPhone= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        +" =? ";

                Cursor phoneCursor = getContentResolver().query(
                        uriPhone,null,selection
                        , new String[]{id}, null
                );

                if (phoneCursor.moveToNext()) {

                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));


                    ContactModal modal = new ContactModal();

                    modal.setName(name);
                    modal.setNumber(number);

                    arrayList.add(modal);

                    phoneCursor.close();

                }


                }

            cursor.close();

            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new MainAdapter(this,arrayList);

            recyclerView.setAdapter(adapter);




        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
        == PackageManager.PERMISSION_GRANTED){

            getContantList();

        }else {
            Toast.makeText(Add_SubscriberChannel.this,"Permission Denied",Toast.LENGTH_SHORT).show();

            checkPermission();
        }
    }
}









