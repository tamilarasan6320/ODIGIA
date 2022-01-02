package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.example.myapplication.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_SubscriberGroup extends AppCompatActivity {

    private View Backbtn, next;
    RecyclerView recyclerView;
    ArrayList<ContactModal> arrayList = new ArrayList<ContactModal>();
    SubscriberAdapter adapter;
    Activity activity;
    Session session;
    public static ArrayList<User> users;
    public ArrayList<String> selecteduserslist;
    String group_id;;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__subscriber_group);
        session = new Session(Add_SubscriberGroup.this);

        activity = Add_SubscriberGroup.this;
        users = new ArrayList<>();
        selecteduserslist = new ArrayList<>();


        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);
        recyclerView = findViewById(R.id.recycler_view);
        group_id = getIntent().getStringExtra(Constant.GROUP_ID);
        //cheack permission
        checkPermission();

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Channel_SettingActivity.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSubscribers();
//                Intent intent = new Intent(Add_SubscriberChannel.this, Channel_SettingActivity.class);
//                startActivity(intent);
            }
        });

    }
    private void selectedSubscribers()
    {
        selecteduserslist.clear();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isIs_Selected()){
                selecteduserslist.add(users.get(i).getId());

            }

        }
        saveSubscriber();

    }

    private void saveSubscriber()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.SUBSCRIBER_ID, String.valueOf(selecteduserslist));
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        params.put(Constant.GROUP_ID,group_id);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Intent intent = new Intent(activity,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

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
        }, activity, Constant.ADD_SUBSCRIBER_GROUP_URL, params,true);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(activity
                , Manifest.permission.READ_CONTACTS

        )
                != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(activity

                    ,new String[]{Manifest.permission.READ_CONTACTS},100
            );
        }else {
            getContantList();

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
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

                    String inputnum = number.replace(" ", "");
                    UserList(inputnum,modal);
                    phoneCursor.close();

                }


            }

            cursor.close();

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SubscriberAdapter(this,users);

        recyclerView.setAdapter(adapter);




    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED){

            getContantList();

        }else {
            Toast.makeText(Add_SubscriberGroup.this,"Permission Denied",Toast.LENGTH_SHORT).show();

            checkPermission();
        }
    }
    private void UserList(String inputnum, ContactModal modal)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                User user = g.fromJson(jsonObject1.toString(), User.class);


                                if (inputnum.contains(user.getMobile())){
                                    users.add(user);
                                    //arrayList.add(modal);
                                }
                                ;
                            } else {
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.USER_LIST_URL, params, true);
    }
}