package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MyprofileFragment extends Fragment {

   private View bckbtn,update_profile;
    Session session;

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
        update_profile =rootview.findViewById(R.id.update_profile);
        session = new Session(getActivity());

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        TextView name = rootview.findViewById(R.id.name);
        TextView usedid = rootview.findViewById(R.id.username);
        ImageView profile = rootview.findViewById(R.id.profile);
        name.setText(session.getData(Constant.FIRSTNAME));
        usedid.setText(session.getData(Constant.USER_NAME));
        Picasso.get()
                .load(session.getData(Constant.PROFILE))
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .transform(new RoundedCornersTransformation(20, 0))
                .into(profile);






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