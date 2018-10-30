package com.lazydevs.tinylens.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Model.ModelComment;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class PostDetailViewActivity extends AppCompatActivity {


     TextView title,user_name,description,category;
     ImageView imageView;

     //for comment
     RecyclerView recyclerView_comment;
     ArrayList<ModelComment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);
        title= findViewById(R.id.title);
        description= findViewById(R.id.description);
        imageView= findViewById(R.id.im_images);
        user_name= findViewById(R.id.tv_name);
        category= findViewById(R.id.category);

        //for comment
        recyclerView_comment = findViewById(R.id.recyclerView_comment);
        comments = new ArrayList<>();


        Glide
                .with(getApplicationContext())
                .load(getIntent().getExtras().getString("image"))
                .into(imageView);

        user_name.setText("By"+" "+getIntent().getExtras().getString("user_name"));
        title.setText(getIntent().getExtras().getString("title"));
        description.setText(getIntent().getExtras().getString("description"));
        category.setText("Category: "+getIntent().getExtras().getString("category"));







    }
}
