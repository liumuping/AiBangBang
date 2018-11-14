package com.example.administrator.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
         public static String getdate(long time) {
                String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
                Date date = new Date(time);
                return dateFormat.format(date);
            }
        public static Date getnowtime(long time) {
            String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
            Date date = new Date(time);
            return date;
            }
        private static void demo02() {
            Date now = new Date();
            // 默认的抽象类的静态方法
            DateFormat dFormat = DateFormat.getInstance();
            System.out.println(dFormat.format(now)); //18-7-27 上午11:41

            DateFormat dFormat2 = DateFormat.getDateInstance(DateFormat.MEDIUM);
            // LONG 2018年7月27日    FULL 2018年7月27日 星期五  SHORT 18-7-27  MEDIUM 默认参数
            System.out.println(dFormat2.format(now)); // 2018-7-27

            DateFormat dFormat3 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
            // FULL  2018年7月27日 星期五 上午11时48分35秒 CST
            // LONG  2018年7月27日 上午11时49分18秒
            System.out.println(dFormat3.format(now));
            }
}
