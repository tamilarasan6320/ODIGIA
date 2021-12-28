package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class New_channelActivity extends AppCompatActivity {

    private View Backbtn;
    public CircleImageView pick;
    public static final int SELECT_FILE = 110;
    public final int reqWritePermission = 2;
    private View next;
    String filePath = null;
    Uri imageUri,profileUri;
    EditText channelname,description;
    boolean profileupdated = false;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_channel);
        session = new Session(New_channelActivity.this);

        Backbtn = findViewById(R.id.topAppBar);
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_channelActivity.this,Floating_ActionActivity.class);
                startActivity(intent);

            }
        });

        next = findViewById(R.id.favorite);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()){
                    if(profileupdated){
//                        Intent intent = new Intent(New_channelActivity.this,Channel_SettingActivity.class);
//                        startActivity(intent);
//                        finish();
                        CreateChannel();

                    }
                    else {

                        Toast.makeText(New_channelActivity.this, "please upload Channel Image", Toast.LENGTH_SHORT).show();

                    }
                }



//                Intent intent = new Intent(New_channelActivity.this,Channel_SettingActivity.class);
//                startActivity(intent);

            }
        });

        channelname = findViewById(R.id.channel_name);
        description = findViewById(R.id.channel_description);

        pick=(CircleImageView)findViewById(R.id.image_set_channel);
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SelectProfileImage();

            }
        });



    }

    private void CreateChannel() {


        Map<String, String> params = new HashMap<>();
        Map<String, String> fileParams = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.CHANNELNAME, channelname.getText().toString().trim());
        params.put(Constant.CHANNELDESCRIPTION, description.getText().toString().trim());
        params.put(Constant.TYPE, Constant.PUBLIC);
        fileParams.put(Constant.CHANNELIMAGE, filePath);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Intent intent = new Intent(New_channelActivity.this,Channel_SettingActivity.class);
                        intent.putExtra(Constant.CHANNEL_ID,jsonObject.getString(Constant.CHANNEL_ID));
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
                    Toast.makeText(this, "Hi Jp"+session.getData(Constant.ID)+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }



            }
            else {
                Toast.makeText(this, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
        }, New_channelActivity.this, Constant.CREATECHANNEL_URL, params,fileParams);



    }

    private void SelectProfileImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(New_channelActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqWritePermission);
            } else if (ContextCompat.checkSelfPermission(New_channelActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, reqWritePermission);
            } else {
                PickImage();
            }
        } else {
            PickImage();
        }
    }

    private void PickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                imageUri = data.getData();
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setOutputCompressQuality(90)
                        .setRequestedSize(300, 300)
                        .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setAspectRatio(1, 1)
                        .start(New_channelActivity.this);


                //KidImage.setImageURI(imageUri);

            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                filePath = result.getUriFilePath(New_channelActivity.this, true);
                if (resultCode == RESULT_OK) {

                    assert result != null;

                    profileUri = result.getUriContent();
                    Picasso.get()
                            .load(profileUri)
                            .fit()
                            .centerInside()
                            .placeholder(R.drawable.ic_profile_placeholder)
                            .error(R.drawable.ic_profile_placeholder)
                            .into(pick);
                    profileupdated = true;


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }

        }
    }

    private boolean isValid() {
        if(channelname.getText().toString().equals("")){
            channelname.setError("Channel is Empty");
            channelname.requestFocus();
            return false;
        }
        if (description.getText().toString().equals("")){
            description.setError("Description must be needed ");
            description.requestFocus();
            return false;
        }

        return  true;
    }

}