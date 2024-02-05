package com.example.myapplication.entities;

public class User {
    private String userName;
    private String password;
    private String displayName;
    private int profilePic;

    public User(String userName, String password, String displayName, int profilePic) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.profilePic =profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }
}
