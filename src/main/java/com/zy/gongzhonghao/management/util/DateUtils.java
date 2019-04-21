package com.zy.gongzhonghao.management.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 此工具类用来获取日期的字符串形式
 */

public class DateUtils {

    //获取日期的字符串形式
    public static String getDateStr(Integer diff,String pattern){
        //获取日期类实例
        Calendar calendar = Calendar.getInstance();
        //获取昨天日期
        calendar.add(Calendar.DATE,diff);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

}
