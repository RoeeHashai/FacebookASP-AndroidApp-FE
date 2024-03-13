package com.example.myapplication.entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
}
