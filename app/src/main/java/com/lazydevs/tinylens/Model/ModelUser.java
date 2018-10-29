package com.lazydevs.tinylens.Model;

public class ModelUser {

    String FirstName;
    String LastName;
    String Email;
    String UserPhotoUrl;


    public ModelUser()
    {

    }

    public ModelUser(String firstName, String lastName, String email) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
    }

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


}
