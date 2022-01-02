package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
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

public class Profile_entryActivity extends AppCompatActivity {

    private View next;
    public CircleImageView pick;
    EditText first_name , last_name,user_id;
    public final int reqWritePermission = 2;
    public static final int SELECT_FILE = 110;
    Uri imageUri,profileUri;
    String mobilenumber;
    String filePath = null;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_entry);
        activity = Profile_entryActivity.this;

        first_name = findViewById(R.id.firstname);
        last_name = findViewById(R.id.lastname);
        user_id = findViewById(R.id.userid);

        //next acivity
        next = findViewById(R.id.favorite);

        mobilenumber = getIntent().getStringExtra(Constant.MOBILE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeruser();

            }
        });


        pick=(CircleImageView)findViewById(R.id.image);
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SelectProfileImage();

            }
        });









    }


    private void Registeruser() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> fileParams = new HashMap<>();
        params.put(Constant.USER_NAME, user_id.getText().toString().trim());
        params.put(Constant.FIRSTNAME, first_name.getText().toString().trim());
        params.put(Constant.LASTNAME, last_name.getText().toString().trim());
        params.put(Constant.MOBILE, mobilenumber);
        fileParams.put(Constant.PROFILE, filePath);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        new Session(activity).createUserLoginSession(jsonObject.getString(Constant.PROFILE),
                                jsonObject.getString(Constant.USER_ID),
                                jsonObject.getString(Constant.USER_NAME),
                                jsonObject.getString(Constant.FIRSTNAME),
                                jsonObject.getString(Constant.LASTNAME),
                                jsonObject.getString(Constant.MOBILE),
                                jsonObject.getString(Constant.DESCRIPTION),
                                jsonObject.getString(Constant.CITY),
                                jsonObject.getString(Constant.INSTAGRAM),
                                jsonObject.getString(Constant.TWITTER),
                                jsonObject.getString(Constant.FACEBOOK),
                                jsonObject.getString(Constant.LINKEDIN),
                                jsonObject.getString(Constant.YOUTUBE)
                        );

                        Intent intent = new Intent(Profile_entryActivity.this,HomeActivity.class);
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
        }, Profile_entryActivity.this, Constant.REGISTER_URL, params,fileParams);



    }

    private void SelectProfileImage()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Profile_entryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqWritePermission);
            } else if (ContextCompat.checkSelfPermission(Profile_entryActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, reqWritePermission);
            } else {
                PickImage();
            }
        } else {
            PickImage();
        }

    }
    public void PickImage() {

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
                        .start(Profile_entryActivity.this);


                //KidImage.setImageURI(imageUri);

            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                filePath = result.getUriFilePath(Profile_entryActivity.this, true);
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


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }

        }
    }

}