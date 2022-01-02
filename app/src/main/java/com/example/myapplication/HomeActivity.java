package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.activity.EarnActivity;
import com.example.myapplication.activity.InvitefriendActivity;
import com.example.myapplication.activity.ListActivity;
import com.example.myapplication.activity.ProfileActivity;
import com.example.myapplication.activity.SettingActivity;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View Search;
    FloatingActionButton fab;
    Session session;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = HomeActivity.this;
        session = new Session(activity);
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
        View header = navigationView.getHeaderView(0);

        TextView Nametxt = header.findViewById(R.id.nametxt);
        CircleImageView Profile = header.findViewById(R.id.profile_icon);
        Nametxt.setText(session.getData(Constant.FIRSTNAME));
        Picasso.get()
                .load(session.getData(Constant.PROFILE))
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(Profile);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
        tabLayout.addTab(tabLayout.newTab().setText("LOCKED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId())

        {
                case R.id.myprofile:
                    Intent intent = new Intent(activity, ProfileActivity.class);
                    startActivity(intent);


                    break;

                case R.id.invitefrd:
                    Intent intent1 = new Intent(activity, InvitefriendActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.settings:
                    Intent intent2 = new Intent(activity, SettingActivity.class);
                    startActivity(intent2);

                    break;

                case R.id.list:
                    Intent intent3 = new Intent(activity, ListActivity.class);
                    startActivity(intent3);


                    break;


                case R.id.earning:
                    Intent intent4 = new Intent(activity, EarnActivity.class);
                    startActivity(intent4);
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




}