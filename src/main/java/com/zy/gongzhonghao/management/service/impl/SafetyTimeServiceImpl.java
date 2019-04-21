package com.zy.gongzhonghao.management.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.SafetyTimeMax;
import com.zy.gongzhonghao.management.bean.SafetyTimeTable;
import com.zy.gongzhonghao.management.mapper.SaTiMaxRecordMapper;
import com.zy.gongzhonghao.management.mapper.SafetyTimeMapper;
import com.zy.gongzhonghao.management.service.SafetyTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 安全工时的实现方法
 */
@Service
public class SafetyTimeServiceImpl extends ServiceImpl<SafetyTimeMapper, SafetyTimeTable> implements SafetyTimeService {

    //注入最大安全工时mapper
    @Autowired
    private SaTiMaxRecordMapper saTiMaxRecordMapper;
    //插入安全工时
    @Override
    public Integer insertSafetyTime(Integer safetyTime) {
        //将安全工时与最大安全工时进行比较，如果大于最大安全工时进行更新
        SafetyTimeMax saTiMaxRecord = saTiMaxRecordMapper.selectById("1");
        //系统第一次使用安全时长为null
        if(saTiMaxRecord == null){
            //直接插入
            SafetyTimeMax maxRecordInsert = new SafetyTimeMax();
            maxRecordInsert.setMaxRecord(safetyTime);
            saTiMaxRecordMapper.insert(maxRecordInsert);
        }
        Integer maxRecord = saTiMaxRecord.getMaxRecord();
        if( maxRecord < safetyTime ){
            //更新最大安全工时
            UpdateWrapper<SafetyTimeMax> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",1);
            SafetyTimeMax maxRecordUpdate = new SafetyTimeMax();
            maxRecordUpdate.setMaxRecord(safetyTime);
            //更新
            saTiMaxRecordMapper.update(maxRecordUpdate,wrapper);
        }
        //插入安全时长
        SafetyTimeTable safetyTimeObject = new SafetyTimeTable();
        safetyTimeObject.setSafetyTime(safetyTime);
        safetyTimeObject.setUpdateTime(new Date());
        Integer result = baseMapper.insert(safetyTimeObject);
        //是否插入成功
        return result;
    }
}
