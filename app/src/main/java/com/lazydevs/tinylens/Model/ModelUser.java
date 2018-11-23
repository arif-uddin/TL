package com.lazydevs.tinylens.Model;

public class ModelUser {

    String FirstName;
    String LastName;
    String Email;
    String UserPhotoUrl;
    String UserId;

    public ModelUser(String firstName, String lastName, String email, String userPhotoUrl, String userId) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        UserPhotoUrl = userPhotoUrl;
        UserId = userId;
    }


    public ModelUser()
    {

    }

    /*public ModelUser(String userPhotoUrl) {
        UserPhotoUrl = userPhotoUrl;
    }*/





    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserPhotoUrl() {
        return UserPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        UserPhotoUrl = userPhotoUrl;
    }

    public String getUserId() { return UserId; }

    public void setUserId(String userId) { UserId = userId; }

}
