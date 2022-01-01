package com.example.myapplication.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;

public class InvitefriendActivity extends AppCompatActivity {

    private View bckbtn;
    TextView inviteid;
    Session session;
    Activity activity;
    ImageView copytext;
    CardView shareid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefriend);
        activity = InvitefriendActivity.this;
        session = new Session(activity);
        bckbtn = findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HomeActivity.class);
                startActivity(intent);
            }
        });
        inviteid = findViewById(R.id.invite_id);
        inviteid.setText("odigia.club/"+ session.getData(Constant.USER_NAME));
        copytext =findViewById(R.id.link_id);
        copytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",inviteid.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, "link copied", Toast.LENGTH_SHORT).show();
            }
        });
        shareid =findViewById(R.id.share_id);
        shareid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });
    }
}