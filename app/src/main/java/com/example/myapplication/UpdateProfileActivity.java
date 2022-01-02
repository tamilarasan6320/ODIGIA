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
import android.widget.TextView;
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
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class UpdateProfileActivity extends AppCompatActivity {
    private View next;
    Uri imageUri,profileUri;
    public CircleImageView pick;
    EditText first_name , last_name,description,city,instagram,twitter,facebook,linkedin,youtube;
    String filePath = null;
    public final int reqWritePermission = 2;
    public static final int SELECT_FILE = 110;
    TextView userid;
    Session session;
    private View bckbtn;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        activity= UpdateProfileActivity.this;
        next = findViewById(R.id.favorite);
        first_name = findViewById(R.id.firstname);
        last_name = findViewById(R.id.lastname);
        description = findViewById(R.id.description);
        city = findViewById(R.id.city);
        instagram = findViewById(R.id.insta_id);
        twitter = findViewById(R.id.twitter_id);
        facebook = findViewById(R.id.fb_id);
        linkedin = findViewById(R.id.Linkedin_id);
        youtube = findViewById(R.id.youtube_id);
        session = new Session(activity);

        bckbtn = findViewById(R.id.topAppBar);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        userid = findViewById(R.id.userid);
        userid.setText(session.getData(Constant.USER_NAME));


        pick=(CircleImageView)findViewById(R.id.update_image);
        first_name.setText(session.getData(Constant.FIRSTNAME));
        last_name.setText(session.getData(Constant.LASTNAME));
        description.setText(session.getData(Constant.DESCRIPTION));
        city.setText(session.getData(Constant.CITY));
        facebook.setText(session.getData(Constant.FACEBOOK));
        twitter.setText(session.getData(Constant.TWITTER));
        linkedin.setText(session.getData(Constant.LINKEDIN));
        youtube.setText(session.getData(Constant.YOUTUBE));
        instagram.setText(session.getData(Constant.INSTAGRAM));


        Picasso.get()
                .load(session.getData(Constant.PROFILE))
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .into(pick);
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SelectProfileImage();

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updateuser();

            }
        });





    }

    private void Updateuser() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.ID, session.getData(Constant.ID));
        params.put(Constant.FIRSTNAME, first_name.getText().toString().trim());
        params.put(Constant.LASTNAME, last_name.getText().toString().trim());
        params.put(Constant.DESCRIPTION, description.getText().toString().trim());
        params.put(Constant.CITY, city.getText().toString().trim());
        params.put(Constant.INSTAGRAM, instagram.getText().toString().trim());
        params.put(Constant.TWITTER, twitter.getText().toString().trim());
        params.put(Constant.FACEBOOK, facebook.getText().toString().trim());
        params.put(Constant.LINKEDIN, linkedin.getText().toString().trim());
        params.put(Constant.YOUTUBE, youtube.getText().toString().trim());
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


                        Intent intent = new Intent(activity,HomeActivity.class);
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

        }, activity, Constant.UPDATE_URL, params,true);








    }


    private void SelectProfileImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(UpdateProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqWritePermission);
            } else if (ContextCompat.checkSelfPermission(UpdateProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
                        .start(UpdateProfileActivity.this);


                //KidImage.setImageURI(imageUri);

            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                filePath = result.getUriFilePath(UpdateProfileActivity.this, true);
                if (resultCode == RESULT_OK) {

                    assert result != null;

                    profileUri = result.getUriContent();
                    updateprofile();



                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }

        }
    }

    private void updateprofile() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> fileParams = new HashMap<>();
        params.put(Constant.ID, session.getData(Constant.ID));
        params.put(Constant.UPDATE_PROFILE, Constant.VAL);
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

                        Picasso.get()
                                .load(session.getData(Constant.PROFILE))
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .error(R.drawable.ic_profile_placeholder)
                                .transform(new RoundedCornersTransformation(20, 0))
                                .into(pick);
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
        }, activity, Constant.UPDATE_URL, params,fileParams);

    }
}