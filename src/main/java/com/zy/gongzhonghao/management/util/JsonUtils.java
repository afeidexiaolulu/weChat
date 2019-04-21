package com.zy.gongzhonghao.management.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lishuai
 * @date: 2019/2/25 20:43
 * @description
 */
public class JsonUtils {

    /**
     *   形如  {"hash":"FkCSN6vUwVRvA0YaH8syRcTiRD5R","key":"批量开通账号结果.xls"}
     * @param
     * @return
     */

    public static String mapToJson(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }


}
