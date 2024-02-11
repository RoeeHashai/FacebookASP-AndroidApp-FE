package com.example.myapplication.entities;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

public class User {
    private String userName;
    private String password;
    private String displayName;
    private Uri uriProfilePic = null;
    private int intProfilePic = 0;

    public User(String userName, String password, String displayName, int profilePic) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.intProfilePic = profilePic;
    }

    public User(String userName, String password, String displayName, Uri profilePic) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.uriProfilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Uri getUriProfilePic() {
        return uriProfilePic;
    }
    public boolean isMachingPassword(String password) {
        return this.password.equals(password);
    }

    public int getIntProfilePic() {
        return intProfilePic;
    }

    public void setProfilePic(int image) {
        this.intProfilePic = image;
        this.uriProfilePic = null;
    }
    public void setProfilePic(Uri image) {
        this.uriProfilePic = image;
        this.intProfilePic = 0;
    }
}
