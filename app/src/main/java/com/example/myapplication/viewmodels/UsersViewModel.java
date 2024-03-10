package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.LoginRequest;
import com.example.myapplication.entities.User;
import com.example.myapplication.repositories.UsersRepository;

import java.util.List;

public class UsersViewModel extends ViewModel {
    private UsersRepository usersRepository;

    public UsersViewModel() {
        usersRepository = new UsersRepository();
    }

    public void createUser(User user) {
        usersRepository.createUser(user);
    }
    public void login(LoginRequest loginRequest) {
        usersRepository.login(loginRequest);
    }
}