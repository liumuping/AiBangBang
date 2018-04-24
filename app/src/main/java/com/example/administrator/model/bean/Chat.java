package com.example.administrator.model.bean;

/**
 * Created by Administrator on 2018/4/14.
 */

public class Chat {
    private String name;
    private int imageId;
    private String data;
    public Chat(String name, int imageId, String data){
        this.name=name;
        this.imageId=imageId;
        this.data=data;

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
