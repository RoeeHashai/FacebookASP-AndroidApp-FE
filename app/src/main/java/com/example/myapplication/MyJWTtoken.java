package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MyJWTtoken {
    private static MyJWTtoken instanse;
    private MutableLiveData<String> token;
    private MutableLiveData<UserDetails> userDetails;
    private MyJWTtoken() {
    }
    public static MyJWTtoken getInstance() {
        if (instanse == null) {
            instanse = new MyJWTtoken();
        }
        return instanse;
    }
    public void setToken(String token) {
        if (this.token == null) {
            this.token = new MutableLiveData<>();
        }
        this.token.postValue("Bearer " + token);
    }

    public boolean isExist() {
        return token.getValue() != null;
    }

    public void forgetToken() {
        this.token = null;
        this.userDetails = null;
    }

    public LiveData<String> getToken() {
        if (token == null) {
            token = new MutableLiveData<>();
        }
        return token;
    }

    public LiveData<UserDetails> getUserDetails() {
        if (userDetails == null) {
            userDetails = new MutableLiveData<>();
        }
        UserDetails u = userDetails.getValue();
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        if (this.userDetails == null) {
            this.userDetails = new MutableLiveData<>();
        }
        this.userDetails.setValue(userDetails);
    }

    public void postUserDetails(UserDetails userDetails) {
        if (this.userDetails == null) {
            this.userDetails = new MutableLiveData<>();
        }
        this.userDetails.postValue(userDetails);
    }
}
