package com.zy.gongzhonghao.management.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.mapper.TotalSafetyDataMapper;
import com.zy.gongzhonghao.management.service.TotalSafetyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TotalSafetyDataServiceImpl extends ServiceImpl<TotalSafetyDataMapper, TotalSafetyData> implements TotalSafetyDataService {


    @Autowired
    private TotalSafetyDataMapper totalSafetyDataMapper;


    //插入数据
    @Override
        public Integer insertJsonTableBatch(List<TotalSafetyData> totalSafetyDataList) {


        try {
            return totalSafetyDataMapper.insertJsonTableBatch(totalSafetyDataList);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    //获取昨天所有在职工人数
    @Override
    public Integer getYesWorkerNum(String yesterday) {
        return totalSafetyDataMapper.getYesWorkerNum(yesterday);
    }


    //查询昨天培训工人数
    @Override
    public Integer getYesWorkerTraNum(String yesterday) {

        return totalSafetyDataMapper.getYesWorkerTraNum(yesterday);
    }

    //查询昨天管理人员总人数
    @Override
    public Integer getYesManaNum(String yesterday) {
        return  totalSafetyDataMapper.getYesManaNum(yesterday);

    }


    //查询昨天管理人员考勤人数
    @Override
    public Integer getYesManaDutyNum(String yesterday) {
        return totalSafetyDataMapper.getYesManaDutyNum(yesterday);
    }


    @Override
    public Float carWarningAvg(String yesterday) {
        if(totalSafetyDataMapper.getCarWarningNumAVG(yesterday) != null){
            return totalSafetyDataMapper.getCarWarningNumAVG(yesterday);
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float craneWeightAvg(String yesterday) {
        if(totalSafetyDataMapper.getCraneWeightNumAVG(yesterday) != null){
            return totalSafetyDataMapper.getCraneWeightNumAVG(yesterday);
        }else {
            return new Float("0");
        }
    }



    @Override
    public Float lifterWeightAvg(String yesterday) {

        if(totalSafetyDataMapper.getLifterWeightNumAVG(yesterday) != null){
            return totalSafetyDataMapper.getLifterWeightNumAVG(yesterday);
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float dustWarningAvg(String yesterday) {
        if(totalSafetyDataMapper.getDustWarningNumAVG(yesterday) != null){
            return totalSafetyDataMapper.getDustWarningNumAVG(yesterday);
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float noiseWarningAvg(String yesterday) {
        if(totalSafetyDataMapper.getNoiseWarningNumAVG(yesterday) != null){
            return totalSafetyDataMapper.getNoiseWarningNumAVG(yesterday);
        }else {
            return new Float("0");
        }
    }
}
