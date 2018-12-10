package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.lazydevs.tinylens.Model.ModelLike;
import com.lazydevs.tinylens.Model.ModelReport;
import com.lazydevs.tinylens.adapter.CommentsAdapter;
import com.lazydevs.tinylens.Model.ModelComment;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

import java.security.PublicKey;
import java.util.ArrayList;

public class PostDetailViewActivity extends AppCompatActivity {


     TextView title,user_name,description,category,deviceModel,tvReport;
     ImageView imageView;

     //for comment
     RecyclerView recyclerView_comment;
     ArrayList<ModelComment> comments;
     ArrayList<ModelUser> users;
     ImageButton postCommennt,likeBtn;
     EditText commentString;
     CommentsAdapter commentsAdapter;

     TextView commenterName,likeCounter;
     ImageView commenterPhoto;

     ModelImage modelImage;
     private String imageUrl;
     private String UserId;

     private DatabaseReference mDatabaseRef;
     private FirebaseAuth firebaseAuth;
     private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        title= findViewById(R.id.title);
        description= findViewById(R.id.description);
        imageView= findViewById(R.id.im_images);
        user_name= findViewById(R.id.tv_name);
        category= findViewById(R.id.category);
        deviceModel=findViewById(R.id.tv_device_model);
        tvReport=findViewById(R.id.tv_report);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("comments");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        modelImage= new ModelImage();

        users = new ArrayList<>();
        comments = new ArrayList<>();

        //for comment
        commentString=(EditText) findViewById(R.id.et_new_comment);
        postCommennt=(ImageButton)findViewById(R.id.btn_post_comment);
        recyclerView_comment = findViewById(R.id.recyclerView_comment);

        likeCounter=(TextView)findViewById(R.id.tv_like_counter_post);
        likeBtn=(ImageButton)findViewById(R.id.btn_like_post);
        commenterName = findViewById(R.id.tv_commenter_username);
        commenterPhoto = findViewById(R.id.iv_commenter_photo);


        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView_comment.setLayoutManager(linearVertical);
        commentsAdapter = new CommentsAdapter(getApplicationContext(),comments,users);
        recyclerView_comment.setAdapter(commentsAdapter);

        imageUrl=getIntent().getExtras().getString("imageThumb");
        UserId=getIntent().getExtras().getString("userId");
        //details view of post
        Glide
                .with(getApplicationContext())
                .load(imageUrl)
                .into(imageView);

        user_name.setText("By"+" "+getIntent().getExtras().getString("user_name"));
        title.setText(getIntent().getExtras().getString("title"));
        description.setText(getIntent().getExtras().getString("description"));
        category.setText("Category: "+getIntent().getExtras().getString("category"));
        deviceModel.setText("Device: "+getIntent().getExtras().getString("deviceModel"));

        final String mKey = getIntent().getExtras().getString("image_key");

        Query query = FirebaseDatabase.getInstance().getReference().child("comments");
        query.orderByChild("imageKey").equalTo(mKey).addChildEventListener(new QueryForComments());

        likeCounter.setText(getIntent().getExtras().getString("like_counter"));

        postCommennt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                if(!commentString.getText().toString().equals(""))
                {
                    ModelComment modelComment = new ModelComment(firebaseUser.getUid(),mKey,commentString.getText().toString());
                    String key = databaseReference.child("comments").push().getKey();
                    databaseReference.child("comments").child(key).setValue(modelComment);
                    commentString.setText("");
                    Toast.makeText(PostDetailViewActivity.this, "commented", Toast.LENGTH_SHORT).show();
                    commentString.setHint("Comment here");
                }

            }
        });


        Query query2 = FirebaseDatabase.getInstance().getReference().child("likes");
        query2.orderByChild("imageKey").equalTo(mKey).addChildEventListener(new QueryForLikes());

        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                ModelReport modelReport=new ModelReport(imageUrl,firebaseAuth.getCurrentUser().getUid(),UserId);
                databaseReference.child("reports").push().setValue(modelReport);
                Toast.makeText(PostDetailViewActivity.this, "Reported Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btn_back_post_detail(View view) {

        onBackPressed();
        finish();
    }

    public void btn_cart_post_detail(View view) {

        Intent intent=new Intent(PostDetailViewActivity.this,OrderActivity.class);
        intent.putExtra("image",getIntent().getExtras().getString("image"));
        intent.putExtra("user_name",getIntent().getExtras().getString("user_name"));
        intent.putExtra("userId",getIntent().getExtras().getString("userId"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void btn_like(View view) {
//        ModelImage Image = (ModelImage) getIntent().getExtras().getSerializableExtra("btn_like");
//        Log.d("imageLike",""+Image.getmKey());
    }


    class QueryForComments implements ChildEventListener
    {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelComment modelComment = dataSnapshot.getValue(ModelComment.class);
            Log.d("Comment",""+modelComment.getComment());
            Log.d("Key",""+modelComment.getComment());


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.child(modelComment.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser  modelUser = dataSnapshot.getValue(ModelUser.class);
                    try
                    {
                        Log.d("User",""+modelUser.getFirstName());
                    }
                    catch(NullPointerException E)
                    {

                    }
                    commentsAdapter.setValues(modelComment,modelUser);
                    commentsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            commentsAdapter.notifyDataSetChanged();
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

    private class QueryForLikes implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            ModelLike modelLike = dataSnapshot.getValue(ModelLike.class);
            modelImage.addLike();
            if(modelLike.getUserID().equals(FirebaseAuth.getInstance().getUid()))
            {
                modelImage.hasUserLiked=true;
                modelImage.like_Key = dataSnapshot.getKey();
                likeBtn.setImageResource(R.drawable.ic_like_icon);
            }
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
                likeBtn.setImageResource(R.drawable.ic_not_like_icon);
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }


}
