package com.lazydevs.tinylens.Model;

public class ModelComment {

    String userID,imageKey,comment_text;

    public ModelComment()
    {

    }

    public ModelComment(String userID, String imageKey, String comment) {
        this.userID = userID;
        this.imageKey = imageKey;
        this.comment_text = comment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getComment() {
        return comment_text;
    }

    public void setComment(String comment) {
        this.comment_text = comment;
    }

}
