package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lazydevs.tinylens.Activity.PostDetailViewActivity;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class SearchUserPhotoAdapter extends RecyclerView.Adapter<SearchUserPhotoAdapter.ViewHolder> {
    private final Context context;
    ArrayList<ModelImage> images;
    ArrayList<ModelUser> users;

    public SearchUserPhotoAdapter(Context context, ArrayList<ModelImage> images, ArrayList<ModelUser> users) {
        this.context = context;
        this.images = images;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_photos_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide
                .with(context)
                .load(images.get(i).getmThumbUrl())
                .apply(requestOptions)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,PostDetailViewActivity.class);
                intent.putExtra("imageThumb",images.get(i).getmThumbUrl());
                intent.putExtra("description",images.get(i).getDescription());
                intent.putExtra("category",images.get(i).getCategory());
                intent.putExtra("title",images.get(i).getTitle());
                intent.putExtra("image_key",images.get(i).getmKey());
                intent.putExtra("like_counter",images.get(i).like_counter+"");
                intent.putExtra("user_name",users.get(i).getFirstName()+" "+users.get(i).getLastName());
                intent.putExtra("userId",users.get(i).getUserId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return images.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_user_photos);
        }
    }

    public void setValue(ModelImage image,ModelUser user)
    {
        images.add(0,image);
        users.add(0,user);
        notifyDataSetChanged();
    }

}
