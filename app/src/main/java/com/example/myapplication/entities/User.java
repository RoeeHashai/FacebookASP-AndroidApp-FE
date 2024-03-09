package com.example.myapplication.entities;

import androidx.room.Entity;

import java.util.List;

/**
 * Represents a user in the application.
 */
@Entity
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String image;
    private List<User> friends;
    /**
     * Constructor for creating a User object with a resource ID of the profile picture.
     * @param userName Username of the user.
     * @param password Password of the user.
     * @param displayName Display name of the user.
     * @param profilePic Resource ID of the profile picture.
     */
    public User(String userName, String password, String displayName, String profilePic) {
        this.email = userName;
        this.password = password;
        this.name = displayName;
        this.image = profilePic;
    }
    /**
     * Retrieves the username of the user.
     * @return Username of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the display name of the user.
     * @return Display name of the user.
     */
    public String getName() {
        return name;
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
    public String getImage() {
        return image;
    }

    /**
     * Sets the user's profile picture using a resource ID.
     * @param image Resource ID of the profile picture.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
