package com.lazydevs.tinylens;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Activity.MainActivity;
import com.lazydevs.tinylens.Activity.PostDetailViewActivity;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;

import java.util.ArrayList;

public class ShowImagesAdapter extends RecyclerView.Adapter<ShowImagesAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelImage> images;
    ArrayList<ModelUser> users;
    MainActivity activity;

    public ShowImagesAdapter(Context context, ArrayList<ModelImage> images, ArrayList<ModelUser> users,MainActivity activity) {
        this.context = context;
        this.images = images;
        this.users = users;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.gridview,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide
                .with(context)
                .load(images.get(i).getmThumbUrl())
                .into(viewHolder.imageView);


        if(images.get(i).hasUserLiked)
        {
            viewHolder.like.setImageResource(R.drawable.ic_like_icon);
        }
        else
        {
            viewHolder.like.setImageResource(R.drawable.ic_not_like_icon);
        }


        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.likeHelper(images.get(i));
            }
        });


        viewHolder.counter.setText(images.get(i).like_counter+"");
        viewHolder.image_title.setText(images.get(i).getTitle());
        viewHolder.user_name.setText("By"+" "+users.get(i).getFirstName()+" "+users.get(i).getLastName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PostDetailViewActivity.class);
                intent.putExtra("image",images.get(i).getmImageUrl());
                intent.putExtra("description",images.get(i).getDescription());
                intent.putExtra("category",images.get(i).getCategory());
                intent.putExtra("title",images.get(i).getTitle());
                intent.putExtra("user_name",users.get(i).getFirstName()+" "+users.get(i).getLastName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name,image_title,counter;
        ImageView imageView;
        CardView cardView;
        ImageButton like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name=itemView.findViewById(R.id.tv_name);
            image_title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.im_images);
            cardView=itemView.findViewById(R.id.cardView);
            like=itemView.findViewById(R.id.btn_like);
            counter=itemView.findViewById(R.id.tv_like_counter);
        }
    }

    public void setValue(ModelImage image,ModelUser user)
    {
        images.add(0,image);
        users.add(0,user);
        notifyDataSetChanged();
    }

}
