package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class InviteFragment extends Fragment {

    private View bckbtn;
    TextView inviteid;
    Session session;
    ImageView copytext;
    CardView shareid;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_invite, container, false);
        session = new Session(getActivity());
        bckbtn = rootview.findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
            }
        });
        inviteid = rootview.findViewById(R.id.invite_id);
        inviteid.setText("odigia.club/"+ session.getData(Constant.USER_NAME));
        copytext = rootview.findViewById(R.id.link_id);
        copytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",inviteid.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "link copied", Toast.LENGTH_SHORT).show();
            }
        });
        shareid =rootview.findViewById(R.id.share_id);
        shareid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });







        final FloatingActionButton fab = ((HomeActivity) getActivity()).getFloatingActionButton();
        if (fab != null) {
            ((HomeActivity) getActivity()).hideFloatingActionButton();
        }
        return rootview;    }
        //hide app bar
        @Override
        public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }


}