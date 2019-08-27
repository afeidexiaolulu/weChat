package com.zy.gongzhonghao.management.scheduled;


import com.zy.gongzhonghao.management.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;


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
            LOGGER.info("查询安全时长定时任务插入数据成功");
        } catch (Exception e) {
            LOGGER.error("查询安全时长定时任务失效");
            e.printStackTrace();
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
            LOGGER.info("插入项目周平均分数成功");
        } catch (Exception e) {
            LOGGER.error("插入每周项目分数失败，定时任务捕获异常");
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
                LOGGER.info("插入天气数据成功");
            }
        } catch (Exception e) {
            LOGGER.error("插入天气信息失败");
            e.printStackTrace();
        }
    }

    /**
     * 每两个小时查询一次所有安全数据
     */
    @Scheduled(cron="0 1 0/2 * * ?")
    public void totalRequData() {
        try {
            totalOperation.totalRequData();
            LOGGER.info("定时任务请求所有安全数据接口无异常");
        } catch (Exception e) {
            LOGGER.error("定时任务捕获所有安全数据插入失败异常");
            e.printStackTrace();
        }
    }
}


