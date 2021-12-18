package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.myapplication.helper.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Enter_OtpActivity extends AppCompatActivity {

    ImageButton Next;
    String verificationCodeBySystem;
    TextView phonetxt;
    PinView pinFromUser;
    String phonetv;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__otp);
        Next = findViewById(R.id.next_btn2);
        pinFromUser = findViewById(R.id.pin_view);
        phonetxt = findViewById(R.id.phonetv);


        //String phonetv = preferences.getString(Constant.Phonenumber,"");
        phonetv = getIntent().getStringExtra(Constant.Phonenumber);
        phonetxt.setText(phonetv);




        sentVerficationCodeTouser(phonetv);




        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = pinFromUser.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    pinFromUser.setError("Wrong OTP...");
                    pinFromUser.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });


    }

    private void sentVerficationCodeTouser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    //Get the code in global variable
                    verificationCodeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {

                        pinFromUser.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Enter_OtpActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            };

    private  void  verifyCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Enter_OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            SharedPreferences preferences =getSharedPreferences(Constant.SHARED_PREF,MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString(Constant.Loggedin, Constant.True);
                            editor.putString(Constant.Phonenumber,phonetv);
                            editor.apply();

                            Toast.makeText(Enter_OtpActivity.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), Profile_entryActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Enter_OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

}