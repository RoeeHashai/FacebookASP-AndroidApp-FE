package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.UserDetails;
import com.example.myapplication.entities.Post;
import com.example.myapplication.repositories.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {

    private PostsRepository repository;
    private LiveData<List<Post>> posts;

    public PostsViewModel() {
        repository = new PostsRepository();
        posts = repository.getAll();
    }

    public LiveData<List<Post>> get() {
        return posts;
    }

    public void getUserDetails() {
        repository.getUserDetails();
    }

    public void reload() {
        repository.reload();
    }

    public void createPost(Post post) {
        repository.createPost(post);
        reload();
    }
}
