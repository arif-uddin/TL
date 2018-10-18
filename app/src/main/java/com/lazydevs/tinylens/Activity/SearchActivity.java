package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lazydevs.tinylens.R;

import java.util.MissingFormatArgumentException;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public void bt_home_search(View view) {
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_upload_search(View view) {
        Intent intent = new Intent(SearchActivity.this,UploadImageActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_notification_search(View view) {
        Intent intent = new Intent(SearchActivity.this,NotificationActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void bt_user_search(View view) {
        Intent intent = new Intent(SearchActivity.this,ProfileActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
}
