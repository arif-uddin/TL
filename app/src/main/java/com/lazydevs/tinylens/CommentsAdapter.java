package com.lazydevs.tinylens;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lazydevs.tinylens.Activity.PostDetailViewActivity;
import com.lazydevs.tinylens.Model.ModelComment;
import com.lazydevs.tinylens.Model.ModelUser;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private final Context context;
    ArrayList<ModelComment> comments;
    ArrayList<ModelUser> users;
    PostDetailViewActivity postDetailViewActivity;
    

    public CommentsAdapter(Context context, ArrayList<ModelComment> comments, ArrayList<ModelUser> users, PostDetailViewActivity postDetailViewActivity) {
        this.context = context;
        this.comments = comments;
        this.users = users;
        this.postDetailViewActivity = postDetailViewActivity;
    }


    public void setValues(ModelComment comment,ModelUser user){
        comments.add(0,comment);
        users.add(0,user);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.User_name.setText(users.get(i).getFirstName()+" "+users.get(i).getLastName());
        viewHolder.Comment_text.setText(comments.get(i).getComment());
        //viewHolder.Commenter_photo.setImageURI(users.get(i).getUserPhotoUrl());
        Glide
                .with(context)
                .load(users.get(i).getUserPhotoUrl())
                .into(viewHolder.Commenter_photo);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Comment_text,User_name;
        ImageButton Commenter_photo;
        public ViewHolder(View itemView) {
            super(itemView);
            Comment_text = itemView.findViewById(R.id.tv_comment_text);
            User_name = itemView.findViewById(R.id.tv_name);
            Commenter_photo = itemView.findViewById(R.id.ib_commenter_photo);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


}
