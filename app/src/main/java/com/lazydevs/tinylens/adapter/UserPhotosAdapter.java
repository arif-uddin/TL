package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class UserPhotosAdapter extends RecyclerView.Adapter<UserPhotosAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelImage> images;

    public UserPhotosAdapter(Context context, ArrayList<ModelImage> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_photos_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide
                .with(context)
                .load(images.get(i).getmThumbUrl())
                .into(viewHolder.imageView);

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

    public void setValue(ModelImage image)
    {
        images.add(0,image);
        notifyDataSetChanged();
    }

}
