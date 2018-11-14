package com.example.administrator.utils;

import android.content.Context;
import android.widget.Toast;

public class Regex {
    public static void main(String[] args) {
        System.out.println(getStringnickname("风雨不看"));
        System.out.println(getStringgender("男"));
        System.out.println(getStringpasswored("1234567"));
    }
    public static boolean getStringusername(String s) {
        String regex = "1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}";
        return s.matches(regex);
    }

    public static boolean getStringpasswored(String s) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return s.matches(regex);
    }

    public static boolean getStringnickname(String s) {
        String regex ="^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$";
        return s.matches(regex);
    }

    public static boolean getStringgender(String s) {
        String regex = "[男女]";
        return s.matches(regex);

    }

    public static boolean getStringage(String s) {
        String regex = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
        return s.matches(regex);
    }

    public static boolean gettime(String s) {
        String regex = "[1-5]?[0-9]";
        return s.matches(regex);
    }

    public static boolean getUserMessage(Context context,String sure_gender ,String sure_nickname, String sure_age) {
        if (Regex.getStringnickname(sure_nickname)) {
            if (Regex.getStringage(sure_age)) {
                if (Regex.getStringgender(sure_gender)) {
                    return true;
                } else {
                    System.out.println("性别输入有误");
                    Toast.makeText(context, "性别输入有误", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else {
                System.out.println("年龄输入有误");
                Toast.makeText(context, "年龄输入有误", Toast.LENGTH_SHORT).show();
            }
        } else {
            System.out.println("昵称不符合规范");
            Toast.makeText(context, "昵称不符合规范", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
