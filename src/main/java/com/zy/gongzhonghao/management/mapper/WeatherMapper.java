package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.Weather;
import org.springframework.stereotype.Component;

@Component
public interface WeatherMapper extends BaseMapper<Weather> {
    //根据日期查天气
    Weather selectWeaByDate(String todayStr);
}
