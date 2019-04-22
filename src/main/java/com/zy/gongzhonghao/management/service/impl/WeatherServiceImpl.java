package com.zy.gongzhonghao.management.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.Weather;
import com.zy.gongzhonghao.management.mapper.WeatherMapper;
import com.zy.gongzhonghao.management.service.WeatherService;
import com.zy.gongzhonghao.management.util.DateUtils;
import com.zy.gongzhonghao.management.util.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class WeatherServiceImpl extends ServiceImpl <WeatherMapper, Weather> implements WeatherService {


    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${weatherurl}")
    private String weatherurl;

    @Autowired
    private WeatherMapper weatherMapper;


    //获取今天天气
    @Override
    public Weather getWeatherMsg() {
        try{
            String dateStr = DateUtils.getDateStr(0,"yyyy-MM-dd");
            Weather todayWea = weatherMapper.selectWeaByDate(dateStr);
            //根据日期查询天气
            if(todayWea != null){
                //查询成功
                return todayWea;
            }else{
                //查询失败
                //手动调用一次查询天气
                insertWeatherMsgByInterface();
                //返回nullweather
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回空wea
        return null;
    }

    //通过三方接口获取天气
    @Override
    public Integer insertWeatherMsgByInterface() {
        try{
            String s = HttpClientUtils.doGet(weatherurl);//可使用
            //解析为json对象
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONArray data = jsonObject.getJSONObject("data").getJSONArray("forecast");
            JSONObject jsonObject1 = data.getJSONObject(0);
            Weather weather = JSONObject.parseObject(jsonObject1.toString(), Weather.class);
            weather.setInsertTime(new Date());
            return baseMapper.insert(weather);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.debug("获取天气失败");
        }
        return 2;
    }
}
