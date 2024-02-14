package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserListSrc {
    private static UserListSrc inst;
    private List<User> users;
    private User activeUser;

    private UserListSrc(){

    }

    public static UserListSrc getInstance(Context context) {
        if (inst == null) {
            inst = new UserListSrc();
            inst.initList(context);
        }
        return inst;
    }

    private void initList(Context context){
        if (users != null)
            return;
        users = JsonToList.createUserList(context, "users.json");
    }
    public User getUser(String userName) {
        for (User user: users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getActiveUser() {
        return activeUser;
    }
}

