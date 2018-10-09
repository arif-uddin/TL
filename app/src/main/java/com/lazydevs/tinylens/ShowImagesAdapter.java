package com.lazydevs.tinylens;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Model.ModelImage;
import com.lazydevs.tinylens.Model.ModelUser;

import java.util.ArrayList;

public class ShowImagesAdapter extends RecyclerView.Adapter<ShowImagesAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelImage> images;
    ArrayList<ModelUser> users;

    public ShowImagesAdapter(Context context, ArrayList<ModelImage> images, ArrayList<ModelUser> users) {
        this.context = context;
        this.images = images;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.gridview,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide
                .with(context)
                .load(images.get(i).getmImageUrl())
                .into(viewHolder.imageView);

        viewHolder.image_title.setText(images.get(i).getTitle());
        viewHolder.user_name.setText(users.get(i).getFirstName()+" "+users.get(i).getLastName());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name,image_title;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name=itemView.findViewById(R.id.tv_name);
            image_title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.im_images);
        }
    }

    public void setValue(ModelImage image,ModelUser user)
    {
        images.add(0,image);
        users.add(0,user);
        notifyDataSetChanged();
    }

}
