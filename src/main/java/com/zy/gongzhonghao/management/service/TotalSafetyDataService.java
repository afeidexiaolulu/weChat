package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import java.util.List;


public interface TotalSafetyDataService extends IService<TotalSafetyData> {

    //将数据插入数据库中
    Integer insertJsonTableBatch(List<TotalSafetyData> totalSafetyDataList);

    //查询昨天在职工人数
    Integer getYesWorkerNum(String yesterday);

    //查询昨天所有的培训工人数
    Integer getYesWorkerTraNum(String yesterday);

    //查询管理人员总人数
    Integer getYesManaNum(String yesterday);

    //查询管理人员考勤人数
    Integer getYesManaDutyNum(String yesterday);

    //泥头车平均
    Float carWarningAvg(String yesterday);
    //塔吊平均
    Float craneWeightAvg(String yesterday);
    //升级机平均
    Float lifterWeightAvg(String yesterday);
    //扬尘平均
    Float dustWarningAvg(String yesterday);
    //噪音平均
    Float noiseWarningAvg(String yesterday);
}
