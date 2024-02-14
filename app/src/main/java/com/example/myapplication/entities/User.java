package com.example.myapplication.entities;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
/**
 * Represents a user in the application.
 */
public class User {
    private String userName;
    private String password;
    private String displayName;
    private Uri uriProfilePic = null;
    private int intProfilePic = 0;
    /**
     * Constructor for creating a User object with a resource ID of the profile picture.
     * @param userName Username of the user.
     * @param password Password of the user.
     * @param displayName Display name of the user.
     * @param profilePic Resource ID of the profile picture.
     */
    public User(String userName, String password, String displayName, int profilePic) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.intProfilePic = profilePic;
    }

    /**
     * Constructor for creating a User object with a URI of the profile picture.
     * @param userName Username of the user.
     * @param password Password of the user.
     * @param displayName Display name of the user.
     * @param profilePic URI of the profile picture.
     */
    public User(String userName, String password, String displayName, Uri profilePic) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.uriProfilePic = profilePic;
    }

    /**
     * Retrieves the username of the user.
     * @return Username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the display name of the user.
     * @return Display name of the user.
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * Retrieves the URI of the user's profile picture.
     * @return URI of the user's profile picture.
     */
    public Uri getUriProfilePic() {
        return uriProfilePic;
    }
    /**
     * Checks if the provided password matches the user's password.
     * @param password Password to check.
     * @return True if the provided password matches the user's password, false otherwise.
     */
    public boolean isMachingPassword(String password) {
        return this.password.equals(password);
    }
    /**
     * Retrieves the resource ID of the user's profile picture.
     * @return Resource ID of the user's profile picture.
     */
    public int getIntProfilePic() {
        return intProfilePic;
    }

    /**
     * Sets the user's profile picture using a resource ID.
     * @param image Resource ID of the profile picture.
     */
    public void setProfilePic(int image) {
        this.intProfilePic = image;
        this.uriProfilePic = null;
    }

    /**
     * Sets the user's profile picture using a URI.
     * @param image URI of the profile picture.
     */
    public void setProfilePic(Uri image) {
        this.uriProfilePic = image;
        this.intProfilePic = 0;
    }
}
