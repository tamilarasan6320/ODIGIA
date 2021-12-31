package com.example.myapplication.model;

import java.io.Serializable;

public class Group implements Serializable {
    String id, user_id,group_name,group_description,group_image;

    public Group(String id, String user_id, String group_name, String group_description, String group_image) {
        this.id = id;
        this.user_id = user_id;
        this.group_name = group_name;
        this.group_description = group_description;
        this.group_image = group_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public String getGroup_image() {
        return group_image;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }
}
