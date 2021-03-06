package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lazydevs.tinylens.Model.ModelComment;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelComment> comments;
    ArrayList<ModelUser> users;
    

    public CommentsAdapter(Context context, ArrayList<ModelComment> comments, ArrayList<ModelUser> users) {
        this.context = context;
        this.comments = comments;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.User_name.setText(users.get(i).getFirstName()+" "+users.get(i).getLastName());
        viewHolder.Commented_text.setText(comments.get(i).getComment());

        Glide
                .with(context)
                .load(users.get(i).getUserPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.imageViewCommenterPhoto);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Commented_text,User_name;
        ImageView imageViewCommenterPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Commented_text = itemView.findViewById(R.id.tv_commented_text);
            User_name = itemView.findViewById(R.id.tv_commenter_username);
            imageViewCommenterPhoto = itemView.findViewById(R.id.iv_commenter_photo);
        }


    }

    public void setValues(ModelComment comment,ModelUser user){
        comments.add(0,comment);
        users.add(0,user);
        notifyDataSetChanged();
    }

}
