package com.example.myapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.LoginRequest;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.entities.User;
import com.example.myapplication.repositories.UsersRepository;

import java.util.List;

public class UsersViewModel extends ViewModel {
    private UsersRepository usersRepository;
    private MutableLiveData<List<Friend>> friends;

    public UsersViewModel() {
        usersRepository = new UsersRepository();
        this.friends = new MutableLiveData<>();
    }

    public MutableLiveData<List<Friend>> getFriends() {
        return friends;
    }
    public void reloadFriends() {
        usersRepository.getUserFriends(friends);
    }
    public void createUser(User user) {
        usersRepository.createUser(user);
    }
    public void login(LoginRequest loginRequest) {
        usersRepository.login(loginRequest);
    }
    public void editUser(UserDetails userDetails) {
        usersRepository.editUser(userDetails);
    }
    public void deleteUser() {
        usersRepository.deleteUser();
    }
    public void getUserDetailsById(String id, MutableLiveData<UserDetails> user) {
        usersRepository.getUserDetails(id, user);
    }
    public void sendRequest(String id) {
        usersRepository.sendRequest(id);
    }
    public void deleteRequest(String id) {
        usersRepository.deleteRequest(id);
    }
    public void acceptRequest(String id) {
        usersRepository.acceptRequest(id);
    }
}