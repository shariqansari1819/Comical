package com.codebosses.comical.pojo;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private String profileImage;
    private String profileImageThumbnail;
    private String phoneNumber;
    private String accountType;
    @ServerTimestamp
    private Date timestamp;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String profileImage, String profileImageThumbnail, String phoneNumber, String accountType) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.profileImage = profileImage;
        this.profileImageThumbnail = profileImageThumbnail;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageThumbnail() {
        return profileImageThumbnail;
    }

    public void setProfileImageThumbnail(String profileImageThumbnail) {
        this.profileImageThumbnail = profileImageThumbnail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
