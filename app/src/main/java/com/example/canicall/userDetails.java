package com.example.canicall;

public class userDetails {
    private int imageView;
    private String UserName;
    private String status;
    private String frndNumber;

    public userDetails(){
//        this.imageView = imageView;
//        this.UserName = UserName;
//        this.status = status;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImageView() {
        return imageView;
    }

    public String getUserName() {
        return UserName;
    }

    public String getStatus() {
        return status;
    }

    public String getFrndNumber() {
        return frndNumber;
    }

    public void setFrndNumber(String frndNumber) {
        this.frndNumber = frndNumber;
    }
}
