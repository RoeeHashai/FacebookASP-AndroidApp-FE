package com.example.myapplication;

import com.example.myapplication.entities.Comment;

import java.util.List;

public class Comments {
    private String _id;
    private List<Comment> comments;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
