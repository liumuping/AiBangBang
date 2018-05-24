package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/14.
 */

public class ReBang implements Serializable{

    private String name;
    private int imageId;
    private String data;
    private int rbid;
    private int userid;

    public int getRbid() {
        return rbid;
    }

    public void setRbid(int rbid) {
        this.rbid = rbid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    
}
