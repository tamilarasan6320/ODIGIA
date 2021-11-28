package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View Search;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         fab = findViewById(R.id.floating_action_button);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Floating_ActionActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId())

        {
                case R.id.myprofile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new MyprofileFragment()
                    ).commit();


                    break;

                case R.id.invitefrd:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new InviteFragment()
                    ).commit();
                    break;

                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new settingFragment()
                    ).commit();
                    break;

                case R.id.list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new listFragment()
                    ).commit();
                    break;


                case R.id.earning:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new EarningFragment()
                    ).commit();
                    break;


            }



        drawer.closeDrawer(GravityCompat.START);


        return true;


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu_top,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_bar:
                Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public FloatingActionButton getFloatingActionButton() {

        return fab;
    }

    public void hideFloatingActionButton() {

        fab.hide();

    }

}