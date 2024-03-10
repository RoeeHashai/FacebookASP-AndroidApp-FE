package com.example.myapplication.repositories;

import android.content.Context;

import com.example.myapplication.LoginRequest;
import com.example.myapplication.R;
import com.example.myapplication.UserDetails;
import com.example.myapplication.api.UserAPI;
import com.example.myapplication.api.WebServiceAPI;
import com.example.myapplication.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
}

