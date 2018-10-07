package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.lazydevs.tinylens.Helper;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText FirstName, LastName, Email, PassWord;
    Button Register;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Helper helper;

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
        firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString(), PassWord.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            Log.d("WhatIstheTask", "" + task);
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            validate();

                            sendToDatabase(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString());

                            if (user.isEmailVerified()) {
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Please verify your Email!!", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(RegistrationActivity.this, "Successful, Please verify your Email!!", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            Log.d("WhatIstheTask", "" + task);
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    void sendToDatabase(String firstName, String lastName, String email) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
        ModelUser user = new ModelUser(firstName, lastName, email);
        databaseReference.child(getUID()).setValue(user);
    }

    private String getUID() {
        FirebaseUser currentFirebaseUser = firebaseAuth.getCurrentUser();
        return currentFirebaseUser.getUid();
    }

    void validate() {
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

}
