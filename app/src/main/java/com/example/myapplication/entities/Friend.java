package com.example.myapplication.entities;

import com.example.myapplication.Base64Utils;

public class Friend {
    private String _id;
    private String name;
    private String image;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
