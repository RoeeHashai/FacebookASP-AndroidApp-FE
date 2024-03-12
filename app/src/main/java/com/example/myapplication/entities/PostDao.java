package com.example.myapplication.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> index();

    @Query("SELECT * FROM post WHERE id = :id")
    Post get(String id);

    @Query("DELETE FROM post")
    void clear();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Post> posts);

    @Insert
    void insert(Post... posts);
}

