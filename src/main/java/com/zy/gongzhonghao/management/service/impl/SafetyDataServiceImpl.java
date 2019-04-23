package com.zy.gongzhonghao.management.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.AccidentNumTable;
import com.zy.gongzhonghao.management.bean.SafetyTimeMax;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyNumDto;
import com.zy.gongzhonghao.management.mapper.AccidentNumMapper;
import com.zy.gongzhonghao.management.mapper.SaTiMaxRecordMapper;
import com.zy.gongzhonghao.management.mapper.SafetyTimeMapper;
import com.zy.gongzhonghao.management.service.SafetyDataService;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 安全数据页面数据请求
 */


@Service
public class SafetyDataServiceImpl implements SafetyDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SafetyDataServiceImpl.class);

    @Autowired
    private AccidentNumMapper accidentNumMapper;

    @Autowired
    private SaTiMaxRecordMapper maxRecordMapper;

    @Autowired
    private SafetyTimeMapper safetyTimeMapper;

    @Override
    public SafetyNumDto getPhoneSafetyData() {
        SafetyNumDto safetyNumDto = new SafetyNumDto();

        //查询最高安全时长，赋值
        SafetyTimeMax saTiMaxRecord = maxRecordMapper.selectById("1");
        if(saTiMaxRecord != null) {
            Integer maxRecord = saTiMaxRecord.getMaxRecord();
            int numLength = maxRecord.toString().length();
            //设置安全时长
            safetyNumDto.setSafetyTimeBig(maxRecord.toString());
            if (numLength > 6) {
                String newMax = maxRecord.toString().substring(0, numLength - 3) + "K";
                safetyNumDto.setSafetyTimeBig(newMax);
            }
        }else {
            safetyNumDto.setSafetyTimeBig("一小时后刷新");
        }
        //查询实时安全工时，赋值
        Integer safetyTime = safetyTimeMapper.selectSafetyTime();
        if(safetyTime != null){
            //安全时长长度
            int safetyTimeLength = safetyTime.toString().length();
            safetyNumDto.setSafetyTime(safetyTime.toString());
            if(safetyTimeLength > 6){
                String newTime = safetyTime.toString().substring(0, safetyTimeLength - 3) + "K";
                safetyNumDto.setSafetyTime(newTime);
            }else {
                safetyNumDto.setSafetyTime("一小时后刷新");
            }
        }


        //查询近三个月的安全事故和日期
        List<AccidentNumTable> accidentNumTables = accidentNumMapper.seletcDataAndDateLast3Month();
        //查询结果长度
        int resultSize = accidentNumTables.size();
        if(resultSize != 0){
            int[] intTem = new int[resultSize];
            Date[] dateTem = new Date[resultSize];
            for (int i = 0; i< resultSize; i++){
                intTem[i] = accidentNumTables.get(i).getAccidentNum();
                dateTem[i] = accidentNumTables.get(i).getAccidentDate();
            }
            safetyNumDto.setAccidentNum(intTem);
            //转换时间格式
            String stringArr[] = new String[resultSize];
            //将日期转换为字符串
            SimpleDateFormat sdf = new SimpleDateFormat("yy年M月");
            for(int i =0;i<intTem.length;i++){
                stringArr[i] = sdf.format(dateTem[resultSize-1-i]);
            }
            safetyNumDto.setDate(stringArr);
            //将数据返回给前台
            return safetyNumDto;
        }else {
            LOGGER.debug("无近三个月安全事故数据");
            return safetyNumDto;
        }
    }
}

