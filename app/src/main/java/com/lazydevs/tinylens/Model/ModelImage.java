package com.lazydevs.tinylens.Model;

import com.google.firebase.database.Exclude;

public class ModelImage {
    private String title;
    private String description;
    private String category;
    private String mThumbUrl;
    private String date;
    private String mImageUrl;
    private String mKey;
    private String userID;
    private String deviceModel;

    @Exclude
    public boolean hasUserLiked=false;

    @Exclude
    public int like_counter=0;

    @Exclude
    public String like_Key;


    public void addLike()
    {
        this.like_counter++;
    }

    public void removeLike()
    {
        this.like_counter--;
    }



    public ModelImage() {
        //empty constructor needed
    }

    public ModelImage(String name, String imageUrl,String mThumbUrl, String userID, String mKey,String description,String category, String deviceModel) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.mThumbUrl=mThumbUrl;
        this.title = name;
        this.description = description;
        this.category = category;
        this.mImageUrl = imageUrl;
        this.userID = userID;
        this.mKey = mKey;
        this.deviceModel = deviceModel;
    }

    public String getTitle() {
        return title;
    }

    public String getmThumbUrl() {
        return mThumbUrl;
    }

    public void setmThumbUrl(String mThumbUrl) {
        this.mThumbUrl = mThumbUrl;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDeviceModel() { return deviceModel; }

    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

}
