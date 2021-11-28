package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Profile_entryActivity extends AppCompatActivity {

    private View next;
    public ImageView pick;


    public final int reqWritePermission = 2;
    public static final int SELECT_FILE = 110;
    Uri imageUri,profileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_entry);

        //next acivity
        next = findViewById(R.id.favorite);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Profile_entryActivity.this,HomeActivity.class);
               startActivity(intent);

            }
        });


        pick=(ImageView)findViewById(R.id.image);
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SelectProfileImage();

            }
        });









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
                if (resultCode == RESULT_OK) {
                    profileUri = result.getUri();
                    Picasso.get()
                            .load(profileUri)
                            .fit()
                            .centerInside()
                            .into(pick);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }

        }
    }

}