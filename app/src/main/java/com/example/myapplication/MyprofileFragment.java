package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MyprofileFragment extends Fragment {

   private View bckbtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_myprofile, container, false);
        bckbtn = rootview.findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
            }
        });






        final FloatingActionButton fab = ((HomeActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((HomeActivity) getActivity()).hideFloatingActionButton();
        }

        return rootview;





    }




    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }






}