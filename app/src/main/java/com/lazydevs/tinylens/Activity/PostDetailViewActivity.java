package com.lazydevs.tinylens.Activity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Model.ModelComment;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;
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
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.description);
        imageView=(ImageView)findViewById(R.id.im_images);
        user_name=(TextView)findViewById(R.id.tv_name);
        category=(TextView)findViewById(R.id.category);

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
