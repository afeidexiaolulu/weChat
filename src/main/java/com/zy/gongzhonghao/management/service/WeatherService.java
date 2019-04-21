package com.zy.gongzhonghao.management.service;

import com.zy.gongzhonghao.management.bean.Weather;

public interface WeatherService {

    //获取天气信息
    Weather getWeatherMsg();

    //通过接口获取信息
    Integer insertWeatherMsgByInterface();
}
