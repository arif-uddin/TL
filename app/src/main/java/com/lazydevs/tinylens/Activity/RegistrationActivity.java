package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.lazydevs.tinylens.helper.Helper;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;
import com.wang.avi.AVLoadingIndicatorView;

public class RegistrationActivity extends AppCompatActivity {

    EditText FirstName, LastName, Email, PassWord;
    ImageButton Register;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Helper helper;
    AVLoadingIndicatorView sign_up_indicator;
    CheckBox showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        helper = new Helper(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        FirstName = findViewById(R.id.et_first_name);
        LastName = findViewById(R.id.et_last_name);
        Email = findViewById(R.id.et_registration_email);
        PassWord = findViewById(R.id.et_registration_pass);
        Register = findViewById(R.id.bt_registration_register);
        sign_up_indicator=(AVLoadingIndicatorView)findViewById(R.id.sign_up_indicator);
        showPassword=(CheckBox)findViewById(R.id.cb_show_password_sign_up);



        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showPassword.isChecked()) {
                    // show password
                    PassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    // hide password
                    PassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    public void register(View view) {
        if(validate())
        {
            Register.setVisibility(ImageButton.GONE);
            sign_up_indicator.setVisibility(AVLoadingIndicatorView.VISIBLE);
            registerUser();
        }

    }

    boolean validate()
    {   if (!helper.isNameValid(FirstName.getText().toString()))// && helper.isPasswordValid(PassWord.getText().toString()))))
        {
            Toast.makeText(this, "First Name invalid", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!helper.isNameValid(LastName.getText().toString()))
        {
            Toast.makeText(this, "Last Name invalid", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!helper.isEmailValid(Email.getText().toString()))
        {
            Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!helper.isPasswordValid(PassWord.getText().toString()))
        {
            Toast.makeText(this, "Password invalid", Toast.LENGTH_SHORT).show();
            return false;
        }else
            {
                 return true;
            }
    }


    void registerUser()
        {
        firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString().trim(), PassWord.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            Log.d("WhatIstheTask", "" + task);
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            verification();

                            sendToDatabase(FirstName.getText().toString().trim(), LastName.getText().toString().trim(), Email.getText().toString().replaceAll(" ",""));

                            if (user.isEmailVerified()) {
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Please verify your Email!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, VerificationActivity.class);
                                startActivity(intent);
                            }
                            Toast.makeText(RegistrationActivity.this, "Successful, Please verify your Email!!", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            Log.d("WhatIstheTask", "" + task);
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Register.setVisibility(ImageButton.VISIBLE);
                            sign_up_indicator.setVisibility(AVLoadingIndicatorView.GONE);
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    void sendToDatabase(String firstName, String lastName, String email) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
        ModelUser user = new ModelUser(firstName, lastName, email,null,firebaseAuth.getCurrentUser().getUid());
        databaseReference.child(getUID()).setValue(user);
    }

    private String getUID() {
        FirebaseUser currentFirebaseUser = firebaseAuth.getCurrentUser();
        return currentFirebaseUser.getUid();
    }

    void verification() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            //Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegistrationActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void btn_back_signup(View view) {
        onBackPressed();
        finish();
    }
}
