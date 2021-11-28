package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class settingFragment extends Fragment {

    private View bckbtn;
    LinearLayout Signout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_setting, container, false);

        bckbtn = rootview.findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
            }
        });

        Signout =rootview.findViewById(R.id.signout);
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);

            }
        });








        final FloatingActionButton fab = ((HomeActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((HomeActivity) getActivity()).hideFloatingActionButton();
        }

        return rootview;



    }

    //hide app bar
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }



}