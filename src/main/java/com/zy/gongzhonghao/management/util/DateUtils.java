package com.zy.gongzhonghao.management.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    public static String getJsonString(Integer diff){
        String yesDateStr = DateUtils.getDateStr(diff,"yyyy-MM-dd");

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("SearchBeginDate",yesDateStr);
        paramMap.put("SearchEndDate",yesDateStr);
        paramMap.put("Token","48C8B324-744C-4480-9E0B-966D8632AB05");
        String mapToJson = JsonUtils.mapToJson(paramMap);
        return mapToJson;
    }
}
