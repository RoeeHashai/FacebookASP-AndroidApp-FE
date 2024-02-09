package com.example.myapplication;

import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserListSrc {
    private static UserListSrc inst;
    private List<User> users;
    private User activeUser;

    private UserListSrc(){

    }

    public static UserListSrc getInstance() {
        if (inst == null) {
            inst = new UserListSrc();
            inst.initList();
        }
        return inst;
    }

    private void initList(){
        if (users != null)
            return;
        users = new ArrayList<>();
        users.add(new User("yatir@gmail.com", "12345678", "Yatir Gross", R.drawable.profile_image ));
        users.add(new User("1", "1", "Natalya Gross", R.drawable.profile_image2));
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

