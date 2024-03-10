package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.entities.User;

import java.util.List;
/**
 * Singleton class responsible for managing the list of users in the application.
 */
public class UserListSrc {
    private static UserListSrc inst;
    private List<User> users;
    private User activeUser;
    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private UserListSrc(){
    }

    /**
     * Gets an instance of the UserListSrc singleton class.
     *
     * @param context The context of the application.
     * @return The instance of the UserListSrc class.
     */
    public static UserListSrc getInstance(Context context) {
        if (inst == null) {
            inst = new UserListSrc();
            inst.initList(context);
        }
        return inst;
    }
    /**
     * Initializes the list of users from a JSON file.
     *
     * @param context The context of the application.
     */
    private void initList(Context context){
        if (users != null)
            return;
        //users = JsonToList.createUserList(context, "users.json");
    }

    /**
     * Gets a user by their username.
     *
     * @param userName The username of the user to retrieve.
     * @return The user object if found, or null if not found.
     */
    public User getUser(String userName) {
        for (User user: users) {
            if (user.getEmail().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user to the list of users.
     *
     * @param user The user to add.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Sets the active user in the application.
     *
     * @param activeUser The active user to set.
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Gets the active user in the application.
     *
     * @return The active user.
     */
    public User getActiveUser() {
        return activeUser;
    }
}

