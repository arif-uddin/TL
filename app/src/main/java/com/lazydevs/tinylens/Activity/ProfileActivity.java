package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import com.lazydevs.tinylens.adapter.ExplorePhotosAdapter;
import com.lazydevs.tinylens.adapter.SearchUserPhotoAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    ImageView profilePhoto;
    RecyclerView recyclerView_profile_photos;
    SearchUserPhotoAdapter searchUserPhotoAdapter;
    DatabaseReference databaseReference;
    public ArrayList<ModelImage> images;
    public ArrayList<ModelUser> users;
    ModelUser user;

    LinearLayout photoBarBackground,likedBarBackground;
    TextView photoBarText,likedBarText,currentUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        photoBarBackground=(LinearLayout)findViewById(R.id.ll_photos_background);
        likedBarBackground=(LinearLayout)findViewById(R.id.ll_liked_background);
        photoBarText=(TextView)findViewById(R.id.tv_photos_profile);
        likedBarText=(TextView)findViewById(R.id.tv_liked_profile);
        currentUserName=(TextView)findViewById(R.id.tv_user_name);
        profilePhoto=(ImageView)findViewById(R.id.iv_profile_photo);


        recyclerView_profile_photos = findViewById(R.id.recyclerView_profile_photos);
        images = new ArrayList<>();
        users = new ArrayList<>();


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
        recyclerView_profile_photos.setLayoutManager(staggeredGridLayoutManager);
        staggeredGridLayoutManager.generateDefaultLayoutParams();
        recyclerView_profile_photos.setHasFixedSize(true);
        searchUserPhotoAdapter = new SearchUserPhotoAdapter(getApplicationContext(),images,users);
        recyclerView_profile_photos.setAdapter(searchUserPhotoAdapter);

        if(firebaseUser==null)
        {
            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        Query query = FirebaseDatabase.getInstance().getReference().child("images");
        query.orderByChild("userID").equalTo(FirebaseAuth.getInstance().getUid()).limitToFirst(50).addChildEventListener(new QueryForImages());

        Query userInfoQuery = FirebaseDatabase.getInstance().getReference().child("users");
        userInfoQuery.orderByKey().equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new QueryForUserDetails());

        photoBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoBarBackground.setBackgroundColor(Color.parseColor("#3498db"));
                likedBarBackground.setBackgroundColor(Color.parseColor("#2980b9"));
            }
        });

        likedBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likedBarBackground.setBackgroundColor(Color.parseColor("#3498db"));
                photoBarBackground.setBackgroundColor(Color.parseColor("#2980b9"));
            }
        });


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


    public void btn_settings(View view) {
        Intent intent=new Intent(ProfileActivity.this,SettingsActivity.class);
        startActivity(intent);
    }


    class QueryForImages implements ChildEventListener
    {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelImage modelImage = dataSnapshot.getValue(ModelImage.class);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.child(modelImage.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser  modelUser= dataSnapshot.getValue(ModelUser.class);
                    searchUserPhotoAdapter.setValue(modelImage,modelUser);
                    searchUserPhotoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            searchUserPhotoAdapter.notifyDataSetChanged();
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

    class QueryForUserDetails implements ChildEventListener{

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
            currentUserName.setText(modelUser.getFirstName()+" "+modelUser.getLastName());

            Glide
                    .with(ProfileActivity.this)
                    .load(modelUser.getUserPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(profilePhoto);
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
