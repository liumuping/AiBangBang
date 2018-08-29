package com.example.administrator.utils;

import android.content.Context;
import android.widget.Toast;

public class Regex {
    public static boolean getStringusername(String s){
        String regex ="1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}";
        return s.matches(regex);
    }
    public static boolean getUserMessage(Context context,String sure_phone, String sure_nickname, String sure_age, String sure_gender){
        if (sure_nickname.matches("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{1,}")){
            if (getStringusername(sure_phone)){
                if (sure_age.matches("^(?:[1-9][0-9]?|1[01][0-9]|120)$")){
                    if (sure_gender.matches("^[\\u4E00-\\uFA29\\uE7C7-\\uE7F3]+-[男女]$")){
                        return true;
                    }else {
                        System.out.println("性别输入有误");
                        Toast.makeText(context,"性别输入有误",Toast.LENGTH_LONG);
                    }
                }else {
                    System.out.println("年龄输入有误");
                    Toast.makeText(context,"年龄输入有误",Toast.LENGTH_LONG);
                }
            }else {
                System.out.println("电话号码有误");
                Toast.makeText(context,"电话号码有误",Toast.LENGTH_LONG);
            }
        }else {
            System.out.println("昵称不符合规范");
            Toast.makeText(context,"昵称不符合规范",Toast.LENGTH_LONG);
        }
        return false;
    }
}
