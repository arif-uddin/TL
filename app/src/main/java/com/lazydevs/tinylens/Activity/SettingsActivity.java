package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.lazydevs.tinylens.R;

public class SettingsActivity extends AppCompatActivity {

    TextView logOut,tvChangePhoto;
    LinearLayout llChangePhoto,linearLayoutChoosePhoto;
    ImageView imageViewProfilePhoto;
    ImageButton imageButtonChoosePhoto;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logOut=(TextView)findViewById(R.id.tv_log_out);
        tvChangePhoto=(TextView)findViewById(R.id.tv_profile_photo_change);
        llChangePhoto=(LinearLayout)findViewById(R.id.ll_profile_photo_change);
        linearLayoutChoosePhoto=(LinearLayout) findViewById(R.id.ll_upload_btn_layout);
        imageViewProfilePhoto=(ImageView)findViewById(R.id.iv_profile_photo_view);
        imageButtonChoosePhoto=(ImageButton)findViewById(R.id.ibtn_photo_chooser);
        firebaseAuth = FirebaseAuth.getInstance();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        tvChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llChangePhoto.getVisibility()==View.VISIBLE)
                {
                    llChangePhoto.setVisibility(LinearLayout.GONE);
                }
                else {
                    llChangePhoto.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });
    }

    public void btn_back_settings(View view) {
        Intent intent=new Intent(SettingsActivity.this,ProfileActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void btn_cancel_photo_change(View view) {
        llChangePhoto.setVisibility(LinearLayout.GONE);
    }
}
