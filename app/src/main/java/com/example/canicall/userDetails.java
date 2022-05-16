package com.example.canicall;

public class userDetails {
    private int imageView;
    private String nameOfUser;
    private String userStatus;

    userDetails(int imageView, String nameOfUser, String userStatus){
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
