package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

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
import com.lazydevs.tinylens.Model.ModelLike;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;
import com.lazydevs.tinylens.ShowImagesAdapter;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    ShowImagesAdapter showImagesAdapter;
    DatabaseReference databaseReference;
    public ArrayList<ModelImage> images;
    public ArrayList<ModelUser> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        images = new ArrayList<>();
        users = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5),true));
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        showImagesAdapter = new ShowImagesAdapter(getApplicationContext(),images,users,MainActivity.this);
        recyclerView.setAdapter(showImagesAdapter);

        if(firebaseUser==null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("images");

        query.orderByKey().limitToFirst(100).addChildEventListener(new QueryForImages());

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

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }


    class QueryForImages implements ChildEventListener
    {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final ModelImage modelImage = dataSnapshot.getValue(ModelImage.class);
            /*showImagesAdapter.setValue(modelImage,);
            showImagesAdapter.notifyDataSetChanged();*/
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("users/" + modelImage.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser  modelUser= dataSnapshot.getValue(ModelUser.class);
                    showImagesAdapter.setValue(modelImage,modelUser);
                    showImagesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            showImagesAdapter.notifyDataSetChanged();

            Query query2 = FirebaseDatabase.getInstance().getReference().child("likes");
            query2.orderByChild("imageKey").equalTo(modelImage.getmKey()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ModelLike modelLike = dataSnapshot.getValue(ModelLike.class);
                    modelImage.addLike();
                    if(modelLike.getUserID().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        modelImage.hasUserLiked=true;
                        modelImage.like_Key = dataSnapshot.getKey();
                    }
                    showImagesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    ModelLike modelLike = dataSnapshot.getValue(ModelLike.class);
                    modelImage.removeLike();
                    if(modelLike.getUserID().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        modelImage.hasUserLiked=false;
                        modelImage.like_Key = null;
                    }
                    showImagesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


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

    public void likeHelper(ModelImage image)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if(!image.hasUserLiked)
        {
            image.hasUserLiked=true;
            ModelLike modelLike = new ModelLike(firebaseUser.getUid(),image.getmKey());
            String key = databaseReference.child("likes").push().getKey();
            databaseReference.child("likes").child(key).setValue(modelLike);
            image.like_Key=key;
        }
        else
        {
            image.hasUserLiked=false;
            if(image.like_Key!=null)
            {
                databaseReference.child("likes").child(image.like_Key).removeValue();
            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}



