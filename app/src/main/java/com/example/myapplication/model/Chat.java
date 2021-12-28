package com.example.myapplication.model;

import java.io.Serializable;

public class Chat implements Serializable {
    String id, user_id,channel_name,channel_description,channel_image;

    public Chat(String id, String user_id, String channel_name, String channel_description, String channel_image) {
        this.id = id;
        this.user_id = user_id;
        this.channel_name = channel_name;
        this.channel_description = channel_description;
        this.channel_image = channel_image;
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

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_description() {
        return channel_description;
    }

    public void setChannel_description(String channel_description) {
        this.channel_description = channel_description;
    }

    public String getChannel_image() {
        return channel_image;
    }

    public void setChannel_image(String channel_image) {
        this.channel_image = channel_image;
    }
}
