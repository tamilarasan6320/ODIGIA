package com.example.myapplication.model;

import java.io.Serializable;

public class User implements Serializable {
    String id, mobile,first_name;
    boolean is_Selected;

    public User(boolean is_Selected) {
        this.is_Selected = is_Selected;
    }

    public boolean isIs_Selected() {
        return is_Selected;
    }

    public void setIs_Selected(boolean is_Selected) {
        this.is_Selected = is_Selected;
    }

    public User(String id, String mobile, String first_name) {
        this.id = id;
        this.mobile = mobile;
        this.first_name = first_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
