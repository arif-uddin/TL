package com.lazydevs.tinylens.Model;

public class ModelImage {
    private String title;
    private String description;
    private String category;
    private String date;
    private String mImageUrl;
    private String mKey;
    private String userID;


    public ModelImage() {
        //empty constructor needed
    }

    public ModelImage(String name, String imageUrl, String userID, String mKey,String description,String category) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.title = name;
        this.description = description;
        this.category = category;
        this.mImageUrl = imageUrl;
        this.userID = userID;
        this.mKey = mKey;
    }

    public String getTitle() {
        return title;
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

}
