package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;
import com.lazydevs.tinylens.ShowImagesAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    ShowImagesAdapter showImagesAdapter;
    DatabaseReference databaseReference;
    public ArrayList<ModelImage> images;
            ModelUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        images = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_profile);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,1,true));
        showImagesAdapter = new ShowImagesAdapter(getApplicationContext(),images,user,ProfileActivity.this);
        recyclerView.setAdapter(showImagesAdapter);

        if(firebaseUser==null)
        {
            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        Query query = FirebaseDatabase.getInstance().getReference().child("images");
       // query.orderByChild().limitToFirst(50).addChildEventListener(new QueryForImages());
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
