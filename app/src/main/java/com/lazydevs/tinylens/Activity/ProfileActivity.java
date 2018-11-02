package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;
import com.lazydevs.tinylens.adapter.UserPhotosAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    RecyclerView recyclerView_profile_photos;
    UserPhotosAdapter userPhotosAdapter;
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
        recyclerView_profile_photos = findViewById(R.id.recyclerView_profile_photos);
        images = new ArrayList<>();


        recyclerView_profile_photos.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView_profile_photos.addItemDecoration(new GridSpacingItemDecoration(3, 10,true));
        userPhotosAdapter = new UserPhotosAdapter(getApplicationContext(),images);
        recyclerView_profile_photos.setAdapter(userPhotosAdapter);

        if(firebaseUser==null)
        {
            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        Query query = FirebaseDatabase.getInstance().getReference().child("images");
        query.orderByChild("userID").equalTo(FirebaseAuth.getInstance().getUid()).limitToFirst(50).addChildEventListener(new QueryForImages());
        //query.orderByKey().limitToFirst(50).addChildEventListener(new QueryForImages());
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


class QueryForImages implements ChildEventListener
    {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelImage modelImage = dataSnapshot.getValue(ModelImage.class);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("images");
            databaseReference.child(modelImage.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    userPhotosAdapter.setValue(modelImage);
                    userPhotosAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            userPhotosAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

}
