package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/17.
 */

public class UserBaseInfo implements Serializable {
    private String username;
    private String password;
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
