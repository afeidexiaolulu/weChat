package com.zy.gongzhonghao.management.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.WorkerManaRate;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyBehaviourDto;
import com.zy.gongzhonghao.management.mapper.WorkerManaRateMapper;
import com.zy.gongzhonghao.management.service.TotalSafetyDataService;
import com.zy.gongzhonghao.management.service.WorkerManaRateService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WorkerManaRateServiceImpl extends ServiceImpl<WorkerManaRateMapper, WorkerManaRate> implements WorkerManaRateService {

    @Autowired
    private WorkerManaRateMapper workerManaRateMapper;

    @Autowired
    private TotalSafetyDataService totalSafetyDataService;

    //查询近十天培训率和到岗率
    @Override
    public SafetyBehaviourDto selectSafetyBehaviourDto() {
        //对象封装
        SafetyBehaviourDto safetyBehaviourDto = new SafetyBehaviourDto();

        //查询最近10天的工人培训率和管理人员到岗率
        List<WorkerManaRate> workerManaRatelist = workerManaRateMapper.selectLast10day();

        //减少空指针异常，将查询结果的数值长度定义为查询到结果长度
        int arraySize = workerManaRatelist.size();

        //工人培训率10天
        int[] workTrain = new int[arraySize];
        //管理人员到岗率10天
        int[] manaDuty = new int[arraySize];
        //显示的日期
        String[] dateString = new String[arraySize];

        SimpleDateFormat sdf = new SimpleDateFormat("M/d");
        for (int i=0; i< arraySize;i++){
            manaDuty[i] = workerManaRatelist.get(i).getManaDutyRate();
            workTrain[i] = workerManaRatelist.get(i).getWorkerTrainRate();
            dateString[i] = sdf.format(workerManaRatelist.get(i).getStatisticsDate());
        }

        //先对出勤率进行赋值，避免翻转后数据异常
        safetyBehaviourDto.setManaDutyRate(workerManaRatelist.get(0).getManaDutyRate());
        safetyBehaviourDto.setWorkerTrainRate(workerManaRatelist.get(0).getWorkerTrainRate());

        //翻转
        ArrayUtils.reverse(workTrain);
        ArrayUtils.reverse(manaDuty);
        ArrayUtils.reverse(dateString);
        //赋值
        safetyBehaviourDto.setWorkTrain(workTrain);
        safetyBehaviourDto.setManaDuty(manaDuty);
        safetyBehaviourDto.setDateString(dateString);
        //返回
        return safetyBehaviourDto;
    }


    //插入培训率和到岗率
    @Override
    public Integer insertTraDuty(String yesterday) {

        WorkerManaRate workerManaRate = new WorkerManaRate();

        //取出昨天的所有在职工人数
        Integer yesWorkerNum = totalSafetyDataService.getYesWorkerNum(yesterday);
        //取出昨天所有培训工人数
        Integer yesWorkerTraNum = totalSafetyDataService.getYesWorkerTraNum(yesterday);

        //数据校验
        if((yesWorkerNum != null && yesWorkerNum != 0 )&& yesWorkerTraNum != null){
            //计算工人培训率
            float wokerTraRate = (float)yesWorkerTraNum / yesWorkerNum;
            wokerTraRate = wokerTraRate * 100;
            //将数值格式化
            DecimalFormat df = new DecimalFormat("0");
            String s = df.format(wokerTraRate);
            Integer workerTraRate =  new Integer(s);
            workerManaRate.setWorkerTrainRate(workerTraRate);
        }else {
            workerManaRate.setWorkerTrainRate(0);
        }

        //取出昨天所有的在职管理人员数量
        Integer yesManaNum = totalSafetyDataService.getYesManaNum(yesterday);
        //取出昨天所有考勤管理人员数量
        Integer yesManaDutyNum = totalSafetyDataService.getYesManaDutyNum(yesterday);
        if((yesManaNum != null && yesManaNum != 0)&& yesManaDutyNum != null){
            float manaDutyRate = (float) yesManaDutyNum / yesManaNum;
            manaDutyRate = manaDutyRate * 100;
            //将数值格式化
            DecimalFormat df = new DecimalFormat("0");
            String s = df.format(manaDutyRate);
            Integer workerTraRate =  new Integer(s);
            workerManaRate.setManaDutyRate(workerTraRate);
        }else {
            workerManaRate.setManaDutyRate(0);
        }


        //将字符串日期解析为时间类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date statisticsDate = null;
        try {
            statisticsDate = sdf.parse(yesterday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        workerManaRate.setStatisticsDate(statisticsDate);
        workerManaRate.setInsertTime(new Date());

        //插入数据库
        Integer result = workerManaRateMapper.insert(workerManaRate);
        //插入是否插入成功
        return result;
    }

}
