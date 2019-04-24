package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.TotalWarning;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyStatusLineDto;
import com.zy.gongzhonghao.management.mapper.TotalWarningMapper;
import com.zy.gongzhonghao.management.service.TotalSafetyDataService;
import com.zy.gongzhonghao.management.service.TotalWarningService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class TotalWarningServiceImpl extends ServiceImpl<TotalWarningMapper, TotalWarning> implements TotalWarningService {

    @Autowired
    private TotalSafetyDataService totalSafetyDataService;

    @Autowired
    private TotalWarningMapper totalWarningMapper;

    //插入各种预警
    @Override
    public Integer insertTotalWarning(String yesterday) {

        TotalWarning totalWarning = new TotalWarning();

        //插入泥头车
        Integer aFloat = Math.round(totalSafetyDataService.carWarningAvg(yesterday));
        totalWarning.setCarWarning(aFloat);
        //插入塔吊
        Integer aFloat1 = Math.round(totalSafetyDataService.craneWeightAvg(yesterday));
        totalWarning.setCraneWeight(aFloat1);
        //插入升降机
        Integer aFloat2 = Math.round(totalSafetyDataService.lifterWeightAvg(yesterday));
        totalWarning.setLifterWeight(aFloat2);
        //插入噪音
        Integer aFloat3 = Math.round(totalSafetyDataService.noiseWarningAvg(yesterday));
        totalWarning.setNoiseWarning(aFloat3);
        //插入扬尘
        Integer aFloat4 = Math.round(totalSafetyDataService.dustWarningAvg(yesterday));
        totalWarning.setDustWarning(aFloat4);

        Date parse = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parse = simpleDateFormat.parse(yesterday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //统计日期
        totalWarning.setStatisticsDate(parse);

        //插入时间
        totalWarning.setInsert_time(new Date());

        return baseMapper.insert(totalWarning);
    }

    //查询最近十天预警
    @Override
    public List<TotalWarning> selectLast10TotalWarningList() {
        List<TotalWarning> totalWarningList = totalWarningMapper.selectTotalWarningList();
        //返回结果
        return totalWarningList;
    }

    @Override
    public TotalWarning selectLastOne() {
        //调用查询十天的方法
        List<TotalWarning> totalWarningList = selectLast10TotalWarningList();

        if(totalWarningList != null && totalWarningList.size() != 0){
           return totalWarningList.get(0);
        }
        //如果为空，返回全是0
        TotalWarning totalWarning = new TotalWarning();
        totalWarning.setCarWarning(0);
        totalWarning.setCraneWeight(0);
        totalWarning.setNoiseWarning(0);
        totalWarning.setLifterWeight(0);
        totalWarning.setDustWarning(0);
        return totalWarning;
    }

    @Override
    public SafetyStatusLineDto getPhoneSafetyStatusLine() {
        SafetyStatusLineDto safetyStatusLineDto = new SafetyStatusLineDto();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        List<TotalWarning> totalWarningList = selectLast10TotalWarningList();

        int size = totalWarningList.size();

        String dateString[] = new String[size];
        Integer craneWeight[] = new Integer[size];
        Integer lifterWeight[] = new Integer[size];
        Integer noiseWarning[] = new Integer[size];
        Integer dustWarning[] = new Integer[size];
        Integer carWarning[] = new Integer[size];

        if (totalWarningList != null && totalWarningList.size() != 0) {
            for (int i = 0; i < size; i++) {
                carWarning[i] = totalWarningList.get(size - i - 1).getCarWarning();
                craneWeight[i] = totalWarningList.get(size - i - 1).getCraneWeight();
                dustWarning[i] = totalWarningList.get(size - i - 1).getDustWarning();
                noiseWarning[i] = totalWarningList.get(size - i - 1).getNoiseWarning();
                lifterWeight[i] = totalWarningList.get(size - i - 1).getLifterWeight();
                dateString[i] = sdf.format(totalWarningList.get(size - i - 1).getStatisticsDate());
            }
            safetyStatusLineDto.setCarWarning(carWarning);
            safetyStatusLineDto.setCraneWeight(craneWeight);
            safetyStatusLineDto.setDateString(dateString);
            safetyStatusLineDto.setDustWarning(dustWarning);
            safetyStatusLineDto.setNoiseWarning(noiseWarning);
            safetyStatusLineDto.setLifterWeight(lifterWeight);
            return safetyStatusLineDto;
        }
        return safetyStatusLineDto;
    }
}
