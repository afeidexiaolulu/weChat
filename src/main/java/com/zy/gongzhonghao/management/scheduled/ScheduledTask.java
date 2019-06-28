package com.zy.gongzhonghao.management.scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.gongzhonghao.management.bean.Project;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.mapper.TotalSafetyDataMapper;
import com.zy.gongzhonghao.management.service.*;
import com.zy.gongzhonghao.management.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 定时任务类实现各种定时方法
 */

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TotalOperation totalOperation;



    /**
     * 查询安全时长并插入数据库中
     */
    //每一小时执行一次查询安全时长，并放入数据库中
    //@Scheduled(cron="0 */1 * * * ?")
    @Scheduled(cron="0 0 0/1 * * ?")
    public void getSafetyData(){
        //查询安全时长
        try {
            totalOperation.getSafetyData();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("查询安全时长定时任务失效");
        }
    }



    /**
     * 计算每周红黑榜并进行更新
     */
    //每周一早上6点钟开始计算上周各个项目平均分数,只执行一次
    @Scheduled(cron = "0 0 6 ? * MON")
    public void projectScoreWeek(){
        try {
            totalOperation.projectScoreWeek();
        } catch (Exception e) {
            LOGGER.info("插入每周项目分数失败，定时任务捕获异常");
            e.printStackTrace();
        }
    }

    /**
     * 每天凌晨0点01分开始获取天气,3小时一次
     */
    @Scheduled(cron = "0 1 0/3 * * ? ")
    public void WeatherTask(){
        try {
            //封装到weatherService中,通过接口获取天气
            Integer result = weatherService.insertWeatherMsgByInterface();
            if(result == 1){
                LOGGER.debug("插入天气数据成功");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("插入天气信息失败");
        }
    }

    /**
     * 每两个小时查询一次所有安全数据
     */
    @Scheduled(cron="0 1 0/2 * * ?")
    public void totalRequData() {
        try {
            totalOperation.totalRequData();
        } catch (Exception e) {
            LOGGER.info("定时任务捕获所有安全数据插入失败异常");
            e.printStackTrace();
        }
    }
}


