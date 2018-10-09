package com.lazydevs.tinylens.Model;

import com.google.firebase.database.Exclude;

public class ModelImage {
    private String mName;
    private String date;
    private String mImageUrl;
    private String mKey;
    private String userID;


    public ModelImage() {
        //empty constructor needed
    }

    public ModelImage(String name, String imageUrl, String userID, String mKey) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.mName = name;
        this.mImageUrl = imageUrl;
        this.userID = userID;
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
