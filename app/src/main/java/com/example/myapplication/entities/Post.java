package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.StringListConverter;

import java.util.List;

/**
 * Entity class representing a post.
 */
@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String _id = "";
    @Embedded
    private UserDetails author;
    private String content;
    private String image;
    @TypeConverters({StringListConverter.class})
    private List<String> likes;
    private String date;
    private int commentsLength;

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public UserDetails getAuthor() {
        return author;
    }

    public void setAuthor(UserDetails author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image.isEmpty()) {
            this.image = image;
        }
        else {
            this.image = Base64Utils.compressBase64Image(image);
        }
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCommentsLength() {
        return commentsLength;
    }

    public void setCommentsLength(int commentsLength) {
        this.commentsLength = commentsLength;
    }
}
