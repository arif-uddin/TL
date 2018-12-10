package com.lazydevs.tinylens.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lazydevs.tinylens.R;

public class EarnedActivity extends AppCompatActivity {

    TextView earnedBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earned);

        earnedBalance=(TextView)findViewById(R.id.tv_earned_balance);

    }

    public void btn_back_earned(View view) {
        onBackPressed();
        finish();
    }
}
