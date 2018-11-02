package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.R;
import com.lazydevs.tinylens.adapter.UserPhotosAdapter;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    RecyclerView recyclerViewExplore;
    DatabaseReference databaseReference;
    public ArrayList<ModelImage> images;
    UserPhotosAdapter userPhotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        recyclerViewExplore=(RecyclerView)findViewById(R.id.rv_explore);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        images = new ArrayList<>();

        recyclerViewExplore.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewExplore.addItemDecoration(new GridSpacingItemDecoration(3, 10,true));
        userPhotosAdapter = new UserPhotosAdapter(getApplicationContext(),images);
        recyclerViewExplore.setAdapter(userPhotosAdapter);

        Query query = FirebaseDatabase.getInstance().getReference().child("images");
        query.orderByKey().limitToFirst(100).addChildEventListener(new QueryForImages());

    }

    public void btnLogin(View view) {
        Intent intent=new Intent(ExploreActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void btnSignUp(View view) {
        Intent intent=new Intent(ExploreActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

    class QueryForImages implements ChildEventListener{

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
