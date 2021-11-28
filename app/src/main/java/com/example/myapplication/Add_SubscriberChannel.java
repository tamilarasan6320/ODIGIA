package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Add_SubscriberChannel extends AppCompatActivity {


    // creating variables for our array list, recycler view progress bar and adapter.
    private ArrayList<ContactsModal> contactsModalArrayList;
    private RecyclerView contactRV;
    private ContactRVAdapter contactRVAdapter;
    private ProgressBar loadingPB;


    private View Backbtn , next;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__subscriber);


        // on below line we are initializing our variables.
        contactsModalArrayList = new ArrayList<>();
        contactRV = findViewById(R.id.idRVContacts);
        FloatingActionButton addNewContactFAB = findViewById(R.id.idFABadd);
        loadingPB = findViewById(R.id.idPBLoading);

        // calling method to prepare our recycler view.
        prepareContactRV();

        // calling a method to request permissions.
        requestPermissions();

        Backbtn = findViewById(R.id.topAppBar);
        next = findViewById(R.id.favorite);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberChannel.this,Channel_SettingActivity.class);
                startActivity(intent);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_SubscriberChannel.this,Channel_SettingActivity.class);
                startActivity(intent);

            }
        });


        // adding on click listener for our fab.
        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on below line.
                Intent i = new Intent(Add_SubscriberChannel.this, CreateNewContactActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in this on create options menu we are calling
        // a menu inflater and inflating our menu file.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        // on below line we are getting our menu item as search view item
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        // on below line we are creating a variable for our search view.
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        // on below line we are setting on query text listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on query submit we are clearing the focus for our search view.
                searchView.clearFocus();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                // on changing the text in our search view we are calling
                // a filter method to filter our array list.
                filter(newText.toLowerCase());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String toLowerCase) {

        // in this method we are filtering our array list.
        // on below line we are creating a new filtered array list.
        ArrayList<ContactsModal> filteredlist = new ArrayList<>();
        // on below line we are running a loop for checking if the item is present in array list.
        for (ContactsModal item : contactsModalArrayList) {
            String text = null;
            if (item.getUserName().toLowerCase().contains(text.toLowerCase())) {
                // on below line we are adding item to our filtered array list.
                filteredlist.add(item);
            }
        }
        // on below line we are checking if the filtered list is empty or not.
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show();
        } else {
            // passing this filtered list to our adapter with filter list method.
            contactRVAdapter.filterList(filteredlist);
        }
    }


    private void requestPermissions() {

        // below line is use to request
        // permission in the current activity.
        Dexter.withActivity(this)
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.READ_CONTACTS,
                        // below is the list of permissions
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS, Manifest.permission.WRITE_CONTACTS)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            getContacts();
                            Toast.makeText(MainActivity.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently,
                            // we will show user a dialog message.
                            showSettingsDialog();
                        }
    }

    private void prepareContactRV() {


        // in this method we are preparing our recycler view with adapter.
        contactRVAdapter = new ContactRVAdapter(this, contactsModalArrayList);
        // on below line we are setting layout manager.
        contactRV.setLayoutManager(new LinearLayoutManager(this));
        // on below line we are setting adapter to our recycler view.
        contactRV.setAdapter(contactRVAdapter);
    }
}