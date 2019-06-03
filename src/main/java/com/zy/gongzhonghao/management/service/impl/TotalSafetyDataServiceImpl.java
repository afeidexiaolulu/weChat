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
        Float warningAVG = totalSafetyDataMapper.getCarWarningNumAVG(yesterday);
        if(warningAVG != null){
            return warningAVG;
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float craneWeightAvg(String yesterday) {
        Float weightAVG = totalSafetyDataMapper.getCraneWeightNumAVG(yesterday);
        if( weightAVG!= null){
            return weightAVG;
        }else {
            return new Float("0");
        }
    }



    @Override
    public Float lifterWeightAvg(String yesterday) {
        Float liftWeightAVG = totalSafetyDataMapper.getLifterWeightNumAVG(yesterday);
        if(liftWeightAVG != null){
            return liftWeightAVG;
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float dustWarningAvg(String yesterday) {
        Float dustWarningAVG =  totalSafetyDataMapper.getDustWarningNumAVG(yesterday);
        if(dustWarningAVG != null){
            return dustWarningAVG;
        }else {
            return new Float("0");
        }
    }

    @Override
    public Float noiseWarningAvg(String yesterday) {
        Float noiseWarningAVG =  totalSafetyDataMapper.getNoiseWarningNumAVG(yesterday);
        if( noiseWarningAVG!= null){
            return noiseWarningAVG;
        }else {
            return new Float("0");
        }
    }
}
