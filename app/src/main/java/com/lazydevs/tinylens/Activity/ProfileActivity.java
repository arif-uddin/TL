package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylens.R;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser==null)
        {
            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);

    }

    public void bt_home_profile(View view) {
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_search_profile(View view) {
        Intent intent = new Intent(ProfileActivity.this,SearchActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_upload_profile(View view) {
        Intent intent = new Intent(ProfileActivity.this,UploadImageActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_notification_pofile(View view) {
        Intent intent = new Intent(ProfileActivity.this,NotificationActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void btn_logout(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
