package com.example.myapplication.api;

import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.Activities.LogInPageActivity;
import com.example.myapplication.ErrorResponse;
import com.example.myapplication.ErrorUtils;
import com.example.myapplication.JWT;
import com.example.myapplication.LoginRequest;
import com.example.myapplication.MyApplication;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.UserDetails;
import com.example.myapplication.entities.User;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void createUser(User user) {
        Call<Void> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyApplication.context, "sign-in successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    ErrorResponse errorResponse = ErrorUtils.parseError(response);
                    String errorMessage = errorResponse.getMessage();
                    Toast.makeText(MyApplication.context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void getToken(LoginRequest loginRequest) {
        Call<JWT> call = webServiceAPI.generateToken(loginRequest);
        call.enqueue(new Callback<JWT>() {
            @Override
            public void onResponse(Call<JWT> call, Response<JWT> response) {
                if (response.isSuccessful()) {
                    JWT token = response.body();
                    MyJWTtoken.getInstance().setToken(token.getToken());
                } else {
                    ErrorResponse errorResponse = ErrorUtils.parseError(response);
                    String errorMessage = errorResponse.getMessage();
                    Toast.makeText(MyApplication.context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JWT> call, Throwable t) {
                Toast.makeText(MyApplication.context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editUser(UserDetails user) {
        String jwt = MyJWTtoken.getInstance().getToken().getValue();
        UserDetails current = MyJWTtoken.getInstance().getUserDetails().getValue();
        String id = current.get_id();
        user.set_id(id);
        Call<Void> call = webServiceAPI.updateUserDetails(jwt, id, user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    user.setEmail(MyJWTtoken.getInstance().getUserDetails().getValue().getEmail());
                    MyJWTtoken.getInstance().setUserDetails(user);
                    Toast.makeText(MyApplication.context, "edit successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    ErrorResponse errorResponse = ErrorUtils.parseError(response);
                    String errorMessage = errorResponse.getMessage();
                    Toast.makeText(MyApplication.context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
