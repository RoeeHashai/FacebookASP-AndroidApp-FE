package com.example.myapplication.api;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.ErrorResponse;
import com.example.myapplication.ErrorUtils;
import com.example.myapplication.MyApplication;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.PostDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Post>> call = webServiceAPI.getPostsFeed(MyJWTtoken.getInstance().getToken().getValue());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                new Thread(() -> {
                    //dao.clear();
                    //dao.insertList(Base64Utils.compressAll(response.body()));
                    //postListData.postValue(dao.index());
                    postListData.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }

    public void getUserPosts(String id) {
        Call<List<Post>> call = webServiceAPI.getUserPosts(MyJWTtoken.getInstance().getToken().getValue(), id);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                new Thread(() -> {
                    //dao.clear();
                    //dao.insertList(Base64Utils.compressAll(response.body()));
                    //postListData.postValue(dao.index());
                    postListData.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }

    public void getUserDetailByEmail() {
        String email = MyJWTtoken.getInstance().getUserDetails().getValue().getEmail();
        Call<UserDetails> call = webServiceAPI.getUserDetailsByEmail(MyJWTtoken.getInstance().getToken().getValue(),
                email);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                new Thread(() -> {
                    UserDetails userDetails = response.body();
                    MyJWTtoken.getInstance().postUserDetails(userDetails);
                }).start();
            }
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
            }
        });
    }

    public void createPost(Post post) {
        Call<Void> call = webServiceAPI.createPost(MyJWTtoken.getInstance().getToken().getValue(),
                MyJWTtoken.getInstance().getUserDetails().getValue().get_id(), post);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyApplication.context, "Published successfully!", Toast.LENGTH_SHORT).show();

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
    public void likePost(String pid) {
        Call<Void> call = webServiceAPI.likePost(MyJWTtoken.getInstance().getToken().getValue(),
                MyJWTtoken.getInstance().getUserDetails().getValue().get_id(), pid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
    public void unlikePost(String pid) {
        Call<Void> call = webServiceAPI.unlikePost(MyJWTtoken.getInstance().getToken().getValue(),
                MyJWTtoken.getInstance().getUserDetails().getValue().get_id(), pid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void deletePost(String pid) {
        Call<Void> call = webServiceAPI.deletePost(MyJWTtoken.getInstance().getToken().getValue(),
                MyJWTtoken.getInstance().getUserDetails().getValue().get_id(), pid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void updatePost(String pid, Post post) {
        Call<Void> call = webServiceAPI.updatePost(MyJWTtoken.getInstance().getToken().getValue(),
                MyJWTtoken.getInstance().getUserDetails().getValue().get_id(), pid, post);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}

