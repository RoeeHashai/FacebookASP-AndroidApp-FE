package com.example.myapplication.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.LoginRequest;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.api.UserAPI;
import com.example.myapplication.entities.User;

import java.util.List;

public class UsersRepository {
    private UserAPI api;

    public UsersRepository() {
        api = new UserAPI();
    }
    public void createUser(User user) {
        api.createUser(user);
    }
    public void login(LoginRequest loginRequest) {
        api.getToken(loginRequest);
    }
    public void editUser(UserDetails userDetails) {
        api.editUser(userDetails);
    }
    public void deleteUser() {
        api.deleteUser();
    }
    public void getUserDetails(String id, MutableLiveData<UserDetails> user) {
        api.getUserDetailById(id, user);
    }
    public void getUserFriends(MutableLiveData<List<Friend>> friends) {
        api.getUserFriends(friends);
    }
    public void sendRequest(String id) {
        api.sendRequest(id);
    }
    public void deleteRequest(String id) {
        api.deleteFriend(id);
    }
    public void acceptRequest(String id) {
        api.acceptFriend(id);
    }
}

