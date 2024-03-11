package com.example.myapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.myapplication.Base64Utils;

@Entity
public class UserDetails {
    @ColumnInfo(name = "id")
    private String _id;
    @ColumnInfo(name = "username")
    private String name;
    private String email;
    @ColumnInfo(name = "profileimage")
    private String image;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
