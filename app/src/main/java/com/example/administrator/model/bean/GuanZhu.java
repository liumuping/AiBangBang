package com.example.administrator.model.bean;

/**
 * Created by Administrator on 2018/4/11.
 */

public class GuanZhu {
    private String name;
    private int imageId;
    public GuanZhu(String name, int imageId){
        this.name=name;
        this.imageId=imageId;

    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
