package com.example.canicall;

public class userDetails {
    public int imageView;
    public String nameOfUser;
    public String userStatus;

    public userDetails(int imageView, String nameOfUser, String userStatus){
        this.imageView = imageView;
        this.nameOfUser = nameOfUser;
        this.userStatus = userStatus;
    }


    public int getImageView() {
        return imageView;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
