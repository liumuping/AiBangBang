package com.example.administrator.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/14.
 */

public class ReBang {
    private int id;
    private String name;
    private int imageId;
    private String data;

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
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
