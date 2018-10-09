package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylens.R;

public class MainActivity extends AppCompatActivity {

    FirebaseApp firebaseApp;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser==null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void upload(View view) {
        Intent intent = new Intent(MainActivity.this,UploadImageActivity.class);
        startActivity(intent);
    }
}
