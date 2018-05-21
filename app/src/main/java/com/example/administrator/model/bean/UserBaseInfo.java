package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/17.
 */

public class UserBaseInfo implements Serializable {
    private String username;
    private String password;
    private int userid;

    public int getUserid() { return userid; }

    public void setUserid(int userid) {
        this.userid = userid;
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
