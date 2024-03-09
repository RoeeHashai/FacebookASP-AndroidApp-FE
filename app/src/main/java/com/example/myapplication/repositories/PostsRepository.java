package com.example.myapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.MyApplication;
import com.example.myapplication.UserDetails;
import com.example.myapplication.api.PostAPI;
import com.example.myapplication.entities.AppDB;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.PostDao;


import java.util.ArrayList;
import java.util.List;

public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    private PostAPI api;

    public PostsRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "PostsDB")
                .allowMainThreadQueries().build();
        dao = db.postDao();
        postListData = new PostListData();
        api = new PostAPI(postListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                postListData.postValue(dao.index());
            }).start();
        }
    }

    public void getUserDetails() {
        api.getUserDetailByEmail();
    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void reload() {
        api.get();
    }

    public void createPost(Post post) {
        api.createPost(post);
    }
}



