package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lazydevs.tinylens.R;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }


    public void btn_back_settings(View view) {
        Intent intent=new Intent(UserProfileActivity.this,PostDetailViewActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
}
